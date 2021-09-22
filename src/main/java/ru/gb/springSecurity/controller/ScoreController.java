package ru.gb.springSecurity.controller;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.gb.springSecurity.dto.UserDto;
import ru.gb.springSecurity.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/v1/app/score")
public class ScoreController {
    private final UserService userService;

    @GetMapping("/inc")
    public UserDto incrementScore(Principal principal) {
        return userService.incrementScore(principal.getName());
    }

    @GetMapping("/dec")
    public UserDto decrementScore(Principal principal) {
        return userService.decrementScore(principal.getName());
    }

    @GetMapping("/get/current")
    public Integer getCurrentUserScore(Principal principal){
        return userService.getScore(principal.getName(), null);
    }

    @GetMapping("/get/{id}")
    public Integer getUserScoreById(@PathVariable(value = "id") int id){
        return userService.getScore(null, id);
    }
}
