package com.healthcare.api.service;

import com.healthcare.api.model.Hospital;
import com.healthcare.api.repository.HospitalRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HospitalService {

    private final HospitalRepository hospitalRepository;

    public HospitalService(HospitalRepository hospitalRepository) {
        this.hospitalRepository = hospitalRepository;
    }

    public List<Hospital> getAllHospitals() {
        return hospitalRepository.findAll();
    }

    public Hospital getHospitalById(Long id) {
        return hospitalRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Hospital not found"));
    }

    public List<Hospital> searchHospitals(String query) {
        return hospitalRepository.findByNameContainingIgnoreCase(query);
    }
}
