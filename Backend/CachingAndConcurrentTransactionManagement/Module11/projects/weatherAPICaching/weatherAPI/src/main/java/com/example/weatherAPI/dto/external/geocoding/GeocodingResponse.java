package com.example.weatherAPI.dto.external.geocoding;

import java.io.Serializable;
import java.util.List;

public record GeocodingResponse(

        List<LocationResult> results
)  {
}
