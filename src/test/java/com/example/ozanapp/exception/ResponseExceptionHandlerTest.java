package com.example.ozanapp.exception;

import com.example.ozanapp.dto.FixerError;
import com.example.ozanapp.util.RestResponseUtil;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class ResponseExceptionHandlerTest {

    @InjectMocks
    private ResponseExceptionHandler responseExceptionHandler;

    @Test
    void handleGenericExceptions() {
        Exception exception = new Exception();
        assertEquals(responseExceptionHandler.handleGenericExceptions(exception), ResponseEntity.ok(RestResponseUtil.error(exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value(), null)));

        ExchangeException exchangeException = new ExchangeException(new FixerError(201, "invalid_base_currency", "An invalid base currency has been entered."));
        assertEquals(responseExceptionHandler.handleGenericExceptions(exchangeException), ResponseEntity.ok(RestResponseUtil.error(exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value(), exchangeException.getError())));

    }
}