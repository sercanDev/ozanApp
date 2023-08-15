package com.example.ozanapp.dto;

public record ConvertFixerResponse(
        boolean success,
        String date,
        float result,
        ConvertQuery query,
        ConvertInfo info,
        FixerError error
) {
}
