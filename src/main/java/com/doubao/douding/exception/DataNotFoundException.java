package com.doubao.douding.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @Author Johnson
 * @Date 2024/1/9-23:54
 * @Description: data not found exception
 **/
@ResponseStatus(HttpStatus.NOT_FOUND)
public class DataNotFoundException extends RuntimeException {
}
