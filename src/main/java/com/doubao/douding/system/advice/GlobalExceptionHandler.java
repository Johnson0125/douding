package com.doubao.douding.system.advice;

import com.doubao.douding.exception.DataNotFoundException;
import jakarta.validation.ConstraintViolationException;
import java.util.List;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @Author Johnson
 * @Date
 * @Description: common exception handler
 **/
@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ResponseBody
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)// 设置状态码为500
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public String postExceptionHandler(MethodArgumentNotValidException e) {
        log.error("error occur", e);
        List<ObjectError> allErrors = e.getBindingResult().getAllErrors();
        Optional<ObjectError> objectError = allErrors.stream().findFirst();
        return objectError.get().getDefaultMessage();
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)// 设置状态码为500
    @ExceptionHandler(ConstraintViolationException.class)
    public String paramExceptionHandler(ConstraintViolationException e) {
        log.error("error occur", e);
        return e.getMessage();
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)// 设置状态码为500
    @ExceptionHandler(DataNotFoundException.class)
    public String dataNotFoundException(ConstraintViolationException e) {
        log.error("error occur", e);
        return e.getMessage();
    }

}
