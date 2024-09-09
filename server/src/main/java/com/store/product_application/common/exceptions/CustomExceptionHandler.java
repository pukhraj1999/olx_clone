package com.store.product_application.common.exceptions;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/*
 * @author Pukhraj Singh
 * @version 1.0
 */
@ControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(HibernateException.class)
    public ResponseEntity<Object> handleHibernateExceptions(
            HibernateException ex) {
        Map<String, Object> body = new HashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("error", ex.getMessage());

        return new ResponseEntity<Object>(body, HttpStatus.BAD_REQUEST);
    }
}
