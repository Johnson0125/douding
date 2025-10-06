package com.doubao.douding.system.security;

import com.doubao.douding.system.entity.UserInfo;
import com.doubao.douding.system.entity.query.QUserInfo;
import java.util.Collection;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

/**
 * @description: douding user details service
 * @author: Johnson
 * @create: 2024-04-05 18:25
 **/
@Component
public class DoudingUserDetailsService implements UserDetailsService {

    //    @Resource
    //    private SysResourceService sysResourceService;

    @Override
    public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {
        UserInfo userInfo = new QUserInfo().username.equalTo(username)
                                                    .findOneOrEmpty()
                                                    .orElseThrow(
                                                        () -> new UsernameNotFoundException("User not found."));

        return new DoudingUserDetails(
            userInfo, getAuthorities(userInfo.getId()));
    }

    private Collection<? extends GrantedAuthority> getAuthorities(Long userId) {
        return null;
    }
}
