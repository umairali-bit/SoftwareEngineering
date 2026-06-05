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

    }

    ;

    public String askAI(String prompt) {
        return chatClient.prompt()
                .user(prompt)
                .call()
                .content();
    }

    public void ingestDataToVector(/*String text*/) {
//        Document document = new Document(text);

        List<Document> movies = List.of(

                new Document(
                        """
                                A high school chemistry teacher diagnosed with cancer
                                turns to manufacturing methamphetamine with a former student.
                                """,
                        Map.of(
                                "title", "Breaking Bad",
                                "genre", "Crime Drama",
                                "year", "2008"
                        )
                ),

                new Document(
                        """
                                The story follows Jimmy McGill's transformation into
                                criminal lawyer Saul Goodman before the events of Breaking Bad.
                                """,
                        Map.of(
                                "title", "Better Call Saul",
                                "genre", "Legal Drama",
                                "year", "2015"
                        )
                ),

                new Document(
                        """
                                Jesse Pinkman attempts to escape his past and
                                build a new future after the events of Breaking Bad.
                                """,
                        Map.of(
                                "title", "El Camino: A Breaking Bad Movie",
                                "genre", "Crime Drama",
                                "year", "2019"
                        )
                ),

                new Document(
                        """
                                An animated series following Jimmy McGill's childhood
                                adventures before becoming Saul Goodman.
                                """,
                        Map.of(
                                "title", "Slippin' Jimmy",
                                "genre", "Animated Comedy",
                                "year", "2022"
                        )
                ),

                new Document(
                        """
                                A fictional documentary examining the rise and fall
                                of Saul Goodman and his criminal connections.
                                """,
                        Map.of(
                                "title", "American Greed: James McGill",
                                "genre", "Mockumentary Crime",
                                "year", "2022"
                        )
                )
        );

        vectorStore.add(movies);
        vectorStore.add(springAIDocs());
    }

    public List<Document> similaritySearch(String text) {
        return vectorStore.similaritySearch(SearchRequest.builder()
                .query(text)
                .topK(2) // the number of result
                .similarityThreshold(0.2) // confidence on the result

                .build());
    }


    public static List<Document> springAIDocs() {

        return List.of(new Document(
                        "Spring AI is a framework that integrates AI capabilities into Spring Boot applications.",
                        Map.of("topic", "spring-ai")
                ),

                new Document(
                        "ChatClient provides a fluent API for interacting with large language models.",
                        Map.of("topic", "chat-client")
                ),

                new Document(
                        "Embeddings convert text into numerical vectors that capture semantic meaning.",
                        Map.of("topic", "embeddings")
                ),

                new Document(
                        "A Vector Store stores embeddings and enables semantic similarity searches.",
                        Map.of("topic", "vector-store")
                ),

                new Document(
                        "PGVector is a PostgreSQL extension that adds vector storage and similarity search capabilities.",
                        Map.of("topic", "pgvector")
                ),

                new Document(
                        "Retrieval-Augmented Generation retrieves relevant documents from a vector store and provides them to the language model.",
                        Map.of("topic", "rag")
                )
        );

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


