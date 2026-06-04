package com.example.spring_ai.service;

import com.example.spring_ai.dto.Joke;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.document.Document;
import org.springframework.stereotype.Service;
import org.springframework.ai.embedding.EmbeddingModel;


import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AIService {

    private final ChatClient chatClient;
    private final EmbeddingModel embeddingModel;
    private final VectorStore vectorStore;


    public float[] getEmbedding(String text) {
        return embeddingModel.embed(text);

    };

    public String askAI(String prompt) {
        return chatClient.prompt()
                .user(prompt)
                .call()
                .content();
    }

    public void ingestDataToVector(/*String text*/) {
//        Document document = new Document(text);

        List<Document> movies = List.of(

                new Document("""
                Title: Breaking Bad
                Genre: Crime Drama
                Year: 2008

                A high school chemistry teacher diagnosed with cancer
                turns to manufacturing methamphetamine with a former student.
                """),

                new Document("""
                Title: Better Call Saul
                Genre: Legal Drama
                Year: 2015

                The story follows Jimmy McGill's transformation into
                criminal lawyer Saul Goodman before the events of Breaking Bad.
                """),

                new Document("""
                Title: El Camino: A Breaking Bad Movie
                Genre: Crime Drama
                Year: 2019

                Jesse Pinkman attempts to escape his past and
                build a new future after the events of Breaking Bad.
                """),

                new Document("""
                Title: Slippin' Jimmy
                Genre: Animated Comedy
                Year: 2022

                An animated series following Jimmy McGill's childhood
                adventures before becoming Saul Goodman.
                """),

                new Document("""
                Title: American Greed: James McGill
                Genre: Mockumentary Crime
                Year: 2022

                A fictional documentary examining the rise and fall
                of Saul Goodman and his criminal connections.
                """)
        );

        vectorStore.add(movies);
    }

    public List<Document> similaritySearch(String text) {
        return vectorStore.similaritySearch(SearchRequest.builder()
                        .query(text)
                        .topK(2) // the number of result
                        .similarityThreshold(0.2) // confidence on the result

                .build());
    }


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


