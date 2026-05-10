package org.example.nuxatechprojectbe.config.response;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ResponseHandler {
    public static <T> ResponseEntity<ResponseHelper<T>> generateResponse(T data, String msg, HttpStatus code, boolean success)
    {
        ResponseHelper<T> body = new ResponseHelper<>(success,msg,code.value(),data);

        return new ResponseEntity<>(body, code);
    }
}
