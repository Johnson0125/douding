package com.doubao.douding.system.dto;

import com.doubao.douding.common.dto.BaseDTO;
import com.doubao.douding.common.dto.validate.Create;
import com.doubao.douding.common.dto.validate.Update;
import com.doubao.douding.system.entity.SysResource;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.validator.constraints.Range;

@EqualsAndHashCode(callSuper = true)
@SuperBuilder
@AllArgsConstructor
@Data
@NoArgsConstructor
@Schema
public class SysResourceDTO extends BaseDTO {

    @Schema(title = "parent id")
    private SysResource parent;

    @NotNull(groups = {
        Create.class,
        Update.class
    })
    @Size(groups = {
        Create.class,
        Update.class
    }, max = 20, message = "resource name length should less than 20")
    @Schema(title = "resource name")
    private String resourceName;

    @Schema(title = "resource type, 1:dir,2:menu,3:button")
    @Range(min = 1, max = 3, groups = {
        Create.class,
        Update.class
    }, message = "resource type, 1:dir,2:menu,3:button")
    private Integer resourceType;

    @Size(groups = {
        Create.class,
        Update.class
    }, max = 50, message = "resource url length should less than 50")
    @Schema(title = "resource url")
    private String url;

    @Schema(title = "resource status, 1:normal,0:disabled")
    @Range(min = 0, max = 1, groups = {
        Create.class,
        Update.class
    }, message = "resource status, 1:normal,0:disabled")
    private Integer resourceStatus;

    @Schema(title = "permission")
    @Size(groups = {
        Create.class,
        Update.class
    }, max = 100, message = "permission length should less than 100")
    private String permission;

    @Size(groups = {
        Create.class,
        Update.class
    }, max = 200, message = "remark length should less than 200")
    @Schema(title = "remark")
    private String remark;
}
