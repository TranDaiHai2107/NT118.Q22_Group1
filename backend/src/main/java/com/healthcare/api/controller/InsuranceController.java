package com.healthcare.api.controller;

import com.healthcare.api.dto.ApiResponse;
import com.healthcare.api.model.Insurance;
import com.healthcare.api.service.InsuranceService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/insurance")
public class InsuranceController {

    private final InsuranceService insuranceService;

    public InsuranceController(InsuranceService insuranceService) {
        this.insuranceService = insuranceService;
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<ApiResponse<List<Insurance>>> getUserInsurance(@PathVariable Long userId) {
        List<Insurance> insuranceList = insuranceService.getUserInsurance(userId);
        return ResponseEntity.ok(ApiResponse.success(insuranceList));
    }

    @PostMapping("/user/{userId}")
    public ResponseEntity<ApiResponse<Insurance>> addInsurance(
            @PathVariable Long userId,
            @RequestBody Insurance insurance) {
        try {
            Insurance saved = insuranceService.addInsurance(userId, insurance);
            return ResponseEntity.ok(ApiResponse.success("Insurance added", saved));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(ApiResponse.error(e.getMessage()));
        }
    }
}
