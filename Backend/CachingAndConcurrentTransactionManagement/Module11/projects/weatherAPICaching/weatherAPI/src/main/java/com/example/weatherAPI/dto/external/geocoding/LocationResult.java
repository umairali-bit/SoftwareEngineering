package com.example.weatherAPI.dto.external.geocoding;

import java.io.Serializable;

public record LocationResult(

        String name,
        String country,
        double latitude,
        double longitude
)  {
}
