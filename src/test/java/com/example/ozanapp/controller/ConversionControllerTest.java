package com.example.ozanapp.controller;

import com.example.ozanapp.dto.ConvertListRequest;
import com.example.ozanapp.dto.ConvertRequest;
import com.example.ozanapp.dto.ConvertResponse;
import com.example.ozanapp.dto.PageRequestDTO;
import com.example.ozanapp.entity.Convert;
import com.example.ozanapp.exception.ExchangeException;
import com.example.ozanapp.service.ConversionService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class ConversionControllerTest {
    private final ObjectMapper mapper = new ObjectMapper();
    private MockMvc mockMvc;
    @InjectMocks
    private ConversionController controller;
    @Mock
    private ConversionService service;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();

    }

    @Test
    void conversion() throws Exception {
        ConvertRequest convertRequest = new ConvertRequest(111.11f, "EUR", "TRY");
        ConvertResponse convertResponse = new ConvertResponse(UUID.randomUUID(), 111.11f);
        when(service.convert(convertRequest)).thenReturn(convertResponse);
        mockMvc.perform(post("/api/conversion/")
                        .content(mapper.writeValueAsString(convertRequest))
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .accept(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8"))
                .andDo(print())
                .andExpect(jsonPath("$.status").value(200))
                .andExpect(status().isOk());
    }

    @Test
    void conversionList() throws Exception {
        ConvertListRequest convertListRequest = new ConvertListRequest(UUID.randomUUID(), new Date(), new PageRequestDTO(0, 10));
        PageImpl<Convert> response = new PageImpl<>(List.of(new Convert(UUID.randomUUID(), "EUR", "TRY", 111.11f, 1.111f, new Date())));
        when(service.findConvertByTransactionIdOrTransactionDate(convertListRequest)).thenReturn(response);
        mockMvc.perform(post("/api/conversion/list")
                        .content(mapper.writeValueAsString(convertListRequest))
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .accept(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8"))
                .andDo(print())
                .andExpect(jsonPath("$.status").value(200))
                .andExpect(status().isOk());
    }
}