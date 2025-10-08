package com.doubao.douding.system.entity;

import com.doubao.douding.common.entity.BaseDomain;
import io.ebean.annotation.DbComment;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @description: relation between role and resource 1:n
 * @author: Johnson
 * @create: 2024-03-16 13:14
 **/
@Entity
@Table(name = "sys_role_resource")
@Builder
@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class SysRoleResource extends BaseDomain {

    @DbComment("role id")
    @ManyToOne
    private SysRole sysRole;

    @DbComment("resource id")
    @ManyToOne
    private SysResource sysResource;
}
