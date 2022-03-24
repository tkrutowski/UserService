package net.focik.userservice.api;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import net.focik.userservice.domain.AppUser;
import net.focik.userservice.domain.exceptions.EmailAlreadyExistsException;
import net.focik.userservice.domain.exceptions.ExceptionHandling;
import net.focik.userservice.domain.exceptions.UserAlreadyExistsException;
import net.focik.userservice.domain.exceptions.UserNotFoundException;
import net.focik.userservice.domain.port.primary.IGetUserUseCase;
import net.focik.userservice.domain.port.primary.IRegisterUserUseCase;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.OK;

@Log4j2
@RequiredArgsConstructor
@RestController
@RequestMapping(path = { "/", "/api/user"})
//najpierw sprawdza czy jest jakiś exception handler w exceptionHandling
public class UserController extends ExceptionHandling {

    private final IGetUserUseCase getUserUseCase;
    private final IRegisterUserUseCase registerUserUseCase;


    @GetMapping
//    ResponseEntity<AppUser> getUser(@RequestParam(name = "username") String username, @RequestParam(name = "password") String password){
    ResponseEntity<AppUser> getUser(@RequestParam(name = "username") String username, @RequestParam(name = "password", required = false) String password){
        int i=0;
        log.info("USER-SERVICE: Try find user by username: = " + username);
        AppUser user = getUserUseCase.getUserByName(username);
        log.info(user != null ? "USER-SERVICE: Found user for username = " + username : "USER-SERVICE: Not found user for username = " + username);

        if(user == null)
            return new ResponseEntity<>( null, HttpStatus.NOT_FOUND);

        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @PostMapping("/register")
    public ResponseEntity<AppUser> register(@RequestBody AppUser user) throws UserNotFoundException, UserAlreadyExistsException, EmailAlreadyExistsException {
        int i=0;
        AppUser newUser = registerUserUseCase.registerUser(user.getFirstName(), user.getLastName(), user.getUsername(), user.getEmail());
        return new ResponseEntity<>(newUser, OK);
    }

    @GetMapping("/home")
    public String getTest(){
//        return "dziala";
    throw new EmailAlreadyExistsException("Już jest.");
    }
}
