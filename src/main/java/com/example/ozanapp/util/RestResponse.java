package com.example.ozanapp.util;

public record RestResponse<T>(int status, String message, T payload) {

}
