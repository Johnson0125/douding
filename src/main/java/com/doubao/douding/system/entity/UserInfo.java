package com.doubao.douding.system.entity;

import com.doubao.douding.entity.BaseDomain;
import com.doubao.douding.system.entity.finder.UserInfoFinder;
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

    @Length(12)
    private Long salt;

    @DbComment("email")
    private String email;

    @DbComment("user''s status info")
    private Integer userStatus;

    @DbComment("role of user")
    private Integer role;

    private char[] password;

}
