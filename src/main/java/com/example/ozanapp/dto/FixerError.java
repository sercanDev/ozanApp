package com.example.ozanapp.dto;

public record FixerError(
        int code,
        String type,
        String info
) {
}
