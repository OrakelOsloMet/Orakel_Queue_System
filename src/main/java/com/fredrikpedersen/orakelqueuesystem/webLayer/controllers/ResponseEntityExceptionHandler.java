package com.fredrikpedersen.orakelqueuesystem.webLayer.controllers;

import com.fredrikpedersen.orakelqueuesystem.utilities.exceptions.RequiredFieldException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

/**
 * @author Fredrik Pedersen
 * @since 20/01/2021 at 15:07
 */

@ControllerAdvice
public class ResponseEntityExceptionHandler {

    @ExceptionHandler({RequiredFieldException.class})
    public ResponseEntity<Object> handleRequiredFieldException(final Exception exception, final WebRequest webRequest) {
        return  new ResponseEntity<>(String.format("RequiredFieldException: %s", exception.getMessage()), new HttpHeaders(), HttpStatus.NOT_ACCEPTABLE);
    }

}
