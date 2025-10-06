package com.doubao.douding.system.reposiroty;

import io.ebean.Database;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author Johnson
 * @Description TODO
 */
public abstract class BaseEbeanTest {

    @Autowired
    protected Database database;
}
