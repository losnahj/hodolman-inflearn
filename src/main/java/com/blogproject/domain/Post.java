package com.blogproject.domain;

import com.blogproject.request.PostUpdate;
import lombok.*;

import javax.persistence.*;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PUBLIC)
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @Lob
    private String content;

    @Builder
    public Post(Long id, String title, String content) {
        this.id = id;
        this.title = title;
        this.content = content;
    }

    public PostUpdator.PostUpdatorBuilder toUpdator() {
        return PostUpdator.builder()
                .title(this.title)
                .content(this.content);
    }

    public void update(PostUpdator postUpdator) {
        this.title = postUpdator.getTitle();
        this.content = postUpdator.getContent();
    }

    public Post toUpdate(PostUpdate req) {
        this.title = req.getTitle() != null ? req.getTitle() : this.title;
        this.content = req.getContent() != null ? req.getContent() : this.content;

        return this;
    }
}
