package com.healthcare.api.controller;

import com.healthcare.api.dto.ApiResponse;
import com.healthcare.api.model.User;
import com.healthcare.api.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/profile")
public class ProfileController {

    private final UserService userService;

    public ProfileController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/{userId}")
    public ResponseEntity<ApiResponse<User>> getProfile(@PathVariable Long userId) {
        try {
            User user = userService.getProfile(userId);
            return ResponseEntity.ok(ApiResponse.success(user));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(ApiResponse.error(e.getMessage()));
        }
    }

    @PutMapping("/{userId}")
    public ResponseEntity<ApiResponse<User>> updateProfile(
            @PathVariable Long userId,
            @RequestBody User updates) {
        try {
            User user = userService.updateProfile(userId, updates);
            return ResponseEntity.ok(ApiResponse.success("Profile updated", user));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(ApiResponse.error(e.getMessage()));
        }
    }
}
