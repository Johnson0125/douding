package com.douding.douding.domain.finder;

import com.douding.douding.domain.User;
import io.ebean.Finder;

public class UserFinder extends Finder<Long, User> {

    public UserFinder(Class<User> type) {
        super(type);
    }
}
