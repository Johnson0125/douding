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

    @NotNull
    @Length(20)
    @DbComment("role name")
    private String roleName;

    @NotNull
    @Length(1)
    @DbComment("role status")
    private Integer roleStatus;
}
