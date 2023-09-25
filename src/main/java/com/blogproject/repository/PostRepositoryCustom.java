package com.blogproject.repository;

import com.blogproject.domain.Post;
import com.blogproject.request.PostCreate;
import com.blogproject.request.PostSearch;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface PostRepositoryCustom {
    public List<Post> getPostsList(PostSearch postSearch);
//    public Post getPosts(Long id);

//    public Long savePosts(PostCreate postCreate);

//    public Long updatePosts();
//    public Long deletePosts(Long id);
}
