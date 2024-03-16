package com.doubao.douding.system.entity;

import com.doubao.douding.entity.BaseDomain;
import io.ebean.annotation.DbComment;
import io.ebean.annotation.Length;
import io.ebean.annotation.NotNull;
import javax.persistence.Entity;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @description: relation between user and role 1:n
 * @author: Johnson
 * @create: 2024-03-16 13:12
 **/
@Entity
@Table(name = "sys_user_role")
@Builder
@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class SysUserRole extends BaseDomain {
    @NotNull
    @Length(19)
    @DbComment("user id")
    private Long userId;

    @NotNull
    @Length(19)
    @DbComment("role id")
    private Long roleId;
}
