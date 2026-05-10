package org.example.nuxatechprojectbe.common.response;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ResponseHandler {
    public static <T> ResponseEntity<ResponseHelper<T>> generateResponse(T data, String msg, HttpStatus code, boolean success)
    {
        ResponseHelper<T> body = new ResponseHelper<>(success,msg,code.value(),data);

        return new ResponseEntity<>(body, code);
    }

    public static ResponseEntity<ResponseHelper<Object>> generateResponse(String msg, HttpStatus code, boolean success) {
        ResponseHelper<Object> body = new ResponseHelper<>(success, msg, code.value(), null);
        return new ResponseEntity<>(body, code);
    }
}
