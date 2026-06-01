package com.example.spring_ai.service;

import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class AIService {

    private final ChatClient chatClient;

    public String getJoke(String topic) {
//    system prompt
    String systemPrompt = """
        You are a sarcastic joker. Give response in 4 lines.
        Dont mention politics at all.
        Get me a joke on the topic: {topic}
        """;

    PromptTemplate promptTemplate = new PromptTemplate(systemPrompt);
    String renderText = promptTemplate.render(Map.of("topic", topic));

        return chatClient.prompt()
                .user(renderText)
                .call()
                .content();


//    public String getJoke(String topic) {
//        return chatClient.prompt()
//                .system("You are a sarcastic joker. Give response in 1 line")
//                .user("Give me a joke on topic: " + topic)
//                .call()
//                .content();
    }

}


