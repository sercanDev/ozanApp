package com.example.ozanapp.entity;

import io.swagger.v3.oas.annotations.Parameter;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import java.util.Date;
import java.util.UUID;

@Entity
@Table(name = "convert")
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Convert {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column(updatable = false, nullable = false)
    private UUID transactionId;
    private String sourceCurrency;
    private String targetCurrency;
    private float sourceAmount;
    private float result;
    private Date transactionDate;

    public Convert(String sourceCurrency, String targetCurrency, float sourceAmount, float result, Date transactionDate) {
        this.sourceCurrency = sourceCurrency;
        this.targetCurrency = targetCurrency;
        this.sourceAmount = sourceAmount;
        this.result = result;
        this.transactionDate = transactionDate;
    }
}
