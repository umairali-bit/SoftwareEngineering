package com.example.spring_ai.service;


import com.example.spring_ai.dto.Joke;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.document.Document;
import org.springframework.ai.embedding.Embedding;
import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AiService {

    private final ChatClient chatClient;
    private final EmbeddingModel embeddingModel;
    private final VectorStore vectorStore;

    public float[] getEmbedding(String text) {
        return embeddingModel.embed(text);

    }

//    public void ingestDataToVectorStore(String text) {
//        Document document = new Document(text);
//        vectorStore.add(List.of(document));

    public void ingestDataToVectorStore() {

        List<Document> breakingBadUniverse = List.of(

                new Document(
                        "Breaking Bad is a crime drama about a high school chemistry teacher who turns into a methamphetamine kingpin.",
                        Map.of(
                                "title", "Breaking Bad",
                                "genre", "Crime, Drama, Thriller",
                                "year", 2008
                        )
                ),

                new Document(
                        "Better Call Saul follows the transformation of Jimmy McGill into the morally challenged lawyer Saul Goodman.",
                        Map.of(
                                "title", "Better Call Saul",
                                "genre", "Crime, Drama",
                                "year", 2015
                        )
                ),

                new Document(
                        "El Camino continues Jesse Pinkman's story after the events of Breaking Bad.",
                        Map.of(
                                "title", "El Camino: A Breaking Bad Movie",
                                "genre", "Crime, Drama, Thriller",
                                "year", 2019
                        )
                )
        );

        vectorStore.add(breakingBadUniverse);
    }

//    similarity search
//    public void similaritySearch(String text) {
//        vectorStore.similaritySearch(text);
    public List<Document> similaritySearch(String text) {
//        return vectorStore.similaritySearch(text);
        return vectorStore.similaritySearch(SearchRequest.builder()
                .query(text)
                .topK(3)
                .similarityThreshold(0.3)
                .build());

    }

    public String askAI(String prompt) {

        return chatClient.prompt()
                .user(prompt)
                .call()
                .content();
    }


    public String getJoke(String topic) {

        String systemPrompt = """
                You are a sarcastic joker, you make poetic jokes in 4 lines.
                You dont make jokes about politics.
                Give me a joke on the topic: {topic}
                """;

        PromptTemplate promptTemplate = new PromptTemplate(systemPrompt);

//       replace placeholders inside the prompt template with actual values
        String renderedText = promptTemplate.render(Map.of("topic", topic));

        var response = chatClient.prompt()
                .user(renderedText)
                .advisors(
                        new SimpleLoggerAdvisor()
                )
                .call()
//                .chatClientResponse(); //to get metadata
                .entity(Joke.class);

        return response.text();
    }

}
