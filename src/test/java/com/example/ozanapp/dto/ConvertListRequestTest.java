package com.example.ozanapp.dto;

import com.example.ozanapp.exception.ExchangeException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ConvertListRequestTest {

    @Test
    void validate() {
        ConvertListRequest convertListRequest = new ConvertListRequest();
        assertEquals(assertThrows(ExchangeException.class, convertListRequest::validate).getError().info(), "At least one of transactionId or transactionDate must be given");
    }
}