package com.ms.orderbookservice.controller;

import com.ms.orderbookservice.constants.ErrorCode;
import com.ms.orderbookservice.data.ErrorResponse;
import com.ms.orderbookservice.exception.ApplicationException;
import com.ms.orderbookservice.exception.InvalidInputException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class GlobalExceptionHandlerTest {

    private GlobalExceptionHandler globalExceptionHandler;

    @BeforeEach
    public void init(){
        globalExceptionHandler = new GlobalExceptionHandler();
    }
    @Test
    public void  test_HandleInvalidInputException(){
        ResponseEntity<Object> responseEntity = globalExceptionHandler.handleInvalidInputException(new InvalidInputException(ErrorCode.ORDBK_101.getErrorCode(), ErrorCode.ORDBK_101.getMessage()));
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertEquals(ErrorCode.ORDBK_101.getErrorCode(), ((ErrorResponse)responseEntity.getBody()).getErrorCode());
        assertEquals(ErrorCode.ORDBK_101.getMessage(), ((ErrorResponse)responseEntity.getBody()).getMessage());

    }

    @Test
    public void  test_HandleApplicationException(){
        ResponseEntity<Object> responseEntity = globalExceptionHandler.handleApplicationException(new ApplicationException(ErrorCode.ORDBK_102.getErrorCode(), ErrorCode.ORDBK_102.getMessage(),new NullPointerException()));
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseEntity.getStatusCode());
        assertEquals(ErrorCode.ORDBK_102.getErrorCode(), ((ErrorResponse)responseEntity.getBody()).getErrorCode());
        assertEquals(ErrorCode.ORDBK_102.getMessage(), ((ErrorResponse)responseEntity.getBody()).getMessage());
    }


//    @Test
//    public void  test_HandleException(){
//        ResponseEntity<Object> responseEntity = globalExceptionHandler.handleException(new Exception());
//        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseEntity.getStatusCode());
//        assertEquals(ErrorCode.ORDBK_100.getErrorCode(), ((ErrorResponse)responseEntity.getBody()).getErrorCode());
//        assertEquals(ErrorCode.ORDBK_100.getMessage(), ((ErrorResponse)responseEntity.getBody()).getMessage());
//
//    }


}
