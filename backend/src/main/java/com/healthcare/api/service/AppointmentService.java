package com.healthcare.api.service;

import com.healthcare.api.dto.BookingRequest;
import com.healthcare.api.model.Appointment;
import com.healthcare.api.model.Doctor;
import com.healthcare.api.model.User;
import com.healthcare.api.repository.AppointmentRepository;
import com.healthcare.api.repository.DoctorRepository;
import com.healthcare.api.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class AppointmentService {

    private final AppointmentRepository appointmentRepository;
    private final UserRepository userRepository;
    private final DoctorRepository doctorRepository;

    public AppointmentService(AppointmentRepository appointmentRepository,
                              UserRepository userRepository,
                              DoctorRepository doctorRepository) {
        this.appointmentRepository = appointmentRepository;
        this.userRepository = userRepository;
        this.doctorRepository = doctorRepository;
    }

    public Appointment createBooking(BookingRequest request) {
        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));
        Doctor doctor = doctorRepository.findById(request.getDoctorId())
                .orElseThrow(() -> new RuntimeException("Doctor not found"));

        Appointment appointment = new Appointment();
        appointment.setAppointmentId("APT-" + UUID.randomUUID().toString().substring(0, 9).toUpperCase());
        appointment.setUser(user);
        appointment.setDoctor(doctor);
        appointment.setHospital(doctor.getHospital() != null ? doctor.getHospital().getName() : "");
        appointment.setDate(request.getDate());
        appointment.setTime(request.getTime());
        appointment.setType(request.getType());
        appointment.setSymptoms(request.getSymptoms());
        appointment.setStatus("upcoming");
        appointment.setQrCode("QR-" + appointment.getAppointmentId());
        appointment.setQueueNumber((int) (Math.random() * 20) + 1);
        appointment.setQueueStatus("waiting");

        return appointmentRepository.save(appointment);
    }

    public List<Appointment> getUserAppointments(Long userId) {
        return appointmentRepository.findByUserId(userId);
    }

    public List<Appointment> getUserAppointmentsByStatus(Long userId, String status) {
        return appointmentRepository.findByUserIdAndStatus(userId, status);
    }

    public Appointment getByAppointmentId(String appointmentId) {
        return appointmentRepository.findByAppointmentId(appointmentId)
                .orElseThrow(() -> new RuntimeException("Appointment not found"));
    }

    public Appointment checkIn(String appointmentId) {
        Appointment appointment = getByAppointmentId(appointmentId);
        appointment.setQueueStatus("waiting");
        return appointmentRepository.save(appointment);
    }

    public Appointment cancelAppointment(String appointmentId) {
        Appointment appointment = getByAppointmentId(appointmentId);
        appointment.setStatus("cancelled");
        return appointmentRepository.save(appointment);
    }
}
