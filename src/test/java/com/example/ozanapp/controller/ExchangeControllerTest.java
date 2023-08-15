package com.example.ozanapp.controller;

import com.example.ozanapp.dto.ExchangeResponse;
import com.example.ozanapp.service.ExchangeService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class ExchangeControllerTest {
    private final ObjectMapper mapper = new ObjectMapper();
    private MockMvc mockMvc;
    @InjectMocks
    private ExchangeController controller;
    @Mock
    private ExchangeService service;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();

    }

    @Test
    void exchange() throws Exception {
        ExchangeResponse exchangeResponse = new ExchangeResponse("111.11");
        when(service.latestRate("EUR", "TRY")).thenReturn(exchangeResponse);
        mockMvc.perform(get("/api/exchange/{base}/{symbol}", "EUR", "TRY")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .accept(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8"))
                .andDo(print())
                .andExpect(jsonPath("$.status").value(200))
                .andExpect(status().isOk());
    }
}