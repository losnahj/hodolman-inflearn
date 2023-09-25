package com.blogproject.service;

import com.blogproject.domain.Post;
import com.blogproject.domain.PostUpdator;
import com.blogproject.exception.PostNotFound;
import com.blogproject.repository.PostRepository;
import com.blogproject.request.PostCreate;
import com.blogproject.request.PostSearch;
import com.blogproject.request.PostUpdate;
import com.blogproject.response.PostResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class PostService {

    private final PostRepository postRepository;

    public void savePost(PostCreate postCreate) {
        // repository.save(postCreate)
        Post post = Post.builder()
                .title(postCreate.getTitle())
                .content(postCreate.getContent())
                .build();

        postRepository.save(post);
    }

    public PostResponse getPost(Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new PostNotFound());

        PostResponse response = PostResponse.builder()
                .id(post.getId())
                .title(post.getTitle())
                .content(post.getContent())
                .build();

        return response;
    }

    public List<PostResponse> getPostList() {
        List<Post> postList = postRepository.findAll();
        return postList.stream()
                .map(post -> new PostResponse(post))
                .collect(Collectors.toList());
    }

    public List<PostResponse> getPostListPaging(PostSearch postSearch) {
        return postRepository.getPostsList(postSearch).stream()
                .map(PostResponse::new)
                .collect(Collectors.toList());
    }

    @Transactional
    public PostResponse updatePost(Long postId, PostUpdate req) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 글입니다."));

//        PostUpdator.PostUpdatorBuilder postUpdatorBuilder = post.toUpdator();
//        PostUpdator postUpdator = postUpdatorBuilder.title(req.getTitle())
//                .content(req.getContent())
//                .build();
//
//        post.update(postUpdator);
        post.toUpdate(req);

        return new PostResponse(post);
    }
}

