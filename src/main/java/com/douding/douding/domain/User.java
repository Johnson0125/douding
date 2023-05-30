package com.douding.douding.domain;

import com.douding.douding.domain.finder.UserFinder;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "user")
@Getter
@Setter
@Builder
public class User {
    public static final UserFinder find = new UserFinder(User.class);
    @Id
    @GeneratedValue
    private Long id;
    private String username;
}
