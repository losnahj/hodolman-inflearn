package com.blogproject.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
public class UnauthorizedException extends RuntimeException {

    private static final String MESSAGE = "Unauthorized Access";

    public UnauthorizedException() {
        super(MESSAGE);
    }
}
