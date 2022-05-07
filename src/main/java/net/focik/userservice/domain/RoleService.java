package net.focik.userservice.domain;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.focik.userservice.domain.exceptions.RoleNotFoundException;
import net.focik.userservice.domain.port.secondary.IPrivilegeRepository;
import net.focik.userservice.domain.port.secondary.IRoleRepository;
import net.focik.userservice.domain.share.PrivilegeType;
import org.springframework.stereotype.Service;

import java.util.*;

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
        Privilege privilege = new Privilege();
        privilege.setRead(PrivilegeType.NULL);
        privilege.setWrite(PrivilegeType.NULL);
        privilege.setDelete(PrivilegeType.NULL);
        privilege.setRole(roleById);

//        Privilege save = privilegeRepository.save(privilege);

        user.addPrivilege(privilege);

        return user;
    }

//    public Privilege findPrivilegeByName(String name) {
//        Privilege privilegeByName = privilegeRepository.getPrivilegeByName(name);
//        if(privilegeByName == null)
//            throw new PrivilegeNotFoundException("Privilege not found by name: "+ name);
//        return privilegeByName;
//    }

    public boolean changePrivilegesInUserRole(AppUser user, Long idRole, Map<String, String> privilegeList) {
//        int index = findIndex(idRole, (List<Role>) user.getRoles());
//        if (index >= 0) {
//            ((List<Role>) user.getRoles()).get(index).setPrivileges(privilegeList);
//            return true;
//        }
        Privilege privilegeByRoleID = getPrivilegeByRoleID(user.getPrivileges(), idRole);
        Iterator<Map.Entry<String, String>> iterator = privilegeList.entrySet().iterator();
        
        while (iterator.hasNext()){
            Map.Entry<String, String> next = iterator.next();
            if(next.getKey().equals("read"))
                privilegeByRoleID.setRead(PrivilegeType.valueOf(next.getValue()));
            else if(next.getKey().equals("write"))
                privilegeByRoleID.setWrite(PrivilegeType.valueOf(next.getValue()));
            else if(next.getKey().equals("delete"))
                privilegeByRoleID.setDelete(PrivilegeType.valueOf(next.getValue()));
        }

        int index = findIndex(privilegeByRoleID.getId(), user.getPrivileges());

        if(index > -1){
            user.getPrivileges().set(index,privilegeByRoleID);
        }

        return true;
    }

    public void deleteRoleFromUser(AppUser user, Long idRole) {
//        List<Privilege> privilegesOld = user.getPrivileges();
//        List<Privilege> privilegesNew = new ArrayList<>();

        Privilege privilege = getPrivilegeByRoleID(user.getPrivileges(), idRole);

        user.deletePrivilege(privilege);

//        for (Privilege p:privilegesOld) {
//            if(!p.getRole().getId().equals(idRole))
//                privilegesNew.add(p);
//        }
//        int index = findIndex(idRole, (List<Role>) user.getRoles());
//        if (index >= 0) {
//            ((List<Role>) user.getRoles()).remove(index);
//            return true;
//        }
        //user.setPrivileges(privilegesNew);
//        return false;
    }

    private int findIndex(Long id, List<Privilege> privileges) {
        int index = -1;
        for (Privilege privilege:privileges) {
            index++;
            if(privilege.getId().equals(id)){
                return index;
            }
        }
        return -1;
    }


    public Privilege getRoleDetails(AppUser user, Long idRole) {
        return getPrivilegeByRoleID(user.getPrivileges(), idRole);
    }


    private Privilege getPrivilegeByRoleID(List<Privilege> privileges, Long idRole) {
        if(privileges == null || privileges.isEmpty())
            throw new RoleNotFoundException("Role id: "+idRole+" not found");


        Optional<Privilege> optionalPrivilege = privileges.stream()
                .filter(privilege -> privilege.getRole().getId().equals(idRole))
                .findFirst();

        if(optionalPrivilege.isEmpty())
            throw new RoleNotFoundException("Role id: "+idRole+" not found");

        return optionalPrivilege.get();
    }
}
