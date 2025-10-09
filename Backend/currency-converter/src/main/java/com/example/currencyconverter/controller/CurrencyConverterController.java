package com.example.currencyconverter.controller;

import com.example.currencyconverter.client.CurrencyApiClient;
import com.example.currencyconverter.dtos.ConversionHistoryDTO;
import com.example.currencyconverter.dtos.ConversionRequest;
import com.example.currencyconverter.dtos.ConversionResponse;
import com.example.currencyconverter.dtos.CurrencyApiResponse;
import com.example.currencyconverter.entities.ConversionHistory;
import com.example.currencyconverter.repositories.ConversionHistoryRepo;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/converterCurrency")
public class CurrencyConverterController {

    private final CurrencyApiClient client;
    private final ConversionHistoryRepo historyRepo;
    private final ModelMapper mapper;

    public CurrencyConverterController(CurrencyApiClient client,
                                       ConversionHistoryRepo historyRepo,
                                       ModelMapper mapper) {
        this.client = client;
        this.historyRepo = historyRepo;
        this.mapper = mapper;
    }

//    @PostMapping
//    public ConversionResponse convert(@Valid @RequestBody ConversionRequest request) {
//        // Call FreeCurrencyAPI
//        CurrencyApiResponse response = client.getRates(request.getFromCurrency(), request.getToCurrency());
//
//        double rate = response.getData().get(request.getToCurrency().toUpperCase());
//        double converted = request.getUnits() * rate;
//
//        // ✅ Save to DB
//        ConversionHistory history = new ConversionHistory();
//        history.setFromCurrency(request.getFromCurrency().toUpperCase());
//        history.setToCurrency(request.getToCurrency().toUpperCase());
//        history.setUnits(request.getUnits());
//        history.setRates(rate);
//        history.setConvertedAmount(converted);
//        historyRepo.save(history);
//
//        // ✅ Return API response
//        return ConversionResponse.builder()
//                .fromCurrency(history.getFromCurrency())
//                .toCurrency(history.getToCurrency())
//                .units(history.getUnits())
//                .rate(history.getRates())
//                .convertedAmount(history.getConvertedAmount())
//                .provider("freecurrencyapi")
//                .timestamp(System.currentTimeMillis() / 1000)
//                .build();
//    }

    // Example: GET /converterCurrency/fromCurrency=EURtoCurrency=USD&units=5000
    //URL: http://localhost:8080/converterCurrency?fromCurrency=EUR&toCurrency=USD&units=5000
    @GetMapping
    public ConversionResponse convert(
            @RequestParam String fromCurrency,
            @RequestParam String toCurrency,
            @RequestParam double units) {

        // Call FreeCurrencyAPI
        CurrencyApiResponse response = client.getRates(fromCurrency, toCurrency);

        Double rate = response.getData().get(toCurrency.toUpperCase());
        if (rate == null) {
            throw new RuntimeException("Currency not supported: " + toCurrency);
        }

        double converted = units * rate;

        // Save history
        ConversionHistory history = new ConversionHistory();
        history.setFromCurrency(fromCurrency.toUpperCase());
        history.setToCurrency(toCurrency.toUpperCase());
        history.setUnits(units);
        history.setRates(rate);
        history.setConvertedAmount(converted);
        historyRepo.save(history);

        // Return response
        return ConversionResponse.builder()
                .fromCurrency(fromCurrency.toUpperCase())
                .toCurrency(toCurrency.toUpperCase())
                .units(units)
                .rate(rate)
                .convertedAmount(converted)
                .provider("freecurrencyapi")
                .timestamp(System.currentTimeMillis() / 1000)
                .build();
    }
}
