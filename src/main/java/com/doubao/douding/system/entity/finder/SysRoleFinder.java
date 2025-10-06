package com.doubao.douding.system.entity.finder;

import com.doubao.douding.system.entity.SysRole;
import io.ebean.Finder;

public class SysRoleFinder extends Finder<Long, SysRole> {
    public SysRoleFinder(final Class<SysRole> type) {
        super(type);
    }
}
