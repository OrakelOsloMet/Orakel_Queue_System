package com.orakeloslomet.web.controllers;

import com.orakeloslomet.utilities.exceptions.NoSuchPersistedEntityException;
import com.orakeloslomet.utilities.exceptions.RequiredFieldException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.util.NoSuchElementException;

/**
 * Take a look here for info on error responses in Spring Boot https://spring.io/blog/2013/11/01/exception-handling-in-spring-mvc,
 * specifically the chapter about global exception handling.
 *
 * @author Fredrik Pedersen
 * @since 20/01/2021 at 15:07
 */

@ControllerAdvice
public class ResponseEntityExceptionHandler {

    @ExceptionHandler({RequiredFieldException.class})
    public ResponseEntity<Object> handleRequiredFieldException(final Exception exception, final WebRequest webRequest) {
        return new ResponseEntity<>(String.format("RequiredFieldException: %s", exception.getMessage()), new HttpHeaders(), HttpStatus.NOT_ACCEPTABLE);
    }

    @ExceptionHandler({NoSuchPersistedEntityException.class})
    public ResponseEntity<Object> handleNoSuchPersistedEntityException(final Exception exception, final WebRequest webRequest) {
        return new ResponseEntity<>(exception.getMessage(), new HttpHeaders(), HttpStatus.NOT_ACCEPTABLE);
    }

    @ExceptionHandler({AccessDeniedException.class})
    public ResponseEntity<Object> handleAccessDeniedException(final Exception exception, final WebRequest webRequest) {
        return new ResponseEntity<>(exception, new HttpHeaders(), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler({Exception.class})
    public ResponseEntity<Object> handleException(final Exception exception, final WebRequest webRequest) {
        return new ResponseEntity<>("An Unexpected Error Occurred: " + exception, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
