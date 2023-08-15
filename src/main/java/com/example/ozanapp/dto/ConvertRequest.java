package com.example.ozanapp.dto;

import io.swagger.v3.oas.annotations.Parameter;

public record ConvertRequest(
        @Parameter(name = "sourceAmount", description = "The currency amount to convert")
        float sourceAmount,

        @Parameter(name = "sourceCurrency", description = "The currency code to convert from")
        String sourceCurrency,

        @Parameter(name = "targetCurrency", description = "The currency code to convert to")
        String targetCurrency
) {
}
