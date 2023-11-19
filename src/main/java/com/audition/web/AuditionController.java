package com.audition.web;

import static org.springframework.web.util.HtmlUtils.htmlEscape;

import com.audition.model.AuditionPost;
import com.audition.model.Comment;
import com.audition.service.AuditionService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@SuppressWarnings({"PMD.BeanMembersShouldSerialize"})
public class AuditionController {

    @Autowired
    AuditionService auditionService;

    @RequestMapping(value = "/posts", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody Page<AuditionPost> getPosts(@RequestParam(defaultValue = "0") final int page,
        @RequestParam(defaultValue = "10") final int size) {

        final List<AuditionPost> posts = auditionService.getPosts();
        final int totalSize = posts.size();
        final int startIndex = page * size;
        final int endIndex = Math.min(startIndex + size, totalSize);

        final List<AuditionPost> pageContent = posts.subList(startIndex, endIndex);
        return new PageImpl<>(pageContent, PageRequest.of(page, size),
            totalSize);
    }

    @RequestMapping(value = "/posts/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody AuditionPost getPostById(@PathVariable("id") final String postId) {
        final String sanitizedPostId = htmlEscape(postId);
        return auditionService.getPostById(sanitizedPostId);
    }

    @RequestMapping(value = "/posts/{id}/comments", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody AuditionPost getCommentsWithPost(@PathVariable("id") final String postId) {

        final String sanitizedPostId = htmlEscape(postId);
        return auditionService.getCommentsWithPost(sanitizedPostId);
    }

    @RequestMapping(value = "/comments", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody List<Comment> getComments(@RequestParam("postId") final String postId) {

        final String sanitizedPostId = htmlEscape(postId);
        return auditionService.getComments(sanitizedPostId);
    }

}