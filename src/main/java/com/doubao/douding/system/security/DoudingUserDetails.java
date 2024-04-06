package com.doubao.douding.system.security;

import com.doubao.douding.system.entity.UserInfo;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * @description: user details
 * @author: Johnson
 * @create: 2024-04-05 16:53
 **/
public class DoudingUserDetails implements UserDetails {

    public DoudingUserDetails(final UserInfo userInfo) {
        this.userInfo = userInfo;
    }

    private final UserInfo userInfo;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("admin"));
    }

    @Override
    public String getPassword() {
        return Arrays.toString(this.userInfo.getPassword());
    }

    @Override
    public String getUsername() {
        return this.userInfo.getUsername();
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
