package net.focik.userservice.domain;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.focik.userservice.domain.exceptions.PrivilegeNotFoundException;
import net.focik.userservice.domain.exceptions.RoleNotFoundException;
import net.focik.userservice.domain.port.secondary.IPrivilegeRepository;
import net.focik.userservice.domain.port.secondary.IRoleRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class RoleService {
    private final IRoleRepository roleRepository;
    private final IPrivilegeRepository privilegeRepository;

    public List<Role> getAllRoles() {

        return roleRepository.getAllRoles();
    }

    public Role findRoleById(Long idRole) {

        Role roleById = roleRepository.getRoleById(idRole);

        if(roleById == null)
            throw new RoleNotFoundException("Role not found by id: "+ idRole);
        return roleById;
    }

    public AppUser addRoleToUser(AppUser user, Long idRole) {

        Role roleById = roleRepository.getRoleById(idRole);

        if(roleById == null)
            throw new RoleNotFoundException("Role not found by id: "+ idRole);

        user.addRole(roleById);

        return user;
    }

    public Privilege findPrivilegeByName(String name) {
        Privilege privilegeByName = privilegeRepository.getPrivilegeByName(name);
        if(privilegeByName == null)
            throw new PrivilegeNotFoundException("Privilege not found by name: "+ name);
        return privilegeByName;
    }

    public boolean changePrivilegesInUserRole(AppUser user, Long idRole, List<Privilege> privilegeList) {
        int index = findIndex(idRole, (List<Role>) user.getRoles());
        if (index >= 0) {
            ((List<Role>) user.getRoles()).get(index).setPrivileges(privilegeList);
            return true;
        }
        return false;
    }

    public boolean deleteRoleFromUser(AppUser user, Long idRole) {
        int index = findIndex(idRole, (List<Role>) user.getRoles());
        if (index >= 0) {
            ((List<Role>) user.getRoles()).remove(index);
            return true;
        }
        return false;
    }

    private int findIndex(Long idRole, List<Role> roles) {
        int index = -1;
        for (Role role:roles) {
            index++;
            if(role.getId().equals(idRole)){
                return index;
            }
        }
        return index;
    }


    public List<Privilege> getRoleDetails(AppUser user, Long idRole) {
        List<Privilege> privilegeList = new ArrayList<>();
        int index = findIndex(idRole, (List<Role>) user.getRoles());
        if (index >= 0) {
            privilegeList = (List<Privilege>) ((List<Role>) user.getRoles()).get(index).getPrivileges();
        }
        return privilegeList;
    }
}
