package com.healthcare.api.service;

import com.healthcare.api.model.MedicalRecord;
import com.healthcare.api.repository.MedicalRecordRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MedicalRecordService {

    private final MedicalRecordRepository medicalRecordRepository;

    public MedicalRecordService(MedicalRecordRepository medicalRecordRepository) {
        this.medicalRecordRepository = medicalRecordRepository;
    }

    public List<MedicalRecord> getUserRecords(Long userId) {
        return medicalRecordRepository.findByUserId(userId);
    }

    public List<MedicalRecord> getUserRecordsByType(Long userId, String type) {
        return medicalRecordRepository.findByUserIdAndType(userId, type);
    }
}
