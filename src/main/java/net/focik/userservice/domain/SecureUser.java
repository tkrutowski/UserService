package net.focik.userservice.domain;

import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static java.util.Arrays.stream;


@AllArgsConstructor
public class SecureUser implements UserDetails {

    private AppUser user;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        return getAuthorities(user.getPrivileges());
//        return user.getRoles().stream()
//                .map(role -> new SimpleGrantedAuthority(role.getName()))
//                .collect(Collectors.toList());
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true; //nie wygasa
    }

    @Override
    public boolean isAccountNonLocked() {
        return user.isNotLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
        //        return user.isTokenExpired();
    }

    @Override
    public boolean isEnabled() {
        return user.isEnabled();
    }


    private Collection<? extends GrantedAuthority> getAuthorities(Collection<Privilege> privileges) {

        return getGrantedAuthorities(getPrivileges(privileges));
    }

    private List<String> getPrivileges(Collection<Privilege> privileges) {

        List<String> privilegesList = new ArrayList<>();
//        List<Privilege> collection = new ArrayList<>();
        for (Privilege privilege : privileges) {
            privilegesList.add(privilege.getRole().getName());
            privilegesList.add(privilege.getFullReadName());
            privilegesList.add(privilege.getFullWriteName());
            privilegesList.add(privilege.getFullDeleteName());

//            collection.addAll(role.getPrivileges());
        }
//        for (Privilege item : collection) {
//            privilegesList.add(item.getName());
//        }
        return privilegesList;
    }

    private List<GrantedAuthority> getGrantedAuthorities(List<String> privileges) {
        List<GrantedAuthority> authorities = new ArrayList<>();
        for (String privilege : privileges) {
            authorities.add(new SimpleGrantedAuthority(privilege));
        }
        return authorities;
    }
}
