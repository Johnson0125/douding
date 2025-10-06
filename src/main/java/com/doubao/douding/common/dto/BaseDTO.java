package com.doubao.douding.common.dto;

import com.doubao.douding.common.dto.validate.Update;
import jakarta.validation.constraints.NotNull;
import java.time.Instant;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * @author Johnson
 */
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Data
@SuperBuilder
public class BaseDTO extends PageDTO {

    @NotNull(groups = {Update.class})
    private Long id;

    private Instant whenCreated;

    private Instant whenModified;

    private String whoCreated;

    private String whoModified;

    private boolean deleted;

}
