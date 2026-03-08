package com.healthcare.api.service;

import com.healthcare.api.model.Doctor;
import com.healthcare.api.repository.DoctorRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DoctorService {

    private final DoctorRepository doctorRepository;

    public DoctorService(DoctorRepository doctorRepository) {
        this.doctorRepository = doctorRepository;
    }

    public List<Doctor> getAllDoctors() {
        return doctorRepository.findAll();
    }

    public Doctor getDoctorById(Long id) {
        return doctorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Doctor not found"));
    }

    public List<Doctor> searchDoctors(String query) {
        List<Doctor> byName = doctorRepository.findByNameContainingIgnoreCase(query);
        List<Doctor> bySpec = doctorRepository.findBySpecializationContainingIgnoreCase(query);
        byName.addAll(bySpec);
        return byName.stream().distinct().toList();
    }

    public List<Doctor> getDoctorsByHospital(Long hospitalId) {
        return doctorRepository.findByHospitalId(hospitalId);
    }
}
