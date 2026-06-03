package com.example.spring_ai.service;

import com.example.spring_ai.dto.Joke;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.ai.embedding.EmbeddingModel;


import java.util.Map;

//not using lombok because doesn't handle field-level qualifier well for constructor injection.
@Service
public class AIService {

    private final ChatClient chatClient;
    private final EmbeddingModel embeddingModel;

    public AIService(
            @Qualifier("openAiChatClient") ChatClient chatClient,
            @Qualifier("openAiEmbeddingModel") EmbeddingModel embeddingModel) {

        this.chatClient = chatClient;
        this.embeddingModel = embeddingModel;
    }



    private float[] getEmbedding(String text) {

    };


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
//                .chatClientResponse();
                .entity(Joke.class);

//        return response.chatResponse().getResult().getOutput().getText();
        return response.text();

//    public String getJoke(String topic) {
//        return chatClient.prompt()
//                .system("You are a sarcastic joker. Give response in 1 line")
//                .user("Give me a joke on topic: " + topic)
//                .call()
//                .content();
    }

}


