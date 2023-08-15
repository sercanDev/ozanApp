package com.example.ozanapp.dto;

import com.example.ozanapp.exception.ExchangeException;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ConvertListRequest {
    private UUID transactionId;
    private Date transactionDate;
    private PageRequestDTO pageRequestDTO;


    public ConvertListRequest validate() throws ExchangeException {
        if (transactionId == null && transactionDate == null)
            throw new ExchangeException(new FixerError(400, null, "At least one of transactionId or transactionDate must be given"));
        return this;
    }
}
