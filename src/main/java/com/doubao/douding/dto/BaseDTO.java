package com.doubao.douding.dto;

import com.doubao.douding.dto.validate.Update;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Johnson
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class BaseDTO {

    @NotNull(groups = {Update.class})
    Long id;

}
