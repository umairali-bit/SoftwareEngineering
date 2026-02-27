package com.example.spring_ai.service;


import com.example.spring_ai.dto.Joke;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.document.Document;
import org.springframework.ai.embedding.Embedding;
import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
        vectorStore.add(springAiComedyDocs());
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

        String template = """
                You are an AI assistant helping a developer.
                
                Rules:
                - Use ONLY the information provided in the context
                - You MAY rephrase, summarize, and explain in natural language
                - Do NOT introduce new concepts or facts
                - If multiple context sections are relevant, combine them into a single explanation
                - If the answer is not present, say "I don't know"
                
                Context:
                {context}
                
                Answer in a friendly, conversational tone.
                """;

        List<Document> documents = vectorStore.similaritySearch(SearchRequest.builder()
                .query(prompt)
                .topK(2)
                .filterExpression("topic == 'ai' or topic == 'vectorstore'")
                .build());

        String context = documents.stream()
                .map(document -> document.getText())
                .collect(Collectors.joining("\n\n"));

        PromptTemplate promptTemplate = new PromptTemplate(template);
        String systemPrompt = promptTemplate.render(Map.of("context", context));

        return chatClient.prompt()
                .system(systemPrompt)
                .user(prompt)
                .call()
                .content();
    }

    public static List<Document> springAiComedyDocs() {
        return List.of(

                new Document(
                        "Spring AI is like a translator between humans and robots, except robots still don’t understand sarcasm.",
                        Map.of("topic", "ai")
                ),

                new Document(
                        "A VectorStore is basically a very fancy filing cabinet where even your embarrassing search history could be stored… if it had feelings.",
                        Map.of("topic", "vectorstore")
                ),

                new Document(
                        "Retrieval Augmented Generation is when AI does open-book exams while humans still struggle with closed-book tests.",
                        Map.of("topic", "vectorstore")
                ),

                new Document(
                        "PgVector stores embeddings in PostgreSQL so your database finally feels important in the AI world.",
                        Map.of("topic", "vectorstore")
                ),

                new Document(
                        "ChatClient lets you talk to AI models like OpenAI or Ollama, which means you now have someone who replies instantly… unlike your friends.",
                        Map.of("topic", "ai")
                ),

                new Document(
                        "Embeddings convert text into numbers so machines understand meaning — basically turning words into math homework.",
                        Map.of("topic", "embedding")
                ),

                new Document(
                        "Spring AI saves developers from writing boring code, but sadly it cannot fix your life choices.",
                        Map.of("topic", "ai")
                )
        );
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
