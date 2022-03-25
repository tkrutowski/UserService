package net.focik.userservice.domain;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.focik.userservice.domain.exceptions.EmailAlreadyExistsException;
import net.focik.userservice.domain.exceptions.UserAlreadyExistsException;
import net.focik.userservice.domain.exceptions.UserNotFoundException;
import net.focik.userservice.domain.port.secondary.IAppUserRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

import static net.focik.userservice.domain.security.constant.UserConstant.*;
import static org.apache.commons.lang3.StringUtils.EMPTY;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
@Qualifier("userDetailsService")
public class UserServiceImpl implements IUserService, UserDetailsService {
    private final IAppUserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AppUser byUsername = userRepository.findUserByUsername(username);

        if (byUsername == null){
            log.error(NO_USER_FOUND_BY_USERNAME + username);
            throw new UsernameNotFoundException(NO_USER_FOUND_BY_USERNAME + username);
        }
        else {
            byUsername.setLastLoginDateDisplay(byUsername.getLastLoginDate());
            byUsername.setLastLoginDate(new Date());
            userRepository.save(byUsername);

            UserPrincipal userPrincipal = new UserPrincipal(byUsername);
            log.info(FOUND_USER_BY_USERNAME + username);
            return userPrincipal;
        }
    }

    @Override
    public AppUser addNewUser(String firstName, String lastName, String username, String password, String email, boolean enabled,
                              boolean isNotLocked) throws UserNotFoundException, UserAlreadyExistsException, EmailAlreadyExistsException {
        validateNewUsernameAndEmail(0L, username, email);
        AppUser user = new AppUser();
        String encodedPassword = encodePassword(password);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setUsername(username);
        user.setEmail(email);
        user.setJoinDate(new Date());
        user.setPassword(encodedPassword);
        user.setEnabled(enabled);
        user.setNotLocked(isNotLocked);
//        user.setRole(ROLE_USER.name());
//        user.setAuthorities(ROLE_USER.getAuthorities());
        userRepository.save(user);
        return user;
    }

    @Override
    public AppUser updateUser(Long id, String newFirstName, String newLastName, String newUsername, String newEmail) {
        AppUser currentUser = validateNewUsernameAndEmail(id, newUsername, newEmail);
        currentUser.setFirstName(newFirstName);
        currentUser.setLastName(newLastName);
        currentUser.setUsername(newUsername);
        currentUser.setEmail(newEmail);
        userRepository.save(currentUser);
        return currentUser;
    }

    @Override
    public List<AppUser> getUsers() {
        return userRepository.findAll();
    }

    @Override
    public void deleteUser(Long id) {
        userRepository.deleteUser(id);
    }

    @Override
    public AppUser findUserByUsername(String username) {
        return userRepository.findUserByUsername(username);
    }



    @Override
    public AppUser findUserById(Long id) {
        return userRepository.findUserById(id);
    }


    @Override
    public AppUser findUserByEmail(String email) {
        return userRepository.findUserByEmail(email);
    }

    private AppUser validateNewUsernameAndEmail(Long currentId, String newUsername, String newEmail) throws UserNotFoundException, UserAlreadyExistsException, EmailAlreadyExistsException {
        AppUser userByNewUsername = findUserByUsername(newUsername);
        AppUser userByNewEmail = findUserByEmail(newEmail);
        if(currentId > 0) {
            AppUser currentUser = findUserById(currentId);
            if(currentUser == null) {
                throw new UserNotFoundException(NO_USER_FOUND_BY_ID + currentId);
            }
            if(userByNewUsername != null && !currentUser.getId().equals(userByNewUsername.getId())) {
                throw new UserAlreadyExistsException(USERNAME_ALREADY_EXISTS);
            }
            if(userByNewEmail != null && !currentUser.getId().equals(userByNewEmail.getId())) {
                throw new EmailAlreadyExistsException(EMAIL_ALREADY_EXISTS);
            }
            return currentUser;
        } else {
            if(userByNewUsername != null) {
                throw new UserAlreadyExistsException(USERNAME_ALREADY_EXISTS);
            }
            if(userByNewEmail != null) {
                throw new EmailAlreadyExistsException(EMAIL_ALREADY_EXISTS);
            }
            return null;
        }
    }

    private String encodePassword(String password) {
        return passwordEncoder.encode(password);
    }
}
