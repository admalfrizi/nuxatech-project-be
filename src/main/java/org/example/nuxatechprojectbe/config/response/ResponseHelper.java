package org.example.nuxatechprojectbe.config.response;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ResponseHelper<T> {

    private boolean success;
    private String message;
    private int code;
    private T data;

    public ResponseHelper(boolean success, String message, int code, T data) {
        this.success = success;
        this.message = message;
        this.code = code;
        this.data = data;
    }

}