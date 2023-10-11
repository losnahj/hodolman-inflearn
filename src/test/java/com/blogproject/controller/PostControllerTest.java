package com.blogproject.controller;

import com.blogproject.domain.Post;
import com.blogproject.repository.PostRepository;
import com.blogproject.request.PostCreate;
import com.blogproject.request.PostUpdate;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.http.MediaType.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc
@SpringBootTest
class PostControllerTest {

    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private PostService postService;


    @AfterEach
    void tearDown() {
        postRepository.deleteAll();
    }

    @Test
    @DisplayName("/posts 요청시 Hello world 를 출력한다.")
    void test() throws Exception {
        // given
        PostCreate postCreate = PostCreate.builder()
                .title("제목입니다.")
                .content("내용입니다.")
                .build();

        String json = objectMapper.writeValueAsString(postCreate);

        System.out.println(json);

        mockMvc.perform(post("/v1/posts")
                        .contentType(APPLICATION_JSON)
                        .content(json)
                )
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @DisplayName("/posts 요청시 Title 값을 필수다.")
    void test2() throws Exception {
        // given
        PostCreate postCreate = PostCreate.builder()
                .content("내용입니다.")
                .build();

        String json = objectMapper.writeValueAsString(postCreate);

        mockMvc.perform(post("/v1/posts")
                        .contentType(APPLICATION_JSON)
                        .content(json)
                )
                .andExpect(status().isBadRequest())
//                .andExpect(content().string("hello world"))
                .andExpect(jsonPath("$.code").value("400"))
                .andExpect(jsonPath("$.message").value("잘못된 요청입니다."))
                .andExpect(jsonPath("$.validation.title").value("타이틀을 입력해주세요."))
                .andDo(print());
    }

    @Test
    @DisplayName("Post 등록 시, DB에 저장이 된다.")
    void Test3() throws Exception {
        // given
        PostCreate postCreate = PostCreate.builder()
                .title("제목입니다.")
                .content("내용입니다.")
                .build();

        String json = objectMapper.writeValueAsString(postCreate);

        mockMvc.perform(post("/v1/posts")
                        .contentType(APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andDo(print());

        assertEquals(1L, postRepository.count());

        Post post = postRepository.findAll().get(0);
        assertEquals("제목입니다.", post.getTitle());
        assertEquals("내용입니다.", post.getContent());
    }

    @Test
    @DisplayName("글 조회 시, 조회 아이디 반환")
    void test4() throws Exception {
        // given

        Post post = Post.builder()
                .title("제목입니다.")
                .content("내용입니다.")
                .build();

        Post savePost = postRepository.save(post);

        mockMvc.perform(get("/v1/posts/{postId}", savePost.getId()))
                .andExpect(status().isOk())
                .andDo(print());

        assertEquals(post.getTitle(), savePost.getTitle());
        assertEquals(post.getContent(), savePost.getContent());
    }

//    @Test
//    @DisplayName("저장된 post가 아닌 경우 에러를 발생한다.")
//    void test5() throws Exception {
//        // given
//        Long postId = 1L;
//        // when
//        mockMvc.perform(get("/v1/posts/{postId}", postId))
//                .andExpect(() -> assertThrows(IllegalArgumentException.class, () -> {
//                    postService.getPost(postId);
//                }))
//                .andDo(print());
//
//        assertThrows(IllegalArgumentException.class, () -> {
//            postService.getPost(postId);
//        }, "존재하지 않는 글입니다.");
//    }

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

        postRepository.saveAll(List.of(post1, post2));

        // when
        MvcResult result = mockMvc.perform(get("/v1/postsList")
                        .contentType(APPLICATION_JSON))
                .andExpect(jsonPath("$.length()", is(2)))
                .andExpect(jsonPath("$[0].id").value(post1.getId()))
                .andExpect(jsonPath("$[0].title").value(post1.getTitle()))
                .andDo(print())
                .andReturn();
    }

    @Test
    void 글1페이지_조회() throws Exception {
        List<Post> postList = IntStream.range(0, 30)
                .mapToObj(i ->
                        Post.builder()
                                .title("제목 - " + i)
                                .content("내용 -" + i)
                                .build()
                )
                .collect(Collectors.toList());

        postRepository.saveAll(postList);


        mockMvc.perform(get("/v1/postsList?page=1&size=5"))
                .andExpect(jsonPath("$.length()").value(5))
                .andExpect(jsonPath("$.[0].id", is(30)))
                .andDo(print());

        // when
//        List<PostResponse> postListPaging = postService.getPostListPaging(1, 5);

        // then
//        assertEquals(5L, postListPaging.size());
    }

    @Test
    @DisplayName("게시글_게시내용_수정시_변경확인")
    void post_update() throws Exception {
        // given
        Post post = Post.builder()
                .title("title 1")
                .content("content 1")
                .build();

        Post save = postRepository.save(post);

        PostUpdate update = PostUpdate.builder()
                .title("title 2")
                .content("content 2")
                .build();


        String json = objectMapper.writeValueAsString(update);

        System.out.println("json = " + json);
        // when
        mockMvc.perform(patch("/v1/posts/{postId}", save.getId())
                        .contentType(APPLICATION_JSON)
                        .content(json))
                .andExpect(jsonPath("$.title", is("title 2")))
                .andDo(print());

        // then
    }

    @Test
    @DisplayName("업데이트시 해당 ID의 post가 존재하지 않을 경우 에러를 반환한다.")
    void post_update_when_NotFound_Post_then_error() throws Exception {
        // given
        Post post = Post.builder()
                .title("title")
                .content("content")
                .build();

        Post savePost = postRepository.save(post);


        // then
//        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> {
//            postService.getPost(savePost.getId() + 1L);
//        });
//
//        assertEquals("존재하지 않는 글입니다.", e.getMessage());

        assertThrows(PostNotFound.class, () -> postService.getPost(savePost.getId() + 1L));
    }
}