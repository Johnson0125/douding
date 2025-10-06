package com.doubao.douding.common.exception;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author Johnson
 * @date 2024-03-11
 * @description service exception for business logic
 */
@Getter
@Setter
@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class ServiceException extends RuntimeException {

    private Integer code;

    private String message;

    public ServiceException() {

    }

    public ServiceException(final String errorMsg) {
        this.message = errorMsg;
    }

    public ServiceException(final String errorMsg, Integer code) {
        this.message = errorMsg;
        this.code = code;
    }

}
