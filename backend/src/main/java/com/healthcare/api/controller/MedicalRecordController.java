package com.healthcare.api.controller;

import com.healthcare.api.dto.ApiResponse;
import com.healthcare.api.model.MedicalRecord;
import com.healthcare.api.service.MedicalRecordService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/records")
public class MedicalRecordController {

    private final MedicalRecordService medicalRecordService;

    public MedicalRecordController(MedicalRecordService medicalRecordService) {
        this.medicalRecordService = medicalRecordService;
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<ApiResponse<List<MedicalRecord>>> getUserRecords(
            @PathVariable Long userId,
            @RequestParam(required = false) String type) {
        List<MedicalRecord> records;
        if (type != null && !type.isEmpty() && !type.equals("all")) {
            records = medicalRecordService.getUserRecordsByType(userId, type);
        } else {
            records = medicalRecordService.getUserRecords(userId);
        }
        return ResponseEntity.ok(ApiResponse.success(records));
    }
}
