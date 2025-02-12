package com.crioprogram.stayease.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.http.HttpStatus;

public class ResponseDTO {
    private HttpStatus code;
    private Object data;
    private String message;

    public ResponseDTO(HttpStatus code, Object data, String message) {
        this.code = code;
        this.data = data;
        this.message = message;
    }

    public HttpStatus getCode() {
        return code;
    }

    public Object getData() {
        return data;
    }

    public String getMessage() {
        return message;
    }

    public void setCode(HttpStatus code) {
        this.code = code;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
