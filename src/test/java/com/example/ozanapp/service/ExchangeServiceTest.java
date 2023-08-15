package com.example.ozanapp.service;

import com.example.ozanapp.dto.ExchangeFixerResponse;
import com.example.ozanapp.dto.FixerError;
import com.example.ozanapp.exception.ExchangeException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ExchangeServiceTest {
    @InjectMocks
    private ExchangeService exchangeService;
    @Mock
    private RestTemplate restTemplate;

    @Test
    @DisplayName("success")
    void latestRate() throws ExchangeException {
        when(restTemplate.getForObject("http://data.fixer.io/api/latest?access_key=f023c4b30ae7ed2adc50eeccdee57d8d&base=EUR&symbols=TRY", ExchangeFixerResponse.class))
                .thenReturn(new ExchangeFixerResponse(true, 1692036063L, "EUR", "2023-08-14", Map.of("TRY", "29.487461"), null));
        assertEquals(exchangeService.latestRate("EUR", "TRY").exchangeRate(), "29.487461");
    }

    @Test
    @DisplayName("return null")
    void latestRateRN() {
        when(restTemplate.getForObject("http://data.fixer.io/api/latest?access_key=f023c4b30ae7ed2adc50eeccdee57d8d&base=EUR&symbols=TRY", ExchangeFixerResponse.class))
                .thenReturn(null);
        ExchangeException exchangeException = assertThrows(ExchangeException.class, () -> exchangeService.latestRate("EUR", "TRY"));
        assertEquals(exchangeException.getError().code(), 500);
        assertNull(exchangeException.getError().type());
        assertEquals(exchangeException.getError().info(), "No response from fixer.io");
    }

    @Test
    @DisplayName("return error")
    void latestRateRE() {
        when(restTemplate.getForObject("http://data.fixer.io/api/latest?access_key=f023c4b30ae7ed2adc50eeccdee57d8d&base=EUR&symbols=TRY", ExchangeFixerResponse.class))
                .thenReturn(new ExchangeFixerResponse(false, 0L, null, null, null, new FixerError(201, "invalid_base_currency", "An invalid base currency has been entered.")));
        ExchangeException exchangeException = assertThrows(ExchangeException.class, () -> exchangeService.latestRate("EUR", "TRY"));
        assertEquals(exchangeException.getError().code(), 201);
        assertEquals(exchangeException.getError().type(), "invalid_base_currency");
        assertEquals(exchangeException.getError().info(), "An invalid base currency has been entered.");
    }
}
