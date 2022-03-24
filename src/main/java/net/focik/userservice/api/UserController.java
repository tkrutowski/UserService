package net.focik.userservice.api;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import net.focik.userservice.domain.AppUser;
import net.focik.userservice.domain.exceptions.EmailAlreadyExistsException;
import net.focik.userservice.domain.exceptions.ExceptionHandling;
import net.focik.userservice.domain.port.primary.IGetUserUseCase;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Log4j2
@RequiredArgsConstructor
@RestController
@RequestMapping(path = { "/", "/api/user"})
//najpierw sprawdza czy jest jakiś exception handler w exceptionHandling
public class UserController extends ExceptionHandling {

    private final IGetUserUseCase getUserUseCase;

//    @PostMapping
//    public Integer addGasConnection(@RequestBody GasConnectionDbDto gasConnectionDbDto){
//        return getGasConnectionUseCase.addGasConnection(gasConnectionDbDto);
//    }

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

    @GetMapping("/home")
    public String getTest(){
//        return "dziala";
    throw new EmailAlreadyExistsException("Już jest.");
    }
}
