package com.blogproject.request;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import static java.lang.Long.*;

@Getter
@Setter
@ToString
public class PostSearch {
    private final int MAX_SIZE = 20;

    private int page;
    private int size;

    public long getOffset() {
        return (long) (Math.max(1, page) - 1) * Math.min(size, MAX_SIZE);
    }
}
