package com.doubao.douding.system.entity;

import com.doubao.douding.common.entity.BaseDomain;
import com.doubao.douding.system.entity.finder.UserInfoFinder;
import io.ebean.annotation.DbComment;
import io.ebean.annotation.Length;
import io.ebean.annotation.NotNull;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author Johnson
 */
@Entity
@Table(name = "user_info")
@Builder
@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class UserInfo extends BaseDomain {

    public static final UserInfoFinder FIND = new UserInfoFinder(UserInfo.class);

    @NotNull
    @Length(50)
    @DbComment("username")
    private String username;

    @DbComment("telephone")
    private String telephone;

    @Length(2)
    @DbComment("gender")
    private Integer gender;

    @DbComment("email")
    private String email;

    @DbComment("user''s status info")
    private Integer userStatus;

    @DbComment("roles of user")
    @OneToMany(mappedBy = "user")
    private List<SysUserRole> roles;

    private char[] password;

}
