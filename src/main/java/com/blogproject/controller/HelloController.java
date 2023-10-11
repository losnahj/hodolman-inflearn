package com.blogproject.controller;

import com.blogproject.controller.user.UserSession;
import com.blogproject.domain.Users;
import com.blogproject.exception.UnauthorizedException;
import com.blogproject.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

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
