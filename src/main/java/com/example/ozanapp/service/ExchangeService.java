package com.example.ozanapp.service;

import com.example.ozanapp.dto.ExchangeFixerResponse;
import com.example.ozanapp.dto.ExchangeResponse;
import com.example.ozanapp.dto.FixerError;
import com.example.ozanapp.exception.ExchangeException;
import com.example.ozanapp.repository.ConvertRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import static com.example.ozanapp.util.Commons.ACCESS_KEY;
import static com.example.ozanapp.util.Commons.URL;

@Service
@AllArgsConstructor
public class ExchangeService {
    private final RestTemplate restTemplate;

    public ExchangeResponse latestRate(String base, String symbol) throws ExchangeException {
        ExchangeFixerResponse response = restTemplate.getForObject(URL + "latest?" + ACCESS_KEY + "&base=" + base + "&symbols=" + symbol, ExchangeFixerResponse.class);
        if (response == null)
            throw new ExchangeException(new FixerError(500, null, "No response from fixer.io"));

        if (response.success())
            return new ExchangeResponse(response.rates().get(symbol));
        else
            throw new ExchangeException(response.error());
    }
}
