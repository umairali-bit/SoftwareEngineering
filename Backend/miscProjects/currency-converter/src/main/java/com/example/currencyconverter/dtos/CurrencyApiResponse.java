package com.example.currencyconverter.dtos;

import lombok.Data;

import java.util.Map;

@Data
public class CurrencyApiResponse {
    private Map<String, Double> data;
}
