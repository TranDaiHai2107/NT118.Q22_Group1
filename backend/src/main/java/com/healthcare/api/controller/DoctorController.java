package com.healthcare.api.controller;

import com.healthcare.api.dto.ApiResponse;
import com.healthcare.api.model.Doctor;
import com.healthcare.api.service.DoctorService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/doctors")
public class DoctorController {

    private final DoctorService doctorService;

    public DoctorController(DoctorService doctorService) {
        this.doctorService = doctorService;
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<Doctor>>> getAllDoctors(
            @RequestParam(required = false) String search) {
        List<Doctor> doctors;
        if (search != null && !search.isEmpty()) {
            doctors = doctorService.searchDoctors(search);
        } else {
            doctors = doctorService.getAllDoctors();
        }
        return ResponseEntity.ok(ApiResponse.success(doctors));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Doctor>> getDoctorById(@PathVariable Long id) {
        try {
            Doctor doctor = doctorService.getDoctorById(id);
            return ResponseEntity.ok(ApiResponse.success(doctor));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(ApiResponse.error(e.getMessage()));
        }
    }

    @GetMapping("/hospital/{hospitalId}")
    public ResponseEntity<ApiResponse<List<Doctor>>> getDoctorsByHospital(@PathVariable Long hospitalId) {
        List<Doctor> doctors = doctorService.getDoctorsByHospital(hospitalId);
        return ResponseEntity.ok(ApiResponse.success(doctors));
    }
}
