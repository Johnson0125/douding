package com.doubao.douding.system.entity;

import com.doubao.douding.common.entity.BaseDomain;
import com.doubao.douding.system.entity.finder.SysRoleFinder;
import io.ebean.annotation.DbComment;
import io.ebean.annotation.Length;
import io.ebean.annotation.NotNull;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @description: system user role
 * @author: Johnson
 * @create: 2024-03-16 13:06
 **/
@Entity
@Table(name = "sys_role")
@Builder
@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class SysRole extends BaseDomain {
    public static final SysRoleFinder FIND = new SysRoleFinder(SysRole.class);

    @NotNull
    @Length(20)
    @DbComment("role name")
    private String roleName;

    @NotNull
    @Length(1)
    @DbComment("role status")
    private Integer roleStatus;

    @Length(100)
    @DbComment("remark")
    private String remark;
}
