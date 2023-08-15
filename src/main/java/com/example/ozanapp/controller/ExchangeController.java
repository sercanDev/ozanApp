package com.example.ozanapp.controller;

import com.example.ozanapp.dto.ExchangeResponse;
import com.example.ozanapp.exception.ExchangeException;
import com.example.ozanapp.service.ExchangeService;
import com.example.ozanapp.util.RestResponse;
import com.example.ozanapp.util.RestResponseUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/exchange")
@AllArgsConstructor
public class ExchangeController {
    private final ExchangeService service;

    @GetMapping("/{base}/{symbol}")
    @Operation(summary = "Get latest exchange rate", description = "Exchange Rate API")
    public RestResponse<ExchangeResponse> exchange(@Parameter(name = "base", description = "currency base") @PathVariable("base") String base, @Parameter(name = "symbol", description = "currency symbol") @PathVariable("symbol") String symbol) throws ExchangeException {
        return RestResponseUtil.success(service.latestRate(base, symbol));
    }
}
