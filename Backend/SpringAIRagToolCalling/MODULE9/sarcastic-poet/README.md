# Sarcastic Poet API

A Spring Boot REST API powered by **Spring AI** that generates sarcastic
poems using either:

-   **Ollama (local LLM)**
-   **OpenAI (cloud LLM)**

The AI provider is controlled using **Spring Profiles**, allowing easy
switching between local and cloud models.

------------------------------------------------------------------------

# Tech Stack

-   Java 21
-   Spring Boot
-   Spring AI
-   Ollama
-   OpenAI
-   Lombok
-   Maven

------------------------------------------------------------------------

# Project Structure

    src/main/java/com/example/sarcasticpoet

    controller/
      PoetController.java

    service/
      PoetService.java

    dto/
      SarcasticPoetDto.java

    config/
      ProfileLogger.java

------------------------------------------------------------------------

# API Endpoint

Generate a sarcastic poem.

    GET /poem?topic={topic}&lang={language}

### Example Request

    http://localhost:8080/poem?topic=java&lang=english

### Example Response

``` json
{
  "title": "Sarcastic Poem on java",
  "poem_text": "Write once, run anywhere...",
  "rhyme_scheme": "free-verse"
}
```

------------------------------------------------------------------------

# DTO

``` java
public record SarcasticPoetDto(
        String title,
        String poem_text,
        String rhyme_scheme
) {}
```

------------------------------------------------------------------------

# Controller

``` java
@RestController
@RequiredArgsConstructor
public class PoetController {

    private final PoetService poetService;

    @GetMapping("/poem")
    public SarcasticPoetDto getPoem(
            @RequestParam String topic,
            @RequestParam(defaultValue = "english") String lang
    ) {
        return poetService.generatePoem(topic, lang);
    }
}
```

------------------------------------------------------------------------

# Service

``` java
@Service
@RequiredArgsConstructor
public class PoetService {

    private final ChatClient chatClient;

    public SarcasticPoetDto generatePoem(String topic, String lang) {

        String poem = chatClient.prompt()
                .user("""
                        Write a short sarcastic poem about %s in %s.
                        Keep it witty, clean, and under 10 lines.
                        """.formatted(topic, lang))
                .call()
                .content();

        return new SarcasticPoetDto(
                "Sarcastic Poem on " + topic,
                poem,
                "free-verse"
        );
    }
}
```

------------------------------------------------------------------------

# Spring Profiles

The project supports two AI providers using Spring Profiles.

  Profile   Model Provider
  --------- ----------------
  ollama    Local LLM
  openai    OpenAI API

------------------------------------------------------------------------

# Configuration

## Default Profile

`application.yml`

``` yaml
spring:
  application:
    name: sarcastic-poet
  profiles:
    default: ollama
```

------------------------------------------------------------------------

# Ollama Configuration

`application-ollama.yml`

``` yaml
spring:
  ai:
    model:
      chat: ollama
      embedding: none
      image: none
      moderation: none
      audio:
        speech: none
        transcription: none

    ollama:
      base-url: http://localhost:11434
      chat:
        options:
          model: qwen3:30b
```

------------------------------------------------------------------------

# OpenAI Configuration

`application-openai.yml`

``` yaml
spring:
  ai:
    model:
      chat: openai
      embedding: none
      image: none
      moderation: none
      audio:
        speech: none
        transcription: none

    openai:
      api-key: ${OPENAI_API_KEY}
      chat:
        options:
          model: gpt-4o-mini
```

------------------------------------------------------------------------

# Running the Application

## Run with Ollama (default)

    mvn spring-boot:run

Make sure Ollama is running:

    ollama run qwen3:30b

------------------------------------------------------------------------

## Run with OpenAI

    mvn spring-boot:run "-Dspring-boot.run.profiles=openai"                                                                     

Set your API key.

Mac/Linux

    export OPENAI_API_KEY=your_key

Windows

    set OPENAI_API_KEY=your_key

------------------------------------------------------------------------

# Testing the API

## Browser

    http://localhost:8080/poem?topic=java&lang=english

## Curl

    curl "http://localhost:8080/poem?topic=java&lang=english"

------------------------------------------------------------------------

# Future Improvements

-   Structured AI output mapping
-   Swagger / OpenAPI documentation
-   Multiple poem styles
-   Response caching
-   Rate limiting
-   Streaming AI responses

------------------------------------------------------------------------

# License

MIT License
