package com.audition.integration;

import static com.audition.builder.AuditionDataBuilder.populateComments;
import static com.audition.builder.AuditionDataBuilder.populatePost;
import static com.audition.builder.AuditionDataBuilder.populatePosts;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

import com.audition.model.AuditionPost;
import com.audition.model.Comment;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.client.AutoConfigureWebClient;
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest;
import org.springframework.http.MediaType;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.client.MockRestServiceServer;

@RestClientTest(AuditionIntegrationClient.class)
@AutoConfigureWebClient(registerRestTemplate = true)
@SuppressWarnings({"PMD.BeanMembersShouldSerialize", "PMD.JUnitTestContainsTooManyAsserts"})
class AuditionIntegrationClientTest {

    @Autowired
    private AuditionIntegrationClient integrationClient;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockRestServiceServer server;

    private static final String CLIENT_URL = "https://jsonplaceholder.typicode.com/posts/";
    private static final String POST_URL_VARIABLE = "postUrl";

    @Test
    void testGetPostsSuccess() throws JsonProcessingException {
        final List<AuditionPost> posts = populatePosts();
        final String postsJson = objectMapper.writeValueAsString(posts);

        ReflectionTestUtils.setField(integrationClient, POST_URL_VARIABLE, CLIENT_URL);

        this.server.expect(requestTo(CLIENT_URL))
            .andRespond(withSuccess(postsJson, MediaType.APPLICATION_JSON));

        final List<AuditionPost> postsResponse = integrationClient.getPosts();

        assertNotNull(postsResponse);
        assertNotNull(postsResponse.get(0));
        assertNotNull(postsResponse.get(1));

        assertEquals(1, postsResponse.get(0).getId());
        assertEquals(10, postsResponse.get(0).getUserId());
        assertEquals("This is title1", postsResponse.get(0).getTitle());
        assertEquals("This is body1", postsResponse.get(0).getBody());

        assertEquals(2, postsResponse.get(1).getId());
        assertEquals(20, postsResponse.get(1).getUserId());
        assertEquals("This is title2", postsResponse.get(1).getTitle());
        assertEquals("This is body2", postsResponse.get(1).getBody());
    }

    @Test
    void testGetPostByIdSuccess() throws JsonProcessingException {
        final AuditionPost post = populatePost();
        final String postJson = objectMapper.writeValueAsString(post);

        ReflectionTestUtils.setField(integrationClient, POST_URL_VARIABLE, CLIENT_URL);
        this.server.expect(requestTo("https://jsonplaceholder.typicode.com/posts/10"))
            .andRespond(withSuccess(postJson, MediaType.APPLICATION_JSON));

        final AuditionPost auditionPostResponse = integrationClient.getPostById("10");

        assertNotNull(auditionPostResponse);
        assertEquals(10, auditionPostResponse.getId());
        assertEquals(100, auditionPostResponse.getUserId());
        assertEquals("This is the title", auditionPostResponse.getTitle());
        assertEquals("This is the body", auditionPostResponse.getBody());
    }

    @Test
    void testGetCommentsSuccess() throws JsonProcessingException {
        final List<Comment> comments = populateComments();
        final String commentsJson = objectMapper.writeValueAsString(comments);

        ReflectionTestUtils.setField(integrationClient, POST_URL_VARIABLE, CLIENT_URL);
        this.server.expect(requestTo("https://jsonplaceholder.typicode.com/posts/1/comments"))
            .andRespond(withSuccess(commentsJson, MediaType.APPLICATION_JSON));

        final List<Comment> commentsResponse = integrationClient.getComments("1");

        assertNotNull(commentsResponse);
        assertNotNull(commentsResponse.get(0));
        assertNotNull(commentsResponse.get(1));

        assertEquals(1, commentsResponse.get(0).getId());
        assertEquals("John", commentsResponse.get(0).getName());
        assertEquals("user1@test.com", commentsResponse.get(0).getEmail());
        assertEquals("This is the comment body1", commentsResponse.get(0).getBody());

        assertEquals(2, commentsResponse.get(1).getId());
        assertEquals("Smith", commentsResponse.get(1).getName());
        assertEquals("user2@test.com", commentsResponse.get(1).getEmail());
        assertEquals("This is the comment body2", commentsResponse.get(1).getBody());
    }
}
