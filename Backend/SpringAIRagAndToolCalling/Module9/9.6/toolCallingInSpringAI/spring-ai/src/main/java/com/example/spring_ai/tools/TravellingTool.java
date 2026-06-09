package com.example.spring_ai.tools;


import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.stereotype.Service;

@Service
public class TravellingTool {

    @Tool(description = "Get the weather of a city")
    public String getWeather(@ToolParam(description = "Name of the city to get the weather for") String city) {

        return switch (city) {
            case "Multan" -> "Sunny , 26 Degrees";
            case "NewJersey" -> "Cloudy, 10 Degrees";
            case "Penang" -> "Sunny , 27 Degrees";

            default -> "Unable to identify the city";
        };
    }
}
