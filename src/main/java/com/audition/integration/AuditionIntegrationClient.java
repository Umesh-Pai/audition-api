package com.audition.integration;

import static org.springframework.http.HttpMethod.GET;

import com.audition.common.exception.SystemException;
import com.audition.model.AuditionPost;
import com.audition.model.Comment;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@SuppressWarnings({"PMD.PreserveStackTrace", "PMD.BeanMembersShouldSerialize"})
@Component
public class AuditionIntegrationClient {

    @Value("${client.post.url}")
    private String postUrl;

    @Autowired
    private RestTemplate restTemplate;

    private static final String RESILIENCE_INSTANCE_NAME = "audition-api";

    @CircuitBreaker(name = RESILIENCE_INSTANCE_NAME)
    @Retry(name = RESILIENCE_INSTANCE_NAME)
    public List<AuditionPost> getPosts() {
        // TODO make RestTemplate call to get Posts from https://jsonplaceholder.typicode.com/posts
        final ResponseEntity<List<AuditionPost>> response = restTemplate.exchange(
            postUrl, GET, null,
            new ParameterizedTypeReference<List<AuditionPost>>() {
            });

        return response.getBody();
    }

    @CircuitBreaker(name = RESILIENCE_INSTANCE_NAME)
    @Retry(name = RESILIENCE_INSTANCE_NAME)
    public AuditionPost getPostById(final Integer id) {
        // TODO get post by post ID call from https://jsonplaceholder.typicode.com/posts/
        try {
            final ResponseEntity<AuditionPost> response = restTemplate.exchange(
                postUrl + id, GET, null,
                AuditionPost.class);

            return response.getBody();
        } catch (final HttpClientErrorException e) {
            if (e.getStatusCode() == HttpStatus.NOT_FOUND) {
                throw new SystemException("Cannot find a Post with id " + id, "Resource Not Found",
                    404);
            } else {
                // TODO Find a better way to handle the exception so that the original error message is not lost. Feel free to change this function.
                throw new SystemException(e.getMessage(), "Bad Request", e.getStatusCode().value());
            }
        }
    }

    @CircuitBreaker(name = RESILIENCE_INSTANCE_NAME)
    @Retry(name = RESILIENCE_INSTANCE_NAME)
    public List<Comment> getComments(final Integer postId) {

        // TODO Write a method GET comments for a post from https://jsonplaceholder.typicode.com/posts/{postId}/comments - the comments must be returned as part of the post.
        try {
            final ResponseEntity<List<Comment>> response = restTemplate.exchange(
                postUrl + postId + "/comments", GET, null,
                new ParameterizedTypeReference<List<Comment>>() {
                });

            return response.getBody();
        } catch (final HttpClientErrorException e) {
            if (e.getStatusCode() == HttpStatus.NOT_FOUND) {
                throw new SystemException("Cannot find a Post with id " + postId, "Resource Not Found",
                    404);
            } else {
                throw new SystemException(e.getMessage(), "Bad Request", e.getStatusCode().value());
            }
        }
    }

}
