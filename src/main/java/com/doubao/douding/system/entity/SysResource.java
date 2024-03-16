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
    @NotNull
    @Length(20)
    @DbComment("resource name")
    private String resourceName;

    @NotNull
    @Length(50)
    @DbComment("resource url")
    private String url;

    @NotNull
    @Length(1)
    @DbComment("url status")
    private Integer urlStatus;
}
