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

    @PostMapping
    public ConversionResponse convert(@Valid @RequestBody ConversionRequest request) {
        // Call FreeCurrencyAPI
        CurrencyApiResponse response = client.getRates(request.getFromCurrency(), request.getToCurrency());

        double rate = response.getData().get(request.getToCurrency().toUpperCase());
        double converted = request.getUnits() * rate;

        // ✅ Save to DB
        ConversionHistory history = new ConversionHistory();
        history.setFromCurrency(request.getFromCurrency().toUpperCase());
        history.setToCurrency(request.getToCurrency().toUpperCase());
        history.setUnits(request.getUnits());
        history.setRates(rate);
        history.setConvertedAmount(converted);
        historyRepo.save(history);

        // ✅ Return API response
        return ConversionResponse.builder()
                .fromCurrency(history.getFromCurrency())
                .toCurrency(history.getToCurrency())
                .units(history.getUnits())
                .rate(history.getRates())
                .convertedAmount(history.getConvertedAmount())
                .provider("freecurrencyapi")
                .timestamp(System.currentTimeMillis() / 1000)
                .build();
    }

    @GetMapping("/history")
    public List<ConversionHistoryDTO> getHistory() {
        return historyRepo.findAll()
                .stream()
                .map(h -> mapper.map(h, ConversionHistoryDTO.class))
                .collect(Collectors.toList());
    }
}
