package com.audition.builder;

import com.audition.model.AuditionPost;
import com.audition.model.Comment;
import java.util.ArrayList;
import java.util.List;

public class AuditionDataBuilder {

    public static List<Comment> populateComments() {

        final Comment comment1 = new Comment();
        comment1.setId(1);
        comment1.setName("John");
        comment1.setEmail("user1@test.com");
        comment1.setBody("This is the comment body1");

        final Comment comment2 = new Comment();
        comment2.setId(2);
        comment2.setName("Smith");
        comment2.setEmail("user2@test.com");
        comment2.setBody("This is the comment body2");

        final List<Comment> comments = new ArrayList<>();
        comments.add(comment1);
        comments.add(comment2);
        return comments;
    }

    public static AuditionPost populatePost() {

        final Comment comment1 = new Comment();
        comment1.setId(1);
        comment1.setName("John");
        comment1.setEmail("user1@test.com");
        comment1.setBody("This is the comment body1");

        final Comment comment2 = new Comment();
        comment2.setId(2);
        comment2.setName("Smith");
        comment2.setEmail("user2@test.com");
        comment2.setBody("This is the comment body2");

        final List<Comment> comments = new ArrayList<>();
        comments.add(comment1);
        comments.add(comment2);

        final AuditionPost post = new AuditionPost();
        post.setId(10);
        post.setUserId(100);
        post.setTitle("This is the title");
        post.setBody("This is the body");
        post.setComments(comments);

        return post;
    }

    public static List<AuditionPost> populatePosts() {

        final AuditionPost post1 = new AuditionPost();
        post1.setId(1);
        post1.setUserId(10);
        post1.setTitle("This is title1");
        post1.setBody("This is body1");

        final AuditionPost post2 = new AuditionPost();
        post2.setId(2);
        post2.setUserId(20);
        post2.setTitle("This is title2");
        post2.setBody("This is body2");

        final List<AuditionPost> posts = new ArrayList<>();
        posts.add(post1);
        posts.add(post2);

        return posts;
    }
}
