package com.doubao.douding.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

/**
 * common struct for response of springmvc
 *
 * @author Johnson
 * @date 2023/12/18 22:54
 */

@Getter
@Setter
public class ResultBean<T> {

    public ResultBean() {
        this.code = HttpStatus.OK.value();
    }

    public ResultBean(T data) {
        this.data = data;
        this.code = HttpStatus.OK.value();
    }

    public ResultBean(Integer code) {
        this.code = code;
    }

    public ResultBean(Integer code, T data) {
        this.code = code;
        this.data = data;
    }

    private Integer code;

    private String message;

    private T data;

    public static ResultBean ok() {
        return new ResultBean(HttpStatus.OK.value());
    }

}
