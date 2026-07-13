package com.example.weatherAPI.dto.external.geocoding;

import java.util.List;

public record GeocodingResponse(

        List<LocationResult> results
) {
}
