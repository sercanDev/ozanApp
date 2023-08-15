package com.example.ozanapp.dto;

public record ConvertQuery(
        String from,
        String to,
        float amount
) {
}
