package org.example.nuxatechprojectbe.common;

import jakarta.servlet.http.HttpServletRequest;
import org.example.nuxatechprojectbe.common.response.ResponseHandler;
import org.example.nuxatechprojectbe.common.response.ResponseHelper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;

public class GlobalExceptionHandler {

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ResponseHelper<Object>> handleNotFound(RuntimeException ex, HttpServletRequest request) {
        return ResponseHandler.generateResponse(
                "Not Found",
                HttpStatus.NOT_FOUND,
                false
        );
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ResponseHelper<Object>> handleGeneralError(Exception ex, HttpServletRequest request) {

        return ResponseHandler.generateResponse(
                "Internal Server Error",
                HttpStatus.INTERNAL_SERVER_ERROR,
                false
        );
    }
}
