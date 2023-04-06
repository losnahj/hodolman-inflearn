package com.blogproject.controller;

import com.blogproject.request.PostCreate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
public class PostController {

    @PostMapping("/v1/posts")
    public ResponseEntity<Map<String, String>> get(@RequestBody @Valid PostCreate req, BindingResult result) {
        log.info("req={}", req);
        if (result.hasErrors()) {
            List<FieldError> fieldErrors = result.getFieldErrors();
            FieldError firstFieldError = fieldErrors.get(0);
            String fieldName = firstFieldError.getField();
            String errorMassage = firstFieldError.getDefaultMessage();

            HashMap<String, String> error = new HashMap<>();
            error.put(fieldName, errorMassage);

            return ResponseEntity.badRequest().body(error);
        }

        return ResponseEntity.ok().build();
    }
}
