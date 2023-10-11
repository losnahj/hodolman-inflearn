package com.blogproject.service;

import com.blogproject.domain.Post;
import com.blogproject.repository.PostRepository;
import com.blogproject.request.PostCreate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
class PostServiceTest {
    @Autowired
    PostRepository postRepository;
    @Autowired
    PostService postService;

    @BeforeEach
    void setUp() {
        postRepository.deleteAll();
    }

    @Test
    @DisplayName("글 작성")
    void name() {
        // given
        PostCreate postCreate = PostCreate.builder()
                .title("제목입니다.")
                .content("내용입니다.")
                .build();


        // when
        postService.savePost(postCreate);

        // then
        assertEquals(1L, postRepository.count());
    }

    @Test
    @DisplayName("글 1개 등록")
    void 글등록후_조회시_DB_size_1() {
        // given
        Post post = Post.builder()
                .title("1234567")
                .content("non content")
                .build();

        Post save = postRepository.save(post);

        // when
        PostResponse response = postService.getPost(save.getId());

        // then
        assertEquals(1, postRepository.count());
        assertEquals(post.getTitle(), response.getTitle());
        assertEquals(post.getContent(), response.getContent());
    }

    @Test
    @DisplayName("글 전체 조회")
    void 글2개등록후_전체조회() throws Exception {
        // given
        Post post1 = Post.builder()
                .title("title1")
                .content("content1")
                .build();

        Post post2 = Post.builder()
                .title("title2")
                .content("content2")
                .build();

        // when
        postRepository.save(post1);
        postRepository.save(post2);

        // then
        List<PostResponse> postList = postService.getPostList();
        assertEquals(2, postList.size());
    }

}