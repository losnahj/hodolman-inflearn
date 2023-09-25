package com.blogproject.controller;

import com.blogproject.exception.PostNotFound;
import com.blogproject.response.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@ControllerAdvice
public class ExceptionController {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    public ErrorResponse invalid(MethodArgumentNotValidException e) {
        System.out.println("에러를 잡았다!");
        Map<String, String> errors = new HashMap<>();
        List<FieldError> fieldErrors = e.getFieldErrors();
        for (FieldError err : e.getFieldErrors()) {
            errors.put(err.getField(), err.getDefaultMessage());
        }
        ErrorResponse response = ErrorResponse.builder()
                .code("400")
                .message("잘못된 요청입니다.")
                .validation(errors)
                .build();

        return response;
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public void notFoundError(PostNotFound e) {
        System.out.println("Post Not Found 에러 검출");
    }

}
