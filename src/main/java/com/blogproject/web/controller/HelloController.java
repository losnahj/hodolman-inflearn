package com.blogproject.web.controller;

import com.blogproject.web.controller.user.UserSession;
import com.blogproject.domain.user.Users;
import com.blogproject.exception.UnauthorizedException;
import com.blogproject.domain.user.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
public class HelloController {

    private final UserRepository userRepository;

    @GetMapping("/hello")
    public String hello() {

        return "hello";
    }

    @PostMapping("/login")
    public UserSession login(@RequestBody UserSession userSession) {

        log.info("userSession >>> {}", userSession);
        Users users = userRepository.findByEmailAndPassword(userSession.getEmail(), userSession.getPassword())
                .orElseThrow(() -> new UnauthorizedException());

        log.info("find user >>> {}", users);

        return userSession;
    }
}
