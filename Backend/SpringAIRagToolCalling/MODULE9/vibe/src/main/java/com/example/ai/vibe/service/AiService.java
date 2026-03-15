package com.example.ai.vibe.service;

import lombok.RequiredArgsConstructor;
import org.springframework.ai.document.Document;
import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AiService {

    private final EmbeddingModel embeddingModel;
    private final VectorStore vectorStore;

    public float[] getEmbedding(String text) {
        return embeddingModel.embed(text);
    }

    public void ingestDataToVectorStore() {
        List<Document> songList = List.of(

                new Document(
                        "A comforting song about emotional pain, loss, and healing. It speaks to someone who " +
                                "feels broken and hopeless but reminds them that support and love can help them recover" +
                                " and find light again.",
                        Map.of(
                                "title", "Fix You",
                                "artist", "Coldplay",
                                "genre", "rock"

                        )
                ),
                new Document(
                        "A deeply emotional song about heartbreak and accepting that a past relationship has ended. The singer reflects on lost love and wishes happiness for someone who has moved on.",
                        Map.of(
                                "title", "Someone Like You",
                                "artist", "Adele",
                                "genre", "sad"
                        )
                ),
                new Document(
                        "A motivational anthem about determination, resilience, and pushing through challenges. Often associated with training, discipline, and rising again after defeat.",
                        Map.of(
                                "title", "Eye of the Tiger",
                                "artist", "Survivor",
                                "genre", "workout"
                        )
                ),
                new Document(
                        "A powerful song about seizing opportunity and overcoming fear. It emphasizes focus, determination, and giving everything you have when your moment arrives.",
                        Map.of(
                                "title", "Lose Yourself",
                                "artist", "Eminem",
                                "genre", "workout"
                        )
                ),
                new Document(
                        "An uplifting song about hope, persistence, and believing that things will work out even when life feels uncertain or difficult.",
                        Map.of(
                                "title", "Don't Stop Believin'",
                                "artist", "Journey",
                                "genre", "classic"
                        )
                ),
                new Document(
                        "A dramatic and theatrical rock song exploring guilt, inner conflict, and emotional turmoil. It blends multiple musical styles and expresses deep reflection on life choices.",
                        Map.of(
                                "title", "Bohemian Rhapsody",
                                "artist", "Queen",
                                "genre", "classic"
                        )
                ),
                new Document(
                        "An energetic track about personal strength, confidence, and becoming stronger after facing challenges or setbacks.",
                        Map.of(
                                "title", "Stronger",
                                "artist", "Kanye West",
                                "genre", "workout"
                        )
                ),
                new Document(
                        "A powerful breakup song about betrayal and emotional pain but also about reclaiming strength and realizing one's worth after being hurt.",
                        Map.of(
                                "title", "Rolling in the Deep",
                                "artist", "Adele",
                                "genre", "sad"
                        )
                ),
                new Document(
                        "A mysterious classic rock song about temptation, excess, and being trapped in a lifestyle that initially appears glamorous but becomes impossible to escape.",
                        Map.of(
                                "title", "Hotel California",
                                "artist", "Eagles",
                                "genre", "classic"
                        )
                ),
                new Document(
                        "A high energy rock song that builds excitement and adrenaline, often associated with sports, workouts, and moments that require intensity and power.",
                        Map.of(
                                "title", "Thunderstruck",
                                "artist", "AC/DC",
                                "genre", "workout"
                        )
                )


        );
        vectorStore.add(songList);
    }

    public List<Document> similaritySearch(String text) {
//        return vectorStore.similaritySearch(text);
        return vectorStore.similaritySearch(SearchRequest.builder()
                .query(text)
                .topK(3)
                .similarityThreshold(0.3)
                .build());

    }
}
