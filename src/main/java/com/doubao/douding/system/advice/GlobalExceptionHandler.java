package com.doubao.douding.system.advice;

import com.doubao.douding.exception.DataNotFoundException;
import com.doubao.douding.exception.ServiceException;
import jakarta.validation.ConstraintViolationException;
import java.util.List;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author Johnson
 * @date
 * @description: common exception handler
 **/
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<String> postExceptionHandler(MethodArgumentNotValidException e) {
        log.error("error occur", e);
        List<ObjectError> allErrors = e.getBindingResult().getAllErrors();
        Optional<ObjectError> objectError = allErrors.stream().findFirst();

        return new ResponseEntity<>(objectError.get().getDefaultMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<String> paramExceptionHandler(ConstraintViolationException e) {
        log.error("error occur", e);
        return new ResponseEntity<>(e.getMessage(), HttpStatus.EXPECTATION_FAILED);
    }

    @ExceptionHandler(DataNotFoundException.class)
    public ResponseEntity<String> dataNotFoundException(DataNotFoundException e) {
        log.error("error occur", e);
        return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ResponseBody
    @ExceptionHandler(ServiceException.class)
    public ResponseEntity<String> serviceException(ServiceException e) {
        log.error("error occur", e);
        return new ResponseEntity<>(e.getMessage(), HttpStatus.EXPECTATION_FAILED);
    }

}
