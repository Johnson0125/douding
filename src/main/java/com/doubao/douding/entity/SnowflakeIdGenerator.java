package com.doubao.douding.entity;

import com.doubao.douding.util.Snowflake;
import io.ebean.config.IdGenerator;

/**
 * @Author Johnson
 * @Date 2023/12/25-22:49
 * @Description: generate a snowflake id
 **/
public class SnowflakeIdGenerator implements IdGenerator {
    @Override
    public Object nextValue() {
        return Snowflake.INSTANCE.nextId();
    }

    @Override
    public String getName() {
        return "snowflakeId";
    }
}
