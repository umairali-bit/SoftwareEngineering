package com.example.spring_ai.tool;


import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.stereotype.Service;

@Service
public class TravellingTools {

    @Tool(description = "Get the weather for a city")
    public String getWeather(@ToolParam(description = "Name of the city", required = true) String city){

        return switch (city) {
            case "New York City" -> "Sunny, 50 degrees";
            case "London" -> "Cloudy, 30 degrees";
            default -> "Cannot find the city";
        };
    }


}
