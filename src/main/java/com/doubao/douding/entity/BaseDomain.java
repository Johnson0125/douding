package com.doubao.douding.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.ebean.Model;
import io.ebean.annotation.WhenCreated;
import io.ebean.annotation.WhenModified;
import io.ebean.annotation.WhoCreated;
import io.ebean.annotation.WhoModified;
import java.time.Instant;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Version;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author zhangs890
 * @Description common base domain which holding common fields
 */

@MappedSuperclass
@Getter
@Setter
@NoArgsConstructor
public abstract class BaseDomain extends Model {
    @Id
    @GeneratedValue(generator = "snowflakeId")
    Long id;

    @JsonIgnore
    @Version
    Long version;

    @WhenCreated
    Instant whenCreated;

    @WhenModified
    Instant whenModified;

    @WhoCreated
    String whoCreated;

    @WhoModified
    String whoModified;
}
