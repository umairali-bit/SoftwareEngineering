package com.example.spring_ai.controller;

import com.example.spring_ai.tool.TravellingTools;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ChatController {

    private final ChatClient chatClient;
    private final TravellingTools travellingTools;

    @PostMapping("/chat")
    public String chat(@RequestBody String message) {
        return chatClient.prompt()
//                .system("""
//            You are an assistant.
//            If the user asks about weather,
//            always use the weather tool.
//        """)
                .user(message)
                .tools(travellingTools)
                .call()
                .content();
    }
}
