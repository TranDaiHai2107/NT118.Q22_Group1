package com.healthcare.api.controller;

import com.healthcare.api.dto.ApiResponse;
import com.healthcare.api.dto.PaymentRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {

    @PostMapping
    public ResponseEntity<ApiResponse<Map<String, Object>>> processPayment(
            @RequestBody PaymentRequest request) {
        Map<String, Object> result = new HashMap<>();
        result.put("transactionId", "TXN-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase());
        result.put("status", "success");
        result.put("amount", request.getAmount());
        result.put("paymentMethod", request.getPaymentMethod());
        return ResponseEntity.ok(ApiResponse.success("Payment processed", result));
    }
}
