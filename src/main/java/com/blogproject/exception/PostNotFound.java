package com.blogproject.exception;

public class PostNotFound extends RuntimeException {
    private static final String MESSAGE = "Not Found Post";

    public PostNotFound() {
        super(MESSAGE);
    }
}
