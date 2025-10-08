package com.doubao.douding.system.entity.finder;

import com.doubao.douding.system.entity.SysUserRole;
import io.ebean.Finder;

public class SysUserRoleFinder extends Finder<Long, SysUserRole> {
    public SysUserRoleFinder(final Class<SysUserRole> type) {
        super(type);
    }
}
