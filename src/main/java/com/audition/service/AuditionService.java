package com.audition.service;

import com.audition.model.AuditionPost;
import com.audition.model.Comment;
import java.util.List;

public interface AuditionService {
    List<AuditionPost> getPosts();

    AuditionPost getPostById(Integer postId);

    AuditionPost getCommentsWithPost(Integer postId);

    List<Comment> getComments(Integer postId);

}
