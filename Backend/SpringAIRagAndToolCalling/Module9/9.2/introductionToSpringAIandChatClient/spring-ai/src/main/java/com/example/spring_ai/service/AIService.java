package com.example.spring_ai.service;

import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class AIService {

    private final ChatClient chatClient;


    public String getJoke(String topic) {
//    system prompt
    String systemPrompt = """
        You are a sarcastic joker. Give response in exactly 4 lines.
        Dont mention politics at all.
        Get me a joke on the topic: {topic}
        """;

    PromptTemplate promptTemplate = new PromptTemplate(systemPrompt);
    String renderText = promptTemplate.render(Map.of("topic", topic));

        var response = chatClient.prompt()
                .user(renderText)
                .advisors(
                        new SimpleLoggerAdvisor()
                )
                .call()
//                .content();
                .chatClientResponse();

        return response.chatResponse().getResult().getOutput().getText();

//    public String getJoke(String topic) {
//        return chatClient.prompt()
//                .system("You are a sarcastic joker. Give response in 1 line")
//                .user("Give me a joke on topic: " + topic)
//                .call()
//                .content();
    }

}


