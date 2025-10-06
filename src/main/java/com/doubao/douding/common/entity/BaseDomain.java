package com.doubao.douding.common.entity;

import io.ebean.Model;
import io.ebean.annotation.SoftDelete;
import io.ebean.annotation.WhenCreated;
import io.ebean.annotation.WhenModified;
import io.ebean.annotation.WhoCreated;
import io.ebean.annotation.WhoModified;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.Version;
import java.time.Instant;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author Johnson
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

    @Version
    Long recordModificationVersion;

    @WhenCreated
    Instant whenCreated;

    @WhenModified
    Instant whenModified;

    @WhoCreated
    String whoCreated;

    @WhoModified
    String whoModified;

    @SoftDelete
    boolean deleted;

}
