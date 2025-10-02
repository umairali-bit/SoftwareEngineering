package com.example.currencyconverter.dtos;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ConversionResponse {
    private String fromCurrency;
    private String toCurrency;
    private double units;
    private double rate;
    private double convertedAmount;
    private String provider;
    private long timestamp;
}
