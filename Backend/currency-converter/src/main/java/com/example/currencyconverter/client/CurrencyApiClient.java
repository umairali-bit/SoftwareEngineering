package com.example.currencyconverter.client;

import com.example.currencyconverter.dtos.CurrencyApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Slf4j
@Component
public class CurrencyApiClient {

    private final RestClient restClient;

    public CurrencyApiClient(RestClient restClient) {
        this.restClient = restClient;
    }

    public CurrencyApiResponse getRates(String from, String to) {
        String f = from.toUpperCase();
        String t = to.toUpperCase();

        log.info("➡️ Calling FreeCurrencyAPI: base={} target={}", f, t);

        return restClient.get()
                .uri(uriBuilder -> uriBuilder
                        .queryParam("base_currency", f)
                        .queryParam("currencies", t)
                        .build())
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, (req, res) -> {
                    String errorBody = new String(res.getBody().readAllBytes());
                    log.error("❌ Client error from FreeCurrencyAPI: {}", errorBody);
                    throw new RuntimeException("Invalid request (bad currency code or API key)");
                })
                .onStatus(HttpStatusCode::is5xxServerError, (req, res) -> {
                    String errorBody = new String(res.getBody().readAllBytes());
                    log.error("❌ Server error from FreeCurrencyAPI: {}", errorBody);
                    throw new RuntimeException("FreeCurrencyAPI unavailable");
                })
                .body(new ParameterizedTypeReference<>() {});
    }
}