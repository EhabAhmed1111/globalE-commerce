package com.ihab.e_commerce.exception;


import com.ihab.e_commerce.rest.response.GlobalExceptionResponse;
import com.stripe.exception.SignatureVerificationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.io.IOException;

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
    public ResponseEntity<GlobalExceptionResponse> handleUnauthorizedActionException(GlobalUnauthorizedActionException globalUnauthorizedActionException){

        GlobalExceptionResponse exceptionResponse =
                GlobalExceptionResponse.builder()
                        .date(System.currentTimeMillis())
                        .status(HttpStatus.CONFLICT.value())
                        .message(globalUnauthorizedActionException.getMessage())
                        .build();
        return new ResponseEntity<>(exceptionResponse, HttpStatus.CONFLICT);
    }
    @ExceptionHandler
    public ResponseEntity<GlobalExceptionResponse> handleConflictException(GlobalConflictException globalConflictException){

        GlobalExceptionResponse exceptionResponse =
                GlobalExceptionResponse.builder()
                        .date(System.currentTimeMillis())
                        .status(HttpStatus.CONFLICT.value())
                        .message(globalConflictException.getMessage())
                        .build();
        return new ResponseEntity<>(exceptionResponse, HttpStatus.CONFLICT);
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

    @ExceptionHandler
    public ResponseEntity<GlobalExceptionResponse> handleIOException(IOException exception){

        GlobalExceptionResponse exceptionResponse =
                GlobalExceptionResponse.builder()
                        .date(System.currentTimeMillis())
                        .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                        .message(exception.getMessage())
                        .build();
        return new ResponseEntity<>(exceptionResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler
    public ResponseEntity<GlobalExceptionResponse> handleRunTimeException(RuntimeException exception){

        GlobalExceptionResponse exceptionResponse =
                GlobalExceptionResponse.builder()
                        .date(System.currentTimeMillis())
                        .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                        .message(exception.getMessage())
                        .build();
        return new ResponseEntity<>(exceptionResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }
   @ExceptionHandler
    public ResponseEntity<GlobalExceptionResponse> handleSignatureVerificationException(SignatureVerificationException exception){

        GlobalExceptionResponse exceptionResponse =
                GlobalExceptionResponse.builder()
                        .date(System.currentTimeMillis())
                        .status(HttpStatus.BAD_REQUEST.value())
                        .message(exception.getMessage())
                        .build();
        return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public ResponseEntity<GlobalExceptionResponse> handlePaymentFailed(PaymentFailedException exception){

        GlobalExceptionResponse exceptionResponse =
                GlobalExceptionResponse.builder()
                        .date(System.currentTimeMillis())
                        .status(HttpStatus.BAD_REQUEST.value())
                        .message(exception.getMessage())
                        .build();
        return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
    }

}
