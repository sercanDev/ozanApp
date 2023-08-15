package com.example.ozanapp.exception;

import com.example.ozanapp.dto.FixerError;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ExchangeException extends Exception {
    private FixerError error;

}
