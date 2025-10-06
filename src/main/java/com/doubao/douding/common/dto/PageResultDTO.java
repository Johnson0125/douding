package com.doubao.douding.common.dto;

import io.ebean.PagedList;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import java.util.function.Function;
import lombok.Builder;

/**
 * @author Johnson
 * @Description dto for page result
 */

@Schema(description = "dto for page result")
@Builder
public record PageResultDTO<D>(
    @Schema(description = "page data")
    List<D> list,
    @Schema(description = "total count")
    long totalCount,
    @Schema(description = "total page")
    int totalPage,
    @Schema(description = "current page")
    int currentPage,
    @Schema(description = "page size")
    int pageSize,
    @Schema(description = "has next")
    boolean hasNext,
    @Schema(description = "has prev")
    boolean hasPrev) {

    public static <E, D> PageResultDTO<D> toPageDto(PagedList<E> pagedList, Function<E, D> entityToDtoFunction) {

        List<D> resultDto = pagedList.getList().stream().map(entityToDtoFunction).toList();

        return PageResultDTO.<D>builder()
                            .list(resultDto)
                            .totalPage(pagedList.getTotalPageCount())
                            .totalCount(pagedList.getTotalCount())
                            .currentPage(pagedList.getPageIndex())
                            .pageSize(pagedList.getPageSize())
                            .hasNext(pagedList.hasNext())
                            .hasPrev(pagedList.hasPrev())
                            .build();
    }
}
