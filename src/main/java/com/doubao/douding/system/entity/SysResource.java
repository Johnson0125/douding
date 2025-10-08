package com.doubao.douding.system.entity;

import com.doubao.douding.common.entity.BaseDomain;
import com.doubao.douding.system.entity.finder.SysResourceFinder;
import com.doubao.douding.system.entity.finder.SysRoleFinder;
import io.ebean.annotation.DbComment;
import io.ebean.annotation.Length;
import io.ebean.annotation.NotNull;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @description: system resources
 * @author: Johnson
 * @create: 2024-03-16 13:09
 **/
@Entity
@Table(name = "sys_resource")
@Builder
@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class SysResource extends BaseDomain {

    public static final SysResourceFinder FIND = new SysResourceFinder(SysResource.class);

    @DbComment("parent id")
    @ManyToOne
    private SysResource parent;

    @NotNull
    @Length(20)
    @DbComment("resource name")
    private String resourceName;

    @NotNull
    @Length(20)
    @DbComment("resource type, 1:dir,2:menu,3:button")
    private Integer resourceType;

    @Length(50)
    @DbComment("resource url")
    private String url;

    @NotNull
    @Length(1)
    @DbComment("resource status, 1:normal,0:disabled")
    private Integer resourceStatus;

    @Length(100)
    @DbComment("permission")
    private String permission;

    @Length(200)
    @DbComment("remark")
    private String remark;
}
