package com.blogproject.domain;

import lombok.Builder;
import lombok.Getter;

@Getter
public class PostUpdator {

    private String title;
    private String content;

    @Builder
    public PostUpdator(String title, String content) {
        this.title = title;
        this.content = content;
    }
}
