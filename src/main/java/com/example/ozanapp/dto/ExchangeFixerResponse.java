package com.example.ozanapp.dto;

import java.util.Map;

public record ExchangeFixerResponse(
        boolean success,
        long timestamp,
        String base,
        String date,
        Map<String, String> rates,
        FixerError error
) {
}
