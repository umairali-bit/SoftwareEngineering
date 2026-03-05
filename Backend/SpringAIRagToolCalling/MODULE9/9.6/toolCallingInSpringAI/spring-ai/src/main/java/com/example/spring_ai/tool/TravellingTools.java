package com.example.spring_ai.tool;


import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.stereotype.Service;

@Service
public class TravellingTools {

    @Tool(description = "Get the weather for a city")
    public String getWeather(@ToolParam(description = "Name of the city", required = true) String city){

        return switch (city) {
            case "NewYork City" -> "Sunny, 50 degrees";
            case "London City" -> "Cloudy, 30 degrees";
            default -> "Cannot find the city";
        };
    }


}
