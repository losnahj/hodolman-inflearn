package com.blogproject.request;

import com.blogproject.domain.PostUpdator;
import lombok.*;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class PostUpdate {
    @NotBlank(message = "타이틀을 입력해주세요.")
    private String title;

    @NotBlank(message = "제목을 입력해주세요.")
    private String content;

    @Builder
    public PostUpdate(String title, String content) {
        this.title = title;
        this.content = content;
    }

}
