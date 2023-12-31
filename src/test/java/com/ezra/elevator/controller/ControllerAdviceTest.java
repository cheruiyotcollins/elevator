package com.ezra.elevator.controller;

import com.ezra.elevator.dto.ResponseDto;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;


@SpringJUnitConfig
@SpringBootTest
class ControllerAdviceTest {


    @Test
    void testHandledExceptionReturnsNotFound() {
        ControllerAdvice controllerAdvice = new ControllerAdvice();
        ResponseEntity<ResponseDto> actualHandledExceptionResult = controllerAdvice
                .handledException(new NoSuchElementException("foo"));
        assertTrue(actualHandledExceptionResult.hasBody());
        assertTrue(actualHandledExceptionResult.getHeaders().isEmpty());
        assertEquals(404, actualHandledExceptionResult.getStatusCode().value());
        ResponseDto body = actualHandledExceptionResult.getBody();
        assertNull(body.getPayload());
        assertEquals("foo", body.getDescription());
        assertEquals(HttpStatus.UNAUTHORIZED, body.getStatus());
    }


    @Test
    void testHandleHttpExceptionsReturnsBadRequest() {
        ControllerAdvice controllerAdvice = new ControllerAdvice();
        ResponseEntity<ResponseDto> actualHandleHttpExceptionsResult = controllerAdvice
                .handleHttpExceptions(new Exception("foo"));
        assertTrue(actualHandleHttpExceptionsResult.hasBody());
        assertTrue(actualHandleHttpExceptionsResult.getHeaders().isEmpty());
        assertEquals(400, actualHandleHttpExceptionsResult.getStatusCode().value());
        ResponseDto body = actualHandleHttpExceptionsResult.getBody();
        assertNull(body.getPayload());
        assertEquals("foo", body.getDescription());
        assertEquals(HttpStatus.UNAUTHORIZED, body.getStatus());
    }






}

