package com.blogproject.repository;

import com.blogproject.domain.Post;
import com.blogproject.request.PostSearch;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static com.blogproject.domain.QPost.*;

@RequiredArgsConstructor
public class PostRepositoryImpl implements PostRepositoryCustom{

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<Post> getPostsList(PostSearch postSearch) {
        return jpaQueryFactory.selectFrom(post)
                .orderBy(post.id.desc())
                .limit(postSearch.getSize())
                .offset(postSearch.getOffset())
                .fetch();
    }
}
