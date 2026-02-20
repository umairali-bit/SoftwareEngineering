package com.example.spring_ai.service;


import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AiService {

    private final ChatClient chatClient;

   public String getJoke(String topic) {
       return chatClient.prompt()
               .system("You are a sarcastic joker, give response in 1 line only.")
                .user("Give me a joke on the topic: " +topic)
                .call()
                .content();
    }

}
