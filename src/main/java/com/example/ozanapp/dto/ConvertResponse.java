package com.example.ozanapp.dto;

import io.swagger.v3.oas.annotations.Parameter;

import java.util.UUID;

public record ConvertResponse(
        @Parameter(description = "The transaction id", example = "a1b2c3d4-e5f6-a7b8-c9d0-e1f2a3b4c5d6")
        UUID transactionId,

        @Parameter(description = "Converted currency amount result", example = "10000000")
        float amount
) {

}
