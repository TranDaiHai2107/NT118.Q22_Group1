package com.healthcare.api.controller;

import com.healthcare.api.dto.ApiResponse;
import com.healthcare.api.model.Hospital;
import com.healthcare.api.service.HospitalService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/hospitals")
public class HospitalController {

    private final HospitalService hospitalService;

    public HospitalController(HospitalService hospitalService) {
        this.hospitalService = hospitalService;
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<Hospital>>> getAllHospitals(
            @RequestParam(required = false) String search) {
        List<Hospital> hospitals;
        if (search != null && !search.isEmpty()) {
            hospitals = hospitalService.searchHospitals(search);
        } else {
            hospitals = hospitalService.getAllHospitals();
        }
        return ResponseEntity.ok(ApiResponse.success(hospitals));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Hospital>> getHospitalById(@PathVariable Long id) {
        try {
            Hospital hospital = hospitalService.getHospitalById(id);
            return ResponseEntity.ok(ApiResponse.success(hospital));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(ApiResponse.error(e.getMessage()));
        }
    }
}
