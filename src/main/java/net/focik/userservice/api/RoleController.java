package net.focik.userservice.api;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import net.focik.userservice.api.dto.PrivilegeDto;
import net.focik.userservice.api.dto.RoleDto;
import net.focik.userservice.domain.HttpResponse;
import net.focik.userservice.domain.Privilege;
import net.focik.userservice.domain.Role;
import net.focik.userservice.domain.exceptions.ExceptionHandling;
import net.focik.userservice.domain.port.primary.IChangePrivilegeInUserRoleUseCase;
import net.focik.userservice.domain.port.primary.IAddRoleToUserUseCase;
import net.focik.userservice.domain.port.primary.IDeleteUsersRoleUseCase;
import net.focik.userservice.domain.port.primary.IGetUserRolesUseCase;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

//@CrossOrigin(exposedHeaders = "Jwt-Token")
@Log4j2
@RequiredArgsConstructor
@RestController
@RequestMapping(path = { "/", "/api/user/role"})
//najpierw sprawdza czy jest jakiś exception handler w exceptionHandling
public class RoleController extends ExceptionHandling {

    private final ModelMapper mapper;
    private final IGetUserRolesUseCase getUserRolesUseCase;
    private final IAddRoleToUserUseCase addRoleToUserUseCase;
    private final IChangePrivilegeInUserRoleUseCase changePrivilegeInUserRoleUseCase;
    private final IDeleteUsersRoleUseCase deleteUsersRoleUseCase;



    @GetMapping("/{id}")
    ResponseEntity<List<RoleDto>> getUserRoles(@PathVariable Long id){
        int i=0;
//        log.info("USER-SERVICE: Try find user by id: = " + id);
        List<Role> allRoles = getUserRolesUseCase.getUserRoles(id);
//        log.info(user != null ? "USER-SERVICE: Found user by ID = " + id : "USER-SERVICE: Not found user by ID = " + id);
        List<RoleDto> roleDtos = allRoles.stream().map(role -> mapper.map(role, RoleDto.class)).collect(Collectors.toList());
        return new ResponseEntity<>(roleDtos, HttpStatus.OK);
    }

    @GetMapping
    ResponseEntity<List<RoleDto>> getRoles(){
        int i=0;
//        log.info("USER-SERVICE: Try find user by id: = " + id);
        List<Role> allRoles = getUserRolesUseCase.getRoles();
//        log.info(user != null ? "USER-SERVICE: Found user by ID = " + id : "USER-SERVICE: Not found user by ID = " + id);
        List<RoleDto> roleDtos = allRoles.stream().map(role -> mapper.map(role, RoleDto.class)).collect(Collectors.toList());
        return new ResponseEntity<>(roleDtos, HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<HttpResponse> addRoleToUser(@RequestParam("userID") Long idUser, @RequestParam("roleID") Long idRole) {
        addRoleToUserUseCase.addRoleToUser(idUser, idRole);
        return response(HttpStatus.OK, "Dodano role do użytkownika.");
    }

    @DeleteMapping()
    public ResponseEntity<HttpResponse> deleteRoleFromUser(@RequestParam("userID") Long idUser, @RequestParam("roleID") Long idRole) {
        deleteUsersRoleUseCase.deleteUsersRoleById(idUser, idRole);
        return response(HttpStatus.OK, "Role has been deleted from user.");
    }


    @GetMapping("/details")
    ResponseEntity<List<PrivilegeDto>> getRolesDetails(@RequestParam("userID") Long idUser,
                                                    @RequestParam("roleID") Long idRole){
//        log.info("USER-SERVICE: Try find user by id: = " + id);
        List<PrivilegeDto> roleDetails = new ArrayList<>();
        Privilege details = getUserRolesUseCase.getRoleDetails(idUser, idRole);
        roleDetails.add(new PrivilegeDto("read",details.getRead().toString()));
        roleDetails.add(new PrivilegeDto("write",details.getWrite().toString()));
        roleDetails.add(new PrivilegeDto("delete",details.getDelete().toString()));
//        log.info(user != null ? "USER-SERVICE: Found user by ID = " + id : "USER-SERVICE: Not found user by ID = " + id);
        return new ResponseEntity<>(roleDetails, HttpStatus.OK);
    }

    @PutMapping("/details")
    public ResponseEntity<HttpResponse> addPrivilegesToUserRole(@RequestParam("userID") Long idUser,
                                                                @RequestParam("roleID") Long idRole,
                                                                @RequestBody List<PrivilegeDto> privList) {

        Map<String, String> map = new HashMap<>();
        privList.forEach(privilegeDto -> map.put(privilegeDto.getName(), privilegeDto.getValue()));

        changePrivilegeInUserRoleUseCase.changePrivilegesInUserRole(idUser, idRole, map);
        return response(HttpStatus.OK, "Dodano przywilej do roli użytkownika.");
    }

    private ResponseEntity<HttpResponse> response(HttpStatus status, String message){
        HttpResponse body = new HttpResponse(status.value(), status, status.getReasonPhrase(), message);
        return new ResponseEntity<>(body, status);
    }
}
