package com.yoga.medledger.controller;

import com.yoga.medledger.dto.ApiResponse;
import com.yoga.medledger.dto.RegisterRequest;
import com.yoga.medledger.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/register")
    public ApiResponse register(@Valid @RequestBody RegisterRequest request) {
        userService.register(request);
        return new ApiResponse(true, "User registered successfully");
    }

    @GetMapping("/me")
    public String me(Authentication authentication) {
        return authentication.getName();
    }
}