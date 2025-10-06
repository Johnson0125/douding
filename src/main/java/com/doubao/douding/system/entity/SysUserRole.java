package com.doubao.douding.system.entity;

import com.doubao.douding.common.entity.BaseDomain;
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
    @ManyToOne
    private UserInfo user;

    @NotNull
    @Length(19)
    @DbComment("role id")
    @ManyToOne
    private SysRole role;
}
