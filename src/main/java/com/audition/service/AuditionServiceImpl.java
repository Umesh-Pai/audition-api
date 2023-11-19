package com.audition.service;

import com.audition.integration.AuditionIntegrationClient;
import com.audition.model.AuditionPost;
import com.audition.model.Comment;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@SuppressWarnings({"PMD.BeanMembersShouldSerialize"})
public class AuditionServiceImpl implements AuditionService {

    @Autowired
    private AuditionIntegrationClient auditionIntegrationClient;

    @Override
    public List<AuditionPost> getPosts() {
        return auditionIntegrationClient.getPosts();
    }

    @Override
    public AuditionPost getPostById(final Integer postId) {
        return auditionIntegrationClient.getPostById(postId);
    }

    @Override
    public AuditionPost getCommentsWithPost(final Integer postId) {
        final AuditionPost post = auditionIntegrationClient.getPostById(postId);
        final List<Comment> comments = auditionIntegrationClient.getComments(postId);

        if (null != post) {
            post.setComments(comments);
        }
        return post;
    }

    @Override
    public List<Comment> getComments(final Integer postId) {
        return auditionIntegrationClient.getComments(postId);
    }
}
