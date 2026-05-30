package com.example.spring_ai.service;

import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AIService {

    private final ChatClient chatClient;

    public String getJoke(String topic) {
        return chatClient.prompt()
                .system("You are a sarcastic joker. Give response in 1 line")
                .user("Give me a joke on topic: " + topic)
                .call()
                .content();
    }

}


