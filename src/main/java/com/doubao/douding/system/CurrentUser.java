package com.doubao.douding.system;

import io.ebean.config.CurrentUserProvider;
import org.springframework.stereotype.Component;

/**
 * @author Johnson
 * @description get current login user
 */
@Component
public class CurrentUser implements CurrentUserProvider {

    /**
     * Return the current user id.
     * <p>
     * The type returned should match the type of the properties annotated
     * with @WhoCreated and @WhoModified. These are typically String, Long or UUID.
     * </p>
     */
    @Override
    public Object currentUser() {
        return "test";
    }

}
