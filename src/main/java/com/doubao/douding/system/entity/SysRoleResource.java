package com.doubao.douding.system.entity;

import com.doubao.douding.common.entity.BaseDomain;
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

    @NotNull
    @Length(19)
    @DbComment("role id")
    private Long roleId;

    @NotNull
    @Length(19)
    @DbComment("resource id")
    private Long resourceId;
}
