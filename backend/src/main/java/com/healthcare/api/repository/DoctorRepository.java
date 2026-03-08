package com.healthcare.api.repository;

import com.healthcare.api.model.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DoctorRepository extends JpaRepository<Doctor, Long> {
    List<Doctor> findBySpecializationContainingIgnoreCase(String specialization);
    List<Doctor> findByNameContainingIgnoreCase(String name);
    List<Doctor> findByHospitalId(Long hospitalId);
}
