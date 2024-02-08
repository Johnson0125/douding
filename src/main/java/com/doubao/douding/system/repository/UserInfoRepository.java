package com.doubao.douding.system.repository;

import com.doubao.douding.system.entity.UserInfo;
import io.ebean.BeanRepository;
import io.ebean.Database;
import org.springframework.stereotype.Repository;

@Repository
public class UserInfoRepository extends BeanRepository<Long, UserInfo> {

    protected UserInfoRepository(Database database) {
        super(UserInfo.class, database);
    }

}
