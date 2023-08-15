package com.example.ozanapp.exception;

import com.example.ozanapp.util.RestResponse;
import com.example.ozanapp.util.RestResponseUtil;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
@Log4j2
public class ResponseExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(Exception.class)
    protected ResponseEntity<RestResponse<?>> handleGenericExceptions(Exception exception) {
        logger.error("====== EXCEPTION HANDLER ======");
        if (exception instanceof ExchangeException exchangeException) {
            logger.error("A new exception has been delivered  info: " + exchangeException.getError().info());
            logger.error("A new exception has been delivered type: " + exchangeException.getError().type());
            logger.error("A new exception has been delivered code: " + exchangeException.getError().code());
            return ResponseEntity.ok(RestResponseUtil.error(exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value(), exchangeException.getError()));

        }
        logger.error("A new exception has been delivered", exception);
        return ResponseEntity.ok(RestResponseUtil.error(exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value(), null));
    }

}
