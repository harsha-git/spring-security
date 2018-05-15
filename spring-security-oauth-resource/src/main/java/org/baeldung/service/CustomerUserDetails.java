package org.baeldung.service;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

/**
 * Created by harsjaya on 5/13/18.
 */
public class CustomerUserDetails implements UserDetails{

    private String username;

    private String password;

    private List<GrantedAuthority> grantedAuthorities;

    public CustomerUserDetails(String username, String password, String[] authorities) {

        this.username = username;

        this.password = password;

        this.grantedAuthorities = AuthorityUtils.createAuthorityList(authorities);

    }



    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return grantedAuthorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
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
