package ru.yusupov.security.details;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

public class UserDetailsImpl  implements UserDetails {
    private String login;
    private String hashPassword;
    private List<GrantedAuthority> authorities;

    public UserDetailsImpl(String login, String hashPassword, List<GrantedAuthority> authorities) {
        this.login = login;
        this.hashPassword = hashPassword;
        this.authorities = authorities;
    }

    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    public String getPassword() {
        return hashPassword;
    }

    public String getUsername() {
        return login;
    }

    public boolean isAccountNonExpired() {
        return true;
    }

    public boolean isAccountNonLocked() {
        return true;
    }

    public boolean isCredentialsNonExpired() {
        return true;
    }

    public boolean isEnabled() {
        return true;
    }
}
