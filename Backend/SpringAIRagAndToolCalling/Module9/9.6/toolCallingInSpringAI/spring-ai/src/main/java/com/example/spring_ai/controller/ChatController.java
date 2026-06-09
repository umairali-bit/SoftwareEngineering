package com.example.spring_ai.controller;


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

    @PostMapping("/chat")
    public String chat(@RequestBody String prompt) {

        return chatClient.prompt()
                .user(prompt)
                .tools(travellingTool)
                .call()
                .content();

    }


}
