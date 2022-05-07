package net.focik.userservice.api.internal;

import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import net.focik.userservice.domain.AppUser;
import net.focik.userservice.domain.UserFacade;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Log4j2
@Service
public class UserEndpoint {

//    final private UserFacade userFacade;
//    final private Gson gson;
//
//    public String getUser(String username){
//        log.info("USER-INTERNAL-SERVICE: Try find user by username = " + username );
//        AppUser user = userFacade.getUser(username);
//        log.info(user != null ? "ADDRESS-INTERNAL-SERVICE: Found address for username = " + username : "ADDRESS-INTERNAL-SERVICE: Not found address for username = " + username);
//        return gson.toJson(user);
//    }
}
