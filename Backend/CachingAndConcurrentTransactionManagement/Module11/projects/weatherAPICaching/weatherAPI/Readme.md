# 🌤️ Weather API

A production-ready Spring Boot REST API that retrieves current weather information for a city using the Open-Meteo APIs. The application performs geocoding, fetches current weather, caches responses in Redis, provides structured logging, global exception handling, environment-specific configuration, and automated tests.

---

# Features

- Current weather lookup by city
- Open-Meteo Geocoding API integration
- Open-Meteo Weather API integration
- Redis caching
- Global exception handling
- Structured logging (SLF4J)
- Spring Profiles (Development & Production)
- Environment variable configuration
- Graceful shutdown
- JUnit 5 & Mockito unit testing
- Spring Boot Actuator

---

# Technology Stack

| Technology | Version |
|------------|----------|
| Java | 21 |
| Spring Boot | 3.x |
| Spring Web | ✓ |
| RestClient | ✓ |
| Redis | ✓ |
| Spring Cache | ✓ |
| Spring Actuator | ✓ |
| JUnit 5 | ✓ |
| Mockito | ✓ |
| Maven | ✓ |

---

# Project Architecture

```
                   Client
                      │
                      ▼
             WeatherController
                      │
                      ▼
             WeatherServiceImpl
          ┌───────────┴───────────┐
          ▼                       ▼
 GeocodingClient           WeatherClient
          │                       │
          ▼                       ▼
 Open-Meteo Geocoding      Open-Meteo Weather
          │
          ▼
      Redis Cache
```

---

# Package Structure

```
com.example.weatherAPI
│
├── client
│   ├── GeocodingClient
│   └── WeatherClient
│
├── config
│   └── RestClientConfig
│
├── controller
│   └── WeatherController
│
├── dto
│   ├── WeatherResponse
│   └── external
│       ├── geocoding
│       └── weather
│
├── exceptions
│   ├── ApiErrorResponse
│   ├── CityNotFoundException
│   ├── WeatherServiceException
│   └── GlobalExceptionHandler
│
├── service
│   ├── WeatherService
│   └── impl
│       └── WeatherServiceImpl
│
└── WeatherApiApplication
```

---

# Request Flow

```
Client Request
      │
      ▼
WeatherController
      │
      ▼
WeatherServiceImpl
      │
      ▼
Check Redis Cache
      │
 ┌────┴────┐
 │         │
 │ HIT     │ MISS
 │         │
 ▼         ▼
Return   GeocodingClient
Cache        │
             ▼
      Open-Meteo Geocoding
             │
             ▼
       WeatherClient
             │
             ▼
      Open-Meteo Weather
             │
             ▼
      Store in Redis
             │
             ▼
      Return Response
```

---

# API Endpoint

## Get Current Weather

```
GET /api/weather/current?city={city}
```

Example

```
GET /api/weather/current?city=Lahore
```

---

# Sample Response

```json
{
  "city": "Lahore",
  "country": "Pakistan",
  "temperature": 35.1,
  "windSpeed": 11.2,
  "weatherCode": 3,
  "date": "2026-07-27T10:30:00",
  "cached": false
}
```

---

# Configuration Profiles

## Development

```
application.yml
+
application-dev.yml
```

Features

- DEBUG logging
- Redis via environment variables
- Development configuration

Run

```
--spring.profiles.active=dev
```

---

## Production

```
application.yml
+
application-prod.yml
```

Features

- INFO logging
- Graceful shutdown
- Production configuration
- Environment variables

Run

```
--spring.profiles.active=prod
```

---

# Environment Variables

| Variable | Description |
|----------|-------------|
| REDIS_HOST | Redis hostname |
| REDIS_PORT | Redis port |
| REDIS_USERNAME | Redis username |
| REDIS_PASSWORD | Redis password |

---

# Caching

The application uses Spring Cache with Redis.

Workflow

```
Request

↓

Redis Lookup

↓

Cache Hit
    ↓
Return Cached Response

Cache Miss
    ↓
Call External APIs
    ↓
Store Response in Redis
```

---

# Logging

The application logs

- Incoming requests
- Cache hits
- Cache misses
- External API calls
- Exceptions
- Request execution time

---

# Error Handling

Global exception handling is implemented using `@RestControllerAdvice`.

Handled exceptions

- CityNotFoundException
- WeatherServiceException
- Generic Exception

---

# Testing

The project includes automated tests using

- JUnit 5
- Mockito
- MockMvc

Test coverage includes

- Service Layer
- Controller
- Exception Handling
- Cache Behavior

---

# Monitoring

Spring Boot Actuator is enabled.

Endpoints

```
/actuator/health

/actuator/info

/actuator/metrics
```

---

# Future Improvements

- Docker & Docker Compose
- CI/CD Pipeline
- GitHub Actions
- Prometheus & Grafana
- Distributed Tracing
- Rate Limiting
- API Documentation (OpenAPI / Swagger)

---

# Author

Umair Ali

Spring Boot • Java • Backend Engineering
