package com.blogproject.controller;

import com.blogproject.domain.Post;
import com.blogproject.request.PostCreate;
import com.blogproject.request.PostSearch;
import com.blogproject.request.PostUpdate;
import com.blogproject.response.PostResponse;
import com.blogproject.service.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.websocket.server.PathParam;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@RestController
public class PostController {

    private final PostService postService;

    @GetMapping("/test")
    public String test() {
        return "hello";
    }

    @PostMapping("/v1/posts")
    public void save(@RequestBody @Valid PostCreate req) {
        log.info("req={}", req);
        postService.savePost(req);
    }

    @GetMapping("/v1/posts/{postId}")
    public PostResponse getPost(@PathVariable(value = "postId") Long postId) {
        PostResponse response = postService.getPost(postId);
        return response;
    }

    @GetMapping("/v1/postsList")
    public List<PostResponse> getPostList(PostSearch postSearch) {
        List<PostResponse> postList = postService.getPostListPaging(postSearch);
        return postList;
    }

    @PatchMapping("/v1/posts/{postId}")
    public PostResponse update(@PathVariable("postId") Long postId, @RequestBody @Valid PostUpdate req) {
        return postService.updatePost(postId, req);
    }
}
