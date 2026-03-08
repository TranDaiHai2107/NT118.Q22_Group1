package com.healthcare.api.controller;

import com.healthcare.api.dto.ApiResponse;
import com.healthcare.api.dto.BookingRequest;
import com.healthcare.api.model.Appointment;
import com.healthcare.api.service.AppointmentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/appointments")
public class AppointmentController {

    private final AppointmentService appointmentService;

    public AppointmentController(AppointmentService appointmentService) {
        this.appointmentService = appointmentService;
    }

    @PostMapping("/book")
    public ResponseEntity<ApiResponse<Appointment>> createBooking(@RequestBody BookingRequest request) {
        try {
            Appointment appointment = appointmentService.createBooking(request);
            return ResponseEntity.ok(ApiResponse.success("Booking created", appointment));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(ApiResponse.error(e.getMessage()));
        }
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<ApiResponse<List<Appointment>>> getUserAppointments(
            @PathVariable Long userId,
            @RequestParam(required = false) String status) {
        List<Appointment> appointments;
        if (status != null && !status.isEmpty()) {
            appointments = appointmentService.getUserAppointmentsByStatus(userId, status);
        } else {
            appointments = appointmentService.getUserAppointments(userId);
        }
        return ResponseEntity.ok(ApiResponse.success(appointments));
    }

    @GetMapping("/{appointmentId}")
    public ResponseEntity<ApiResponse<Appointment>> getAppointment(@PathVariable String appointmentId) {
        try {
            Appointment appointment = appointmentService.getByAppointmentId(appointmentId);
            return ResponseEntity.ok(ApiResponse.success(appointment));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(ApiResponse.error(e.getMessage()));
        }
    }

    @PostMapping("/{appointmentId}/checkin")
    public ResponseEntity<ApiResponse<Appointment>> checkIn(@PathVariable String appointmentId) {
        try {
            Appointment appointment = appointmentService.checkIn(appointmentId);
            return ResponseEntity.ok(ApiResponse.success("Checked in", appointment));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(ApiResponse.error(e.getMessage()));
        }
    }

    @PutMapping("/{appointmentId}/cancel")
    public ResponseEntity<ApiResponse<Appointment>> cancel(@PathVariable String appointmentId) {
        try {
            Appointment appointment = appointmentService.cancelAppointment(appointmentId);
            return ResponseEntity.ok(ApiResponse.success("Cancelled", appointment));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(ApiResponse.error(e.getMessage()));
        }
    }
}
