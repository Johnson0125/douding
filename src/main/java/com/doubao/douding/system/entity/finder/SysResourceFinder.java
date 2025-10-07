package com.doubao.douding.system.entity.finder;

import com.doubao.douding.system.entity.SysResource;
import com.doubao.douding.system.entity.SysRole;
import io.ebean.Finder;

public class SysResourceFinder extends Finder<Long, SysResource> {
    public SysResourceFinder(final Class<SysResource> type) {
        super(type);
    }
}
