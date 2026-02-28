package com.example.spring_ai.service;

import lombok.RequiredArgsConstructor;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.ai.chat.client.advisor.vectorstore.VectorStoreChatMemoryAdvisor;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.document.Document;
import org.springframework.ai.reader.pdf.PagePdfDocumentReader;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Value;
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

    @Value("classpath:Breaking_Bad_FAQ.pdf")
    private Resource pdfFile;

//  using advisors the retrieve the data
    public String askWithAdvisors(String prompt, String userId) {

        return chatClient.prompt()
                .system("")
                .user(prompt)
                .advisors(
                        VectorStoreChatMemoryAdvisor.builder(vectorStore)
                                .conversationId(userId)
                                .defaultTopK(4)
                                .build()

                )
                .call()
                .content();


    }

    public String askAI(String prompt) {


//         retrieving the data
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
                .topK(5)
                .similarityThreshold(0.4)
                        .filterExpression("file_name == 'Breaking_Bad_FAQ.pdf'")
                .build());



//      Augment
        String context = documents.stream()
                .map(document -> document.getText())
                .collect(Collectors.joining("\n\n"));

        PromptTemplate promptTemplate = new PromptTemplate(template);
        String systemPrompt = promptTemplate.render(Map.of("context", context));


//        generated response
        return chatClient.prompt()
                .system(systemPrompt)
                .user(prompt)
                .advisors(
                        new SimpleLoggerAdvisor()
                )
                .call()
                .content();
    }

    public void ingestPdfToVectorStore() {
        PagePdfDocumentReader reader = new PagePdfDocumentReader(pdfFile);
        List<Document> page = reader.get();

        TokenTextSplitter  splitter = TokenTextSplitter.builder()
                .withChunkSize(120)
                .build();

        List<Document> chunks = splitter.apply(page);

//        to see chunks
        for (int i = 0; i < Math.min(10, chunks.size()); i++) {
            System.out.println("CHUNK " + i + ":\n" + chunks.get(i).getText());
            System.out.println("-----");
        }
        vectorStore.add(chunks);
    }

//    public static List<Document> springAiComedyDocs() {
//        return List.of(
//
//                new Document(
//                        "Spring AI is like a translator between humans and robots, except robots still don’t understand sarcasm.",
//                        Map.of("topic", "ai")
//                ),
//
//                new Document(
//                        "A VectorStore is basically a very fancy filing cabinet where even your embarrassing search history could be stored… if it had feelings.",
//                        Map.of("topic", "vectorstore")
//                ),
//
//                new Document(
//                        "Retrieval Augmented Generation is when AI does open-book exams while humans still struggle with closed-book tests.",
//                        Map.of("topic", "vectorstore")
//                ),
//
//                new Document(
//                        "PgVector stores embeddings in PostgreSQL so your database finally feels important in the AI world.",
//                        Map.of("topic", "vectorstore")
//                ),
//
//                new Document(
//                        "ChatClient lets you talk to AI models like OpenAI or Ollama, which means you now have someone who replies instantly… unlike your friends.",
//                        Map.of("topic", "ai")
//                ),
//
//                new Document(
//                        "Embeddings convert text into numbers so machines understand meaning — basically turning words into math homework.",
//                        Map.of("topic", "embedding")
//                ),
//
//                new Document(
//                        "Spring AI saves developers from writing boring code, but sadly it cannot fix your life choices.",
//                        Map.of("topic", "ai")
//                )
//        );
//    }


}
