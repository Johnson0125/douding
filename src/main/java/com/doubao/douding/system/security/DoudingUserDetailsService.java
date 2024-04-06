package com.doubao.douding.system.security;

import com.doubao.douding.system.entity.UserInfo;
import com.doubao.douding.system.entity.query.QUserInfo;
import java.util.Optional;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

/**
 * @description: douding user details service
 * @author: Johnson
 * @create: 2024-04-05 18:25
 **/
public class DoudingUserDetailsService implements UserDetailsService {

    @Override
    public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {
        Optional<UserInfo> userInfoOptional = new QUserInfo().username.equalTo(username).findOneOrEmpty();
        return new DoudingUserDetails(
            userInfoOptional.orElseThrow(() -> new UsernameNotFoundException("User not found.")));
    }
}
