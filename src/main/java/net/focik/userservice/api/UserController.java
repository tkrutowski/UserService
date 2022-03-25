package net.focik.userservice.api;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import net.focik.userservice.domain.AppUser;
import net.focik.userservice.domain.HttpResponse;
import net.focik.userservice.domain.UserPrincipal;
import net.focik.userservice.domain.exceptions.EmailAlreadyExistsException;
import net.focik.userservice.domain.exceptions.ExceptionHandling;
import net.focik.userservice.domain.exceptions.UserAlreadyExistsException;
import net.focik.userservice.domain.exceptions.UserNotFoundException;
import net.focik.userservice.domain.port.primary.*;
import net.focik.userservice.domain.utility.JwtTokenProvider;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static net.focik.userservice.domain.security.constant.SecurityConstant.JWT_TOKEN_HEADER;
import static org.springframework.http.HttpStatus.OK;

@Log4j2
@RequiredArgsConstructor
@RestController
@RequestMapping(path = { "/", "/api/user"})
//najpierw sprawdza czy jest jakiś exception handler w exceptionHandling
public class UserController extends ExceptionHandling {

    private final IGetUserUseCase getUserUseCase;
    private final IAddNewUserUseCase addNewUserUseCase;
    private final JwtTokenProvider jwtTokenProvider;
    private final AuthenticationManager authenticationManager;
    private final IUpdateUserUseCase updateUserUseCase;
    private final IDeleteUserUseCase deleteUserUseCase;
    private final IChangePasswordUseCase changePasswordUseCase;

    @GetMapping("/{id}")
    ResponseEntity<AppUser> getUser(@PathVariable Long id){
        int i=0;
        log.info("USER-SERVICE: Try find user by id: = " + id);
        AppUser user = getUserUseCase.findUserById(id);
        log.info(user != null ? "USER-SERVICE: Found user by ID = " + id : "USER-SERVICE: Not found user by ID = " + id);

        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @GetMapping
    ResponseEntity<List<AppUser>> getUsers(){
        int i=0;
//        log.info("USER-SERVICE: Try find user by id: = " + id);
        List<AppUser> allUsers = getUserUseCase.getAllUsers();
//        log.info(user != null ? "USER-SERVICE: Found user by ID = " + id : "USER-SERVICE: Not found user by ID = " + id);

        return new ResponseEntity<>(allUsers, HttpStatus.OK);
    }

    @PostMapping("/register")
    public ResponseEntity<AppUser> register(@RequestBody AppUser user) throws UserNotFoundException, UserAlreadyExistsException, EmailAlreadyExistsException {
        int i=0;
        AppUser newUser = addNewUserUseCase.addNewUser(user.getFirstName(), user.getLastName(), user.getUsername(), user.getPassword(),
                user.getEmail(), user.isEnabled(), user.isNotLocked());
        return new ResponseEntity<>(newUser, OK);
    }

    @PutMapping("/update")
    public ResponseEntity<AppUser> updateUser(@RequestBody AppUser user) throws UserNotFoundException, UserAlreadyExistsException, EmailAlreadyExistsException {
        int i=0;
        AppUser updatedUser = updateUserUseCase.updateUser(user.getId(), user.getFirstName(), user.getLastName(), user.getUsername(), user.getEmail());

        return new ResponseEntity<>(updatedUser, OK);
    }

    @PutMapping("/changepass/{id}")
    public ResponseEntity<HttpResponse> changePassword(@PathVariable Long id, @RequestParam("oldPass") String oldPass, @RequestParam("newPass") String newPass) {
        changePasswordUseCase.changePassword(id, oldPass, newPass);
        return response(HttpStatus.OK, "Hasło zmienione.");
    }

    @PostMapping("/login")
    public ResponseEntity<AppUser> login(@RequestBody AppUser user) {
        authenticate(user.getUsername(), user.getPassword());
        AppUser loginUser = getUserUseCase.findUserByUsername(user.getUsername());
        UserPrincipal userPrincipal = new UserPrincipal(loginUser);
        HttpHeaders jwtHeader = getJwtHeader(userPrincipal);
        return new ResponseEntity<>(loginUser, jwtHeader, OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<HttpResponse> deleteUser(@PathVariable Long id){
        deleteUserUseCase.deleteUserById(id);
        return response(HttpStatus.OK, "Użytkownik usunięty.");
    }
    private HttpHeaders getJwtHeader(UserPrincipal user) {
        HttpHeaders headers = new HttpHeaders();
        headers.add(JWT_TOKEN_HEADER, jwtTokenProvider.generateJwtAccessToken(user));
        return headers;
    }

    private void authenticate(String username, String password) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
    }

    private ResponseEntity<HttpResponse> response(HttpStatus status, String message){
        return null;
    }
}
