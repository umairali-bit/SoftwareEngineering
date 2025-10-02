package com.example.currencyconverter.dtos;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ConversionHistoryDTO {
    private Long id;
    private String fromCurrency;
    private String toCurrency;
    private double units;
    private double rate;
    private double convertedAmount;
    private String createdBy;
    private LocalDateTime createdAt;
}
