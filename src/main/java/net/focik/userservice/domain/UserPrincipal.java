package net.focik.userservice.domain;

import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.stream.Collectors;

import static java.util.Arrays.stream;


@AllArgsConstructor
public class UserPrincipal implements UserDetails {

    private AppUser user;
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        return user.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority(role.getName()))
                .collect(Collectors.toList());
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
}
