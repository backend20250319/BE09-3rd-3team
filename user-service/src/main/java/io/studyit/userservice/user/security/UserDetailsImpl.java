package io.studyit.userservice.user.security;

import io.studyit.userservice.user.entity.User;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;


@Getter
public class UserDetailsImpl implements UserDetails {

    private String userId;
    private String password;
    private Collection<? extends GrantedAuthority> authorities;

    public UserDetailsImpl(User user) {
        this.userId = user.getUserId();
        this.password = user.getPassword();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return java.util.Collections.emptyList();
    }

    @Override
    public String getUsername() {
        return userId;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
