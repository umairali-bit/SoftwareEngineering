package com.example.spring_ai.controller;


import com.example.spring_ai.tools.FlightBookingTools;
import com.example.spring_ai.tools.TravellingTool;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ChatController {

    private final ChatClient chatClient;
    private final TravellingTool travellingTool;
    private final FlightBookingTools flightBookingTools;

    @PostMapping("/chat")
    public String chat(@RequestBody String prompt, @RequestParam String userId) {

        String systemPrompt = String.format("""
                You are a friendly and professional flight booking assistant.
                Use the available tools to create, view, or update bookings.
                Always confirm the actions with the user when possible.
                The current user ID is provided by the application.
                
                IMPORTANT: The current user id is "%s". 
                When calling tools that require a user id. ALWAYS use the exact value.
                """, userId);


        return chatClient.prompt()
                .system(systemPrompt)
                .user(prompt)
                .tools(travellingTool, flightBookingTools)
                .call()
                .content();

    }


}
