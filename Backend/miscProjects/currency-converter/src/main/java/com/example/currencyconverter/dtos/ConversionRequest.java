package com.example.currencyconverter.dtos;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class ConversionRequest {

    @NotBlank
    @Pattern(regexp = "^[A-Z]{3}$", message = "Currency code must be a 3-letter ISO code")
    private String fromCurrency;

    @NotBlank
    @Pattern(regexp = "^[A-Z]{3}$", message = "Currency code must be a 3-letter ISO code")
    private String toCurrency;

    @Min(value = 1, message = "Units must be at least 1")
    private double units;
}
