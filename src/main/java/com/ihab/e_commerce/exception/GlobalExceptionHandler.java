package com.ihab.e_commerce.exception;


import com.ihab.e_commerce.controller.response.GlobalExceptionResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/** @author Ihab
* */
@RestControllerAdvice
public class GlobalExceptionHandler {

// All Exception are handled here

    @ExceptionHandler
    public ResponseEntity<GlobalExceptionResponse> handleNotFoundException(GlobalNotFoundException globalNotFoundException){

        GlobalExceptionResponse exceptionResponse =
                GlobalExceptionResponse.builder()
                        .date(System.currentTimeMillis())
                        .status(HttpStatus.NOT_FOUND.value())
                        .message(globalNotFoundException.getMessage())
                        .build();
        return new ResponseEntity<>(exceptionResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    public ResponseEntity<GlobalExceptionResponse> handleIllegalArgException(IllegalArgumentException exception){

        GlobalExceptionResponse exceptionResponse =
                GlobalExceptionResponse.builder()
                        .date(System.currentTimeMillis())
                        .status(HttpStatus.BAD_REQUEST.value())
                        .message(exception.getMessage())
                        .build();
        return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
    }

}
