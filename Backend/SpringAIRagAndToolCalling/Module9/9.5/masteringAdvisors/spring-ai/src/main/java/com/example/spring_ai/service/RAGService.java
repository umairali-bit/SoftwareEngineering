package com.example.spring_ai.service;


import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.client.advisor.SafeGuardAdvisor;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.ai.chat.client.advisor.vectorstore.QuestionAnswerAdvisor;
import org.springframework.ai.chat.client.advisor.vectorstore.VectorStoreChatMemoryAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.memory.MessageWindowChatMemory;
import org.springframework.ai.chat.memory.repository.jdbc.JdbcChatMemoryRepository;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.document.Document;
import org.springframework.ai.reader.pdf.PagePdfDocumentReader;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RAGService {

    private final ChatClient chatClient;
    private final VectorStore vectorStore;
    private final ChatMemory  chatMemory;

    @Value("classpath:Spring_AI_Interview_Guide.pdf")
    Resource resource;


//    Manual RAG  implementation
//    Retrieval

    public String askAI(String prompt) {

        String template = """
                You are an AI assistant helping a developer.
                
                Rules:
                - Use ONLY the information provided in the context.
                - You may rephrase, summarize, and explain the information in natural language.
                - Do NOT introduce new concepts, facts, or assumptions.
                - If multiple context sections are relevant, combine them into a single coherent explanation.
                - If the answer cannot be found in the context, respond with: "I don't know."
                
                Context:
                {context}
                
                Prompt:
                {prompt}
                
                Answer in a friendly and conversational tone.
                """;

//      Augment
        List<Document> documents = vectorStore.similaritySearch(SearchRequest.builder()
                .query(prompt)
                .topK(4)
                .similarityThreshold(0.5)
                .filterExpression("file_name == 'Spring_AI_Interview_Guide.pdf'")
                .build());

        if (documents.isEmpty()) {
            return "I don't know.";
        }

//        Displaying the result
        String context = documents.stream()
                .map(s -> s.getText())
                .collect(Collectors.joining("\n\n"));

        PromptTemplate promptTemplate = new PromptTemplate(template);
        String systemPrompt = promptTemplate.render(Map.of(
                "context", context,
                "prompt", prompt));


        return chatClient.prompt()
                .user(systemPrompt)
//                .advisors(new SimpleLoggerAdvisor()) -- added this advisor in AIConfig
                .call()
                .content();
    }

    public void ingestPdfToVectorStore() {
        PagePdfDocumentReader reader = new PagePdfDocumentReader(resource);
        List<Document> pages = reader.get();
        TokenTextSplitter tokenTextSplitter = TokenTextSplitter.builder()
                .withChunkSize(200)
                .build();

        List<Document> chunks = tokenTextSplitter.apply(pages);
        vectorStore.add(chunks);

    }

//    Applying long term memory advisor
//    Applying short term memory advisor
//    Applying RAG advisor
//    Applying SafeGuard advisor
    public String askAiWithAdvisors(String prompt, String userId) {
        return chatClient.prompt()
                .system("""
                        You are an AI assistant called Cody.
                        Greet users with your name (Cody) and the user name if you know their name.
                        Answer in a friendly and conversational tone.
                        """)
                .user(prompt)
                .advisors(

                        new SafeGuardAdvisor(List.of("Politics","Gaming")),

                        MessageChatMemoryAdvisor.builder(chatMemory)
                                .conversationId(userId)
                                .build(),

                        VectorStoreChatMemoryAdvisor.builder(vectorStore)
                                .conversationId(userId)
                                .defaultTopK(4)
                                .build(),

                        QuestionAnswerAdvisor.builder(vectorStore)
                                .searchRequest(SearchRequest.builder()
                                        .filterExpression("file_name == 'Spring_AI_Interview_Guide.pdf'")
                                        .topK(4)
                                        .build())
                                .build()

                )
                .call()
                .content();

    }

}
