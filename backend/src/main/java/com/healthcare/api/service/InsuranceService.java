package com.healthcare.api.service;

import com.healthcare.api.model.Insurance;
import com.healthcare.api.model.User;
import com.healthcare.api.repository.InsuranceRepository;
import com.healthcare.api.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InsuranceService {

    private final InsuranceRepository insuranceRepository;
    private final UserRepository userRepository;

    public InsuranceService(InsuranceRepository insuranceRepository, UserRepository userRepository) {
        this.insuranceRepository = insuranceRepository;
        this.userRepository = userRepository;
    }

    public List<Insurance> getUserInsurance(Long userId) {
        return insuranceRepository.findByUserId(userId);
    }

    public Insurance addInsurance(Long userId, Insurance insurance) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        insurance.setUser(user);
        return insuranceRepository.save(insurance);
    }
}
