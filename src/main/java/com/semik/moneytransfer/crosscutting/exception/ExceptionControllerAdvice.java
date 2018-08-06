package com.semik.moneytransfer.crosscutting.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
@Slf4j
public class ExceptionControllerAdvice {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> ex(HttpServletRequest req, MethodArgumentNotValidException ex) {
        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
        return new ResponseEntity<>(new ErrorMessage(httpStatus, ex), httpStatus);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> ex(HttpServletRequest req, Exception ex) {
        log.error("Unknown error", ex);
        return new ResponseEntity<>("Unknown error", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<Object> ex(HttpServletRequest req, BusinessException ex) {
        HttpStatus status = HttpStatus.PRECONDITION_FAILED;
        return new ResponseEntity<>(new ErrorMessage(status, ex), status);
    }
}
