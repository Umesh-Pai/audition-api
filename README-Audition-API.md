# Audition API

Audition API enables a consumer to retrieve the posts and comments.

## Getting Started

### Prerequisite tooling

- Any Springboot/Java IDE. Ideally IntelliJIdea.
- Java 17
- Gradle 8
  
### Prerequisite knowledge

- Java
- SpringBoot
- Gradle
- Junit

## Running the application

Run the following command from the folder containing the build.gradle file.

./gradlew bootRun

## OpenAPI Specification

OpenAPI Specification can be accessed using the URL http://server:port/context-path/swagger-ui.html

## Future Enhancements

- Exclude BeanMembersShouldSerialize PMD rule
- Add unit tests for resilience4j CircuitBreaker and Retry
- Add fallback methods for CircuitBreaker
- Add unit tests for negative scenarios
- Add tests for the controller class using WebMvcTest

## Pagination
Pagination has been implemented for /posts endpoint. To retrieve a specific set of values we can pass the page number and size.
/posts?page=2&size=5




