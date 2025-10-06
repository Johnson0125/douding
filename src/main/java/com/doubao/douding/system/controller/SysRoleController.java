package com.doubao.douding.system.controller;

import com.doubao.douding.common.dto.PageResultDTO;
import com.doubao.douding.common.dto.validate.Create;
import com.doubao.douding.common.dto.validate.Update;
import com.doubao.douding.system.dto.SysRoleDTO;
import com.doubao.douding.system.service.SysRoleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Johnson
 * @Description SysRole management
 */
@RestController
@RequestMapping("/sysRole")
@RequiredArgsConstructor
@Tag(name = "sysRoleController", description = "SysRole management")
public class SysRoleController {

    private final SysRoleService sysRoleService;

    @PostMapping("/add")
    @Operation(summary = "add sysRole")
    public ResponseEntity<SysRoleDTO> add(@RequestBody @Validated(Create.class) SysRoleDTO sysRoleDTO) {
        sysRoleDTO = sysRoleService.add(sysRoleDTO);
        return new ResponseEntity<>(sysRoleDTO, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    @Operation(summary = "get by id")
    public ResponseEntity<SysRoleDTO> get(@PathVariable("id") Long id) {
        Optional<SysRoleDTO> optional = Optional.ofNullable(sysRoleService.findById(id));
        return ResponseEntity.of(optional);
    }

    @PostMapping("/page")
    @Operation(summary = "page select")
    public ResponseEntity<PageResultDTO<SysRoleDTO>> page(@RequestBody SysRoleDTO sysRoleDTO) {
        return ResponseEntity.ok(sysRoleService.page(sysRoleDTO));
    }

    @PutMapping("/update")
    @Operation(summary = "update")
    public ResponseEntity<SysRoleDTO> update(@RequestBody @Validated(Update.class) SysRoleDTO sysRoleDTO) {
        sysRoleDTO = sysRoleService.update(sysRoleDTO);
        return ResponseEntity.accepted().body(sysRoleDTO);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "delete by id")
    public ResponseEntity<Boolean> delete(@PathVariable("id") Long id) {
        sysRoleService.delete(id);
        return ResponseEntity.ok(true);
    }
}
