package net.focik.userservice.api;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import net.focik.userservice.api.dto.UserDto;
import net.focik.userservice.api.dto.UserLoginDto;
import net.focik.userservice.domain.AppUser;
import net.focik.userservice.domain.HttpResponse;
import net.focik.userservice.domain.SecureUser;
import net.focik.userservice.domain.exceptions.EmailAlreadyExistsException;
import net.focik.userservice.domain.exceptions.ExceptionHandling;
import net.focik.userservice.domain.exceptions.UserAlreadyExistsException;
import net.focik.userservice.domain.exceptions.UserNotFoundException;
import net.focik.userservice.domain.port.primary.*;
import net.focik.userservice.domain.utility.JwtTokenProvider;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static net.focik.userservice.domain.security.constant.SecurityConstant.JWT_TOKEN_HEADER;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

//@CrossOrigin(exposedHeaders = "Jwt-Token")
@Log4j2
@RequiredArgsConstructor
@RestController
@RequestMapping(path = { "/", "/api/user"})
//najpierw sprawdza czy jest jakiś exception handler w exceptionHandling
public class UserController extends ExceptionHandling {

    private final ModelMapper mapper;
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

//        return new ResponseEntity<>(mapper.map(user, UserDto.class), HttpStatus.OK);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @GetMapping
    ResponseEntity<List<UserDto>> getUsers(){
        int i=0;
//        log.info("USER-SERVICE: Try find user by id: = " + id);
        List<AppUser> allUsers = getUserUseCase.getAllUsers();
//        log.info(user != null ? "USER-SERVICE: Found user by ID = " + id : "USER-SERVICE: Not found user by ID = " + id);
        List<UserDto> userDtos = allUsers.stream().map(user -> mapper.map(user, UserDto.class)).collect(Collectors.toList());
        return new ResponseEntity<>(userDtos, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<AppUser> register(@RequestBody AppUser user) throws UserNotFoundException, UserAlreadyExistsException, EmailAlreadyExistsException {
        int i=0;
        AppUser newUser = addNewUserUseCase.addNewUser(user.getFirstName(), user.getLastName(), user.getUsername(), user.getPassword(),
                user.getEmail(), user.isEnabled(), user.isNotLocked());
        return new ResponseEntity<>(newUser, CREATED);
    }

    @PutMapping("/update")
//    @PreAuthorize("hasAnyAuthority('USER_PROFILE_WRITE')")
    public ResponseEntity<AppUser> updateUser(@RequestBody AppUser user) throws UserNotFoundException, UserAlreadyExistsException, EmailAlreadyExistsException {
        AppUser updatedUser = null;
        try {
            int i =0;
            updatedUser = updateUserUseCase.updateUser(user.getId(), user.getFirstName(), user.getLastName(), user.getUsername(), user.getEmail());
        }catch (Exception e) {
            int j = 0;
            log.info(e.getMessage());
        }
        return new ResponseEntity<>(updatedUser, OK);
    }

    @PutMapping("/update/active/{id}")
//    @PreAuthorize("hasAnyAuthority('USER_PROFILE_WRITE')")
    public ResponseEntity<HttpResponse> updateUserActive(@PathVariable Long id, @RequestParam("enabled") boolean isEnabled ) {
            updateUserUseCase.updateIsActive(id, isEnabled);
        return response(HttpStatus.OK, "Zaaktualizowano status użytkownika.");
    }

    @PutMapping("/update/lock/{id}")
//    @PreAuthorize("hasAnyAuthority('USER_PROFILE_WRITE')")
    public ResponseEntity<HttpResponse> updateUserLock(@PathVariable Long id, @RequestParam("lock") boolean isLock ) {
        updateUserUseCase.updateIsLock(id, isLock);
        return response(HttpStatus.OK, "Zaaktualizowano status użytkownika.");
    }

    @PutMapping("/changepass/{id}")
    public ResponseEntity<HttpResponse> changePassword(@PathVariable Long id, @RequestParam("oldPass") String oldPass, @RequestParam("newPass") String newPass) {
        changePasswordUseCase.changePassword(id, oldPass, newPass);
        return response(HttpStatus.OK, "Hasło zmienione.");
    }

    @PostMapping("/login")
    public ResponseEntity<UserLoginDto> login(@RequestBody AppUser user) {
        authenticate(user.getUsername(), user.getPassword());
        AppUser loginUser = getUserUseCase.findUserByUsername(user.getUsername());
        SecureUser secureUser = new SecureUser(loginUser);
        HttpHeaders jwtHeader = getJwtHeader(secureUser);

                UserLoginDto loginDto = mapper.map(loginUser, UserLoginDto.class);
        return new ResponseEntity<>(loginDto, jwtHeader, OK);
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN_DELETE')")
    public ResponseEntity<HttpResponse> deleteUser(@PathVariable Long id){
        deleteUserUseCase.deleteUserById(id);
        return response(HttpStatus.NO_CONTENT, "Użytkownik usunięty.");
    }
    private HttpHeaders getJwtHeader(SecureUser user) {
        HttpHeaders headers = new HttpHeaders();
        headers.add(JWT_TOKEN_HEADER, jwtTokenProvider.generateJwtAccessToken(user));
//        headers.add("Access-Control-Request-Headers", "Authorization");
       // headers.add("Access-Control-Request-Headers", JWT_TOKEN_HEADER);
        return headers;
    }

    private void authenticate(String username, String password) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
    }

    private ResponseEntity<HttpResponse> response(HttpStatus status, String message){
        HttpResponse body = new HttpResponse(status.value(), status, status.getReasonPhrase(), message);
        return new ResponseEntity<>(body, status);
    }
}
