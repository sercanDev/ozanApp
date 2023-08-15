package com.example.ozanapp.util;

import org.springframework.http.HttpStatus;

public class RestResponseUtil {

    public static <T> RestResponse<T> success(T payload, String message) {
        return new RestResponse<>(HttpStatus.OK.value(), message, payload);
    }

    public static <T> RestResponse<T> error(String message, int status, T payload) {
        return new RestResponse<>(status, message, payload);
    }

    public static <T> RestResponse<T> success(T payload) {
        return success(payload, HttpStatus.OK.getReasonPhrase());
    }

}
