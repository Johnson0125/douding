package com.doubao.douding.system.controller;

import com.doubao.douding.dto.validate.Create;
import com.doubao.douding.dto.validate.Update;
import com.doubao.douding.system.dto.UserInfoDTO;
import com.doubao.douding.system.service.UserInfoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
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
 */
@RestController
@RequestMapping("/userInfo")
@RequiredArgsConstructor
@Tag(name = "userInfoController", description = "userInfo management")
public class UserController {

    private final UserInfoService userInfoService;

    @Operation(summary = "add user", description = "using after login")
    @PostMapping("/add")
    public ResponseEntity<UserInfoDTO> add(@RequestBody @Validated(Create.class) UserInfoDTO userInfoDto) {
        userInfoDto = userInfoService.save(userInfoDto);
        return new ResponseEntity<>(userInfoDto, HttpStatus.CREATED);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserInfoDTO> getUserInfo(@PathVariable("userId") Long userId) {
        Optional<UserInfoDTO> optionalUserInfoDTO = Optional.ofNullable(userInfoService.findById(userId));
        return ResponseEntity.of(optionalUserInfoDTO);
    }

    @PutMapping("/update")
    public ResponseEntity<UserInfoDTO> update(@RequestBody @Validated(Update.class) UserInfoDTO userInfoDto) {
        userInfoDto = userInfoService.update(userInfoDto);
        return ResponseEntity.accepted().body(userInfoDto);
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<Boolean> delete(@PathVariable("userId") Long userId) {
        userInfoService.delete(userId);
        return ResponseEntity.ok(true);
    }

    @GetMapping("/list")
    public ResponseEntity<List<UserInfoDTO>> list() {
        return ResponseEntity.ok(userInfoService.list());
    }
}
