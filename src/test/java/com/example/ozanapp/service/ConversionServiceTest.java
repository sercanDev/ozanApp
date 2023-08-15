package com.example.ozanapp.service;

import com.example.ozanapp.dto.*;
import com.example.ozanapp.entity.Convert;
import com.example.ozanapp.exception.ExchangeException;
import com.example.ozanapp.repository.ConvertRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.web.client.RestTemplate;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ConversionServiceTest {
    @InjectMocks
    private ConversionService service;
    @Mock
    private RestTemplate restTemplate;

    @Mock
    private ConvertRepository convertRepository;

    @Test
    @DisplayName("convert: success")
    void convert() throws ExchangeException {
        when(restTemplate.getForObject("http://data.fixer.io/api/convert?access_key=f023c4b30ae7ed2adc50eeccdee57d8d&from=EUR&to=TRY&amount=111.11", ConvertFixerResponse.class))
                .thenReturn(new ConvertFixerResponse(true, "2023-08-14", 111.11f, new ConvertQuery("EUR", "TRY", 1.111f), new ConvertInfo("1519328414", "148.972231"), null));
        when(convertRepository.save(any(Convert.class))).thenReturn(new Convert(UUID.randomUUID(), "EUR", "TRY", 111.11f, 1.111f, new Date()));
        ConvertResponse convertResponse = service.convert(new ConvertRequest(111.11f, "EUR", "TRY"));
        assertNotNull(convertResponse.transactionId());
    }

    @Test
    @DisplayName("convert: return null")
    void convertRN() {
        when(restTemplate.getForObject("http://data.fixer.io/api/convert?access_key=f023c4b30ae7ed2adc50eeccdee57d8d&from=EUR&to=TRY&amount=111.11", ConvertFixerResponse.class))
                .thenReturn(null);
        ExchangeException exchangeException = assertThrows(ExchangeException.class, () -> service.convert(new ConvertRequest(111.11f, "EUR", "TRY")));
        assertEquals(exchangeException.getError().code(), 500);
        assertNull(exchangeException.getError().type());
        assertEquals(exchangeException.getError().info(), "No response from fixer.io");
    }

    @Test
    @DisplayName("convert: return error")
    void convertRE() {
        when(restTemplate.getForObject("http://data.fixer.io/api/convert?access_key=f023c4b30ae7ed2adc50eeccdee57d8d&from=EUR&to=TRY&amount=111.11", ConvertFixerResponse.class))
                .thenReturn(new ConvertFixerResponse(false, null, 111.11f, null, null, new FixerError(105, "function_access_restricted", "Access Restricted - Your current Subscription Plan does not support this API Function.")));
        ExchangeException exchangeException = assertThrows(ExchangeException.class, () -> service.convert(new ConvertRequest(111.11f, "EUR", "TRY")));
        assertEquals(exchangeException.getError().code(), 105);
        assertEquals(exchangeException.getError().type(), "function_access_restricted");
        assertEquals(exchangeException.getError().info(), "Access Restricted - Your current Subscription Plan does not support this API Function.");
    }


    @Test
    @DisplayName("findConvertByTransactionIdOrTransactionDate: success")
    void findConvertByTransactionIdOrTransactionDate() throws ExchangeException {
        when(convertRepository.findByTransactionIdOrTransactionDate(any(UUID.class), any(Date.class), any()))
                .thenReturn(Optional.of(new PageImpl<>(List.of(new Convert(UUID.randomUUID(), "EUR", "TRY", 111.11f, 1.111f, new Date())))));
        assertNotNull(service.findConvertByTransactionIdOrTransactionDate(new ConvertListRequest(UUID.randomUUID(), new Date(), new PageRequestDTO(0, 10))));
    }


    @Test
    @DisplayName("findConvertByTransactionIdOrTransactionDate: return null")
    void findConvertByTransactionIdOrTransactionDateRN() {
        when(convertRepository.findByTransactionIdOrTransactionDate(any(UUID.class), any(Date.class), any()))
                .thenReturn(Optional.empty());
        assertThrows(ExchangeException.class, () -> service.findConvertByTransactionIdOrTransactionDate(new ConvertListRequest(UUID.randomUUID(), new Date(), new PageRequestDTO(0, 10))));
    }
}