package com.example.weatherAPI.client;

import com.example.weatherAPI.dto.external.geocoding.GeocodingResponse;
import com.example.weatherAPI.dto.external.geocoding.LocationResult;
import com.example.weatherAPI.exceptions.CityNotFoundException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;


@Component
public class GeocodingClient {

    private final RestClient client;

    public GeocodingClient(@Qualifier("geocodingRestClient")
                           RestClient client) {
        this.client = client;
    }

    public LocationResult findLocation(String city) {

        GeocodingResponse response = client.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/search")
                        .queryParam("name", city)
                        .queryParam("count" , 1)
                        .build())
                .retrieve()
                .body(GeocodingResponse.class);


        if (response == null ||
            response.results() == null ||
                    response.results().isEmpty()) {
            throw new CityNotFoundException("City not found");
        }

        return response.results().get(0);

    }


}
