package com.blogproject.exception;

import java.util.HashMap;
import java.util.Map;

public class Unauthorized extends AbstractException {

    private static final String MESSAGE = "인증이 필요합니다";

    public Unauthorized() {
        super(MESSAGE);
    }

    @Override
    public int getStatusCode() {
        return 401;
    }
}
