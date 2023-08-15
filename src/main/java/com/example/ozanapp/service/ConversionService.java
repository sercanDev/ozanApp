package com.example.ozanapp.service;

import com.example.ozanapp.dto.*;
import com.example.ozanapp.entity.Convert;
import com.example.ozanapp.exception.ExchangeException;
import com.example.ozanapp.repository.ConvertRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Date;

import static com.example.ozanapp.util.Commons.ACCESS_KEY;
import static com.example.ozanapp.util.Commons.URL;

@Service
@AllArgsConstructor
public class ConversionService {
    private final RestTemplate restTemplate;
    private final ConvertRepository convertRepository;

    public ConvertResponse convert(ConvertRequest request) throws ExchangeException {
        ConvertFixerResponse response = restTemplate.getForObject(URL + "convert?" + ACCESS_KEY + "&from=" + request.sourceCurrency() + "&to=" + request.targetCurrency() + "&amount=" + request.sourceAmount(), ConvertFixerResponse.class);
        if (response == null)
            throw new ExchangeException(new FixerError(500, null, "No response from fixer.io"));

        if (response.success()) {
            Convert savedTransaction = convertRepository.save(new Convert(request.sourceCurrency(), request.targetCurrency(), request.sourceAmount(), response.result(), new Date()));
            return new ConvertResponse(savedTransaction.getTransactionId(), savedTransaction.getSourceAmount());
        } else
            throw new ExchangeException(response.error());
    }

    public Page<Convert> findConvertByTransactionIdOrTransactionDate(ConvertListRequest request) throws ExchangeException {
        return convertRepository.findByTransactionIdOrTransactionDate(request.getTransactionId(), request.getTransactionDate(), request.getPageRequestDTO().buildAsPageRequest())
                .orElseThrow(() -> new ExchangeException(new FixerError(404, null, "No transaction found")));
    }
}
