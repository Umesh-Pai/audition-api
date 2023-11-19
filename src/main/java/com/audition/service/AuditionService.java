package com.audition.service;

import com.audition.integration.AuditionIntegrationClient;
import com.audition.model.AuditionPost;
import com.audition.model.Comment;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@SuppressWarnings({"PMD.BeanMembersShouldSerialize"})
public class AuditionService {

    @Autowired
    private AuditionIntegrationClient auditionIntegrationClient;

    public List<AuditionPost> getPosts() {
        return auditionIntegrationClient.getPosts();
    }

    public AuditionPost getPostById(final Integer postId) {
        return auditionIntegrationClient.getPostById(postId);
    }

    public AuditionPost getCommentsWithPost(final Integer postId) {
        final AuditionPost post = auditionIntegrationClient.getPostById(postId);
        final List<Comment> comments = auditionIntegrationClient.getComments(postId);
        post.setComments(comments);
        return post;
    }

    public List<Comment> getComments(final Integer postId) {
        return auditionIntegrationClient.getComments(postId);
    }
}
