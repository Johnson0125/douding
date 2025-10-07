package com.doubao.douding.system.controller;

import com.doubao.douding.common.dto.PageResultDTO;
import com.doubao.douding.common.dto.validate.Create;
import com.doubao.douding.common.dto.validate.Update;
import com.doubao.douding.system.dto.SysResourceDTO;
import com.doubao.douding.system.dto.SysRoleDTO;
import com.doubao.douding.system.service.SysResourceService;
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

@RestController
@RequestMapping("/sysResource")
@RequiredArgsConstructor
@Tag(name = "sysResourceController", description = "SysResource management")
public class SysResourceController {

    private final SysResourceService sysResourceService;

    @PostMapping("/add")
    @Operation(summary = "add sysResource")
    public ResponseEntity<SysResourceDTO> add(@RequestBody @Validated(Create.class) SysResourceDTO sysResourceDTO) {
        sysResourceDTO = sysResourceService.save(sysResourceDTO);
        return new ResponseEntity<>(sysResourceDTO, HttpStatus.CREATED);
    }

    @PutMapping("/update")
    @Operation(summary = "update sysResource")
    public ResponseEntity<SysResourceDTO> update(@RequestBody @Validated(Update.class) SysResourceDTO sysResourceDTO) {
        sysResourceDTO = sysResourceService.update(sysResourceDTO);
        return ResponseEntity.accepted().body(sysResourceDTO);
    }

    @DeleteMapping("/delete/{id}")
    @Operation(summary = "delete sysResource")
    public ResponseEntity<Boolean> delete(@PathVariable("id") Long id) {
        sysResourceService.delete(id);
        return ResponseEntity.ok(true);
    }

    @GetMapping("/{id}")
    @Operation(summary = "get sysResource")
    public ResponseEntity<SysResourceDTO> get(@PathVariable("id") Long id) {
        Optional<SysResourceDTO> optional = Optional.ofNullable(sysResourceService.findById(id));
        return ResponseEntity.of(optional);
    }

    @PostMapping("/page")
    @Operation(summary = "page select")
    public ResponseEntity<PageResultDTO<SysResourceDTO>> page(@RequestBody SysResourceDTO sysResourceDTO) {
        return ResponseEntity.ok(sysResourceService.page(sysResourceDTO));
    }
}
