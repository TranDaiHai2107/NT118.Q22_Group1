package com.healthcare.api.repository;

import com.healthcare.api.model.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
    List<Appointment> findByUserId(Long userId);
    List<Appointment> findByUserIdAndStatus(Long userId, String status);
    Optional<Appointment> findByAppointmentId(String appointmentId);
}
