package com.doubao.douding.common.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * @author Johnson
 * @Description page param
 */
@Schema(description = "page param")
@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class PageDTO {
    private static final Integer PAGE_INDEX = 1;
    private static final Integer PAGE_SIZE = 10;

    @Schema(description = "page index, start from 1", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @NotNull(message = "page index could not be null")
    @Min(value = 1, message = "the min page index is 1")
    private Integer pageIndex;

    @Schema(description = "page size, max value is 100", requiredMode = Schema.RequiredMode.REQUIRED, example = "100")
    @NotNull(message = "page index could not be null")
    @Max(value = 1, message = "the max page index is 100")
    private Integer pageSize;

}
