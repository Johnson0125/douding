package com.doubao.douding.system.entity.finder;

import com.doubao.douding.system.entity.UserInfo;
import io.ebean.Finder;

/**
 * @author Johnson
 */
public class UserInfoFinder extends Finder<Long, UserInfo> {

    public UserInfoFinder(Class<UserInfo> type) {
        super(type);
    }

}
