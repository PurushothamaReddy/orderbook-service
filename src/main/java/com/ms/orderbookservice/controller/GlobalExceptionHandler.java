package com.ms.orderbookservice.controller;

import com.ms.orderbookservice.constants.ErrorCode;
import com.ms.orderbookservice.data.ErrorResponse;
import com.ms.orderbookservice.exception.ApplicationException;
import com.ms.orderbookservice.exception.InvalidInputException;
import com.ms.orderbookservice.util.OrderBookUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(InvalidInputException.class)
    public ResponseEntity<Object> handleInvalidInputException(InvalidInputException invalidInputException) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse(invalidInputException.getErrorCode(), invalidInputException.getMessage(), OrderBookUtil.getStackTraceString(invalidInputException)));
    }

    @ExceptionHandler(ApplicationException.class)
    public ResponseEntity<Object> handleApplicationException(ApplicationException applicationException) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorResponse(applicationException.getErrorCode(), applicationException.getMessage(), OrderBookUtil.getStackTraceString(applicationException)));
    }

//    @ExceptionHandler(Exception.class)
//    protected ResponseEntity<Object> handleException(Exception exception) {
//        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorResponse(ErrorCode.ORDBK_100.getErrorCode(), ErrorCode.ORDBK_100.getMessage(), OrderBookUtil.getStackTraceString(exception)));
//    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex,
            HttpHeaders headers, HttpStatus status, WebRequest request) {

        Map<String, Object> responseBody = new LinkedHashMap<>();
        responseBody.put("timestamp", new Date());
        responseBody.put("status", status.value());

        List<String> errors = ex.getBindingResult().getFieldErrors()
                .stream()
                .map(x -> x.getDefaultMessage())
                .collect(Collectors.toList());

        responseBody.put("errors", errors);

        return new ResponseEntity<>(responseBody, headers, status);
    }
}
