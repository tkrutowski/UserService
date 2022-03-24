package net.focik.userservice.domain;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Component
@RequiredArgsConstructor
public class UserFacade {

    private final UserService userService;

    public AppUser registerUser(String firstName, String lastName, String username, String email) {
        return userService.register(firstName, lastName, username, email);
    }
//
//
//    public AppUser addUser(AppUser user) {
//        return userService.saveUser(user);
//    }
//
//    public AppUser getUser(String username) {
//         return userService.getUser(username);
//    }
}
