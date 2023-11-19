package com.audition.web;

import static com.audition.builder.AuditionDataBuilder.populateComments;
import static com.audition.builder.AuditionDataBuilder.populatePost;
import static com.audition.builder.AuditionDataBuilder.populatePosts;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

import com.audition.model.AuditionPost;
import com.audition.model.Comment;
import com.audition.service.AuditionServiceImpl;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;

@ExtendWith(MockitoExtension.class)
@SuppressWarnings({"PMD.BeanMembersShouldSerialize", "PMD.JUnitTestContainsTooManyAsserts"})
class AuditionControllerTest {

    @InjectMocks
    private AuditionController controller;

    @Mock
    private AuditionServiceImpl service;

    @Test
    void testGetPostsSuccess() {

        when(service.getPosts()).thenReturn(populatePosts());
        final Page<AuditionPost> posts = controller.getPosts(0, 2);

        assertEquals(2, posts.getTotalElements());
        assertEquals(1, posts.getTotalPages());
        assertEquals(2, posts.getPageable().getPageSize());

        assertEquals(1, posts.getContent().get(0).getId());
        assertEquals(10, posts.getContent().get(0).getUserId());
        assertEquals("This is title1", posts.getContent().get(0).getTitle());
        assertEquals("This is body1", posts.getContent().get(0).getBody());

        assertEquals(2, posts.getContent().get(1).getId());
        assertEquals(20, posts.getContent().get(1).getUserId());
        assertEquals("This is title2", posts.getContent().get(1).getTitle());
        assertEquals("This is body2", posts.getContent().get(1).getBody());
    }

    @Test
    void testGetPostByIdSuccess() {

        when(service.getPostById(1)).thenReturn(populatePost());
        final AuditionPost post = controller.getPostById(1);

        assertNotNull(post);
        assertEquals(10, post.getId());
        assertEquals(100, post.getUserId());
        assertEquals("This is the title", post.getTitle());
        assertEquals("This is the body", post.getBody());
    }

    @Test
    void testGetCommentsWithPostSuccess() {

        when(service.getCommentsWithPost(1)).thenReturn(populatePost());
        final AuditionPost post = controller.getCommentsWithPost(1);

        assertNotNull(post);
        assertEquals(10, post.getId());
        assertEquals(100, post.getUserId());
        assertEquals("This is the title", post.getTitle());
        assertEquals("This is the body", post.getBody());

        assertEquals(2, post.getComments().size());

        assertEquals(1, post.getComments().get(0).getId());
        assertEquals("John", post.getComments().get(0).getName());
        assertEquals("user1@test.com", post.getComments().get(0).getEmail());
        assertEquals("This is the comment body1", post.getComments().get(0).getBody());
    }

    @Test
    void testGetCommentsSuccess() {

        when(service.getComments(1)).thenReturn(populateComments());
        final List<Comment> comments = controller.getComments(1);

        assertNotNull(comments);
        assertNotNull(comments.get(0));
        assertNotNull(comments.get(1));

        assertEquals(1, comments.get(0).getId());
        assertEquals("John", comments.get(0).getName());
        assertEquals("user1@test.com", comments.get(0).getEmail());
        assertEquals("This is the comment body1", comments.get(0).getBody());

        assertEquals(2, comments.get(1).getId());
        assertEquals("Smith", comments.get(1).getName());
        assertEquals("user2@test.com", comments.get(1).getEmail());
        assertEquals("This is the comment body2", comments.get(1).getBody());
    }
}
