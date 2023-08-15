package com.example.ozanapp.controller;

import com.example.ozanapp.dto.ConvertListRequest;
import com.example.ozanapp.dto.ConvertRequest;
import com.example.ozanapp.dto.ConvertResponse;
import com.example.ozanapp.entity.Convert;
import com.example.ozanapp.exception.ExchangeException;
import com.example.ozanapp.service.ConversionService;
import com.example.ozanapp.util.RestResponse;
import com.example.ozanapp.util.RestResponseUtil;
import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/conversion")
@AllArgsConstructor
public class ConversionController {
    private final ConversionService conversionService;

    @PostMapping
    @Operation(summary = "Convert amount of currency from one currency to another", description = "Conversion API")
    public RestResponse<ConvertResponse> conversion(@RequestBody ConvertRequest request) throws ExchangeException {
        return RestResponseUtil.success(conversionService.convert(request));
    }

    @PostMapping("/list")
    @Operation(summary = "Conversion History", description = "Conversion Hisyory API")
    public RestResponse<Page<Convert>> conversionList(@RequestBody ConvertListRequest request) throws ExchangeException {
        return RestResponseUtil.success(conversionService.findConvertByTransactionIdOrTransactionDate(request.validate()));
    }
}
