-- Seed data for Healthcare App (Oracle syntax)

-- Users
MERGE INTO users u USING (SELECT 1 AS id FROM dual) d ON (u.id = d.id)
WHEN NOT MATCHED THEN INSERT (id, name, email, phone, password, address, patient_id)
VALUES (1, 'Sarah Williams', 'sarah.williams@email.com', '+1 (555) 123-4567', 'password123', 'New York, NY', 'PT-123456');

-- Hospitals
MERGE INTO hospitals h USING (SELECT 1 AS id FROM dual) d ON (h.id = d.id)
WHEN NOT MATCHED THEN INSERT (id, name, image, rating, review_count, specialties, distance, price_range, address, phone, operating_hours, latitude, longitude)
VALUES (1, 'City Heart Hospital', 'https://images.unsplash.com/photo-1519494026892-80bbd2d6fd0d?w=800', 4.8, 450, 'Cardiology,Emergency,Surgery', '1.2 km', '$$$', '123 Medical Plaza, Downtown', '+1 (555) 123-4567', '24/7', 37.7749, -122.4194);

MERGE INTO hospitals h USING (SELECT 2 AS id FROM dual) d ON (h.id = d.id)
WHEN NOT MATCHED THEN INSERT (id, name, image, rating, review_count, specialties, distance, price_range, address, phone, operating_hours, latitude, longitude)
VALUES (2, 'Children''s Medical Center', 'https://images.unsplash.com/photo-1587351021759-3e566b6af7cc?w=800', 4.9, 389, 'Pediatrics,Neonatology,Child Surgery', '2.5 km', '$$', '456 Kids Avenue, Northside', '+1 (555) 234-5678', 'Mon-Sat: 8AM-8PM', 37.7849, -122.4094);

MERGE INTO hospitals h USING (SELECT 3 AS id FROM dual) d ON (h.id = d.id)
WHEN NOT MATCHED THEN INSERT (id, name, image, rating, review_count, specialties, distance, price_range, address, phone, operating_hours, latitude, longitude)
VALUES (3, 'Advanced Diagnostic Center', 'https://images.unsplash.com/photo-1538108149393-fbbd81895907?w=800', 4.7, 267, 'Radiology,Laboratory,Imaging', '0.8 km', '$$', '789 Health Street, Midtown', '+1 (555) 345-6789', 'Mon-Fri: 7AM-9PM', 37.7649, -122.4294);

MERGE INTO hospitals h USING (SELECT 4 AS id FROM dual) d ON (h.id = d.id)
WHEN NOT MATCHED THEN INSERT (id, name, image, rating, review_count, specialties, distance, price_range, address, phone, operating_hours, latitude, longitude)
VALUES (4, 'Women''s Wellness Hospital', 'https://images.unsplash.com/photo-1516549655169-df83a0774514?w=800', 4.8, 512, 'Obstetrics,Gynecology,Maternity', '3.1 km', '$$$', '321 Care Boulevard, Eastside', '+1 (555) 456-7890', '24/7', 37.7549, -122.3994);

-- Doctors
MERGE INTO doctors d USING (SELECT 1 AS id FROM dual) s ON (d.id = s.id)
WHEN NOT MATCHED THEN INSERT (id, name, specialization, hospital_id, rating, review_count, experience, consultation_fee, image, next_available, bio, available_slots)
VALUES (1, 'Dr. Sarah Johnson', 'Cardiologist', 1, 4.9, 234, 12, 150, 'https://images.unsplash.com/photo-1559839734-2b71ea197ec2?w=400', 'Today, 3:00 PM', 'Board certified cardiologist with expertise in preventive cardiology and heart disease management.', '09:00 AM,10:00 AM,11:00 AM,02:00 PM,03:00 PM,04:00 PM');

MERGE INTO doctors d USING (SELECT 2 AS id FROM dual) s ON (d.id = s.id)
WHEN NOT MATCHED THEN INSERT (id, name, specialization, hospital_id, rating, review_count, experience, consultation_fee, image, next_available, bio, available_slots)
VALUES (2, 'Dr. Michael Chen', 'Pediatrician', 2, 4.8, 189, 8, 120, 'https://images.unsplash.com/photo-1612349317150-e413f6a5b16d?w=400', 'Tomorrow, 10:00 AM', 'Specialized in pediatric care with a gentle approach to treating children.', '10:00 AM,11:00 AM,01:00 PM,02:00 PM,03:00 PM');

MERGE INTO doctors d USING (SELECT 3 AS id FROM dual) s ON (d.id = s.id)
WHEN NOT MATCHED THEN INSERT (id, name, specialization, hospital_id, rating, review_count, experience, consultation_fee, image, next_available, bio, available_slots)
VALUES (3, 'Dr. Emily Rodriguez', 'Dermatologist', 3, 4.7, 156, 10, 130, 'https://images.unsplash.com/photo-1594824476967-48c8b964273f?w=400', 'Mar 8, 9:00 AM', 'Expert in medical and cosmetic dermatology treatments.', '09:00 AM,10:30 AM,02:00 PM,03:30 PM,04:30 PM');

MERGE INTO doctors d USING (SELECT 4 AS id FROM dual) s ON (d.id = s.id)
WHEN NOT MATCHED THEN INSERT (id, name, specialization, hospital_id, rating, review_count, experience, consultation_fee, image, next_available, bio, available_slots)
VALUES (4, 'Dr. James Wilson', 'Orthopedic', 4, 4.9, 201, 15, 180, 'https://images.unsplash.com/photo-1622253692010-333f2da6031d?w=400', 'Today, 4:00 PM', 'Specializes in sports medicine and joint replacement surgery.', '09:00 AM,11:00 AM,02:00 PM,04:00 PM');

MERGE INTO doctors d USING (SELECT 5 AS id FROM dual) s ON (d.id = s.id)
WHEN NOT MATCHED THEN INSERT (id, name, specialization, hospital_id, rating, review_count, experience, consultation_fee, image, next_available, bio, available_slots)
VALUES (5, 'Dr. Lisa Anderson', 'General Practitioner', 1, 4.8, 312, 14, 100, 'https://images.unsplash.com/photo-1638202993928-7267aad84c31?w=400', 'Today, 2:00 PM', 'Comprehensive primary care for patients of all ages.', '09:00 AM,10:00 AM,11:00 AM,02:00 PM,03:00 PM,04:00 PM,05:00 PM');

-- Appointments
MERGE INTO appointments a USING (SELECT 1 AS id FROM dual) s ON (a.id = s.id)
WHEN NOT MATCHED THEN INSERT (id, appointment_id, user_id, doctor_id, hospital, appointment_date, appointment_time, status, appointment_type, symptoms, qr_code, queue_number, queue_status)
VALUES (1, 'APT001', 1, 1, 'City Heart Hospital', '2026-03-08', '03:00 PM', 'upcoming', 'Specialist Consultation', 'Chest pain, irregular heartbeat', 'QR-APT001', 5, 'waiting');

MERGE INTO appointments a USING (SELECT 2 AS id FROM dual) s ON (a.id = s.id)
WHEN NOT MATCHED THEN INSERT (id, appointment_id, user_id, doctor_id, hospital, appointment_date, appointment_time, status, appointment_type, symptoms, qr_code, queue_number, queue_status)
VALUES (2, 'APT002', 1, 5, 'Family Health Clinic', '2026-03-10', '10:00 AM', 'upcoming', 'General Checkup', NULL, 'QR-APT002', NULL, NULL);

MERGE INTO appointments a USING (SELECT 3 AS id FROM dual) s ON (a.id = s.id)
WHEN NOT MATCHED THEN INSERT (id, appointment_id, user_id, doctor_id, hospital, appointment_date, appointment_time, status, appointment_type, symptoms, qr_code, queue_number, queue_status)
VALUES (3, 'APT003', 1, 2, 'Children''s Medical Center', '2026-02-28', '02:00 PM', 'completed', 'Vaccination', NULL, NULL, NULL, NULL);

MERGE INTO appointments a USING (SELECT 4 AS id FROM dual) s ON (a.id = s.id)
WHEN NOT MATCHED THEN INSERT (id, appointment_id, user_id, doctor_id, hospital, appointment_date, appointment_time, status, appointment_type, symptoms, qr_code, queue_number, queue_status)
VALUES (4, 'APT004', 1, 3, 'Skin Care Clinic', '2026-03-01', '11:00 AM', 'cancelled', 'Dermatology Consultation', NULL, NULL, NULL, NULL);

-- Medical Records
MERGE INTO medical_records r USING (SELECT 1 AS id FROM dual) s ON (r.id = s.id)
WHEN NOT MATCHED THEN INSERT (id, record_id, user_id, record_date, record_type, title, doctor, hospital, details, attachments)
VALUES (1, 'REC001', 1, '2026-02-28', 'appointment', 'Pediatric Checkup', 'Dr. Michael Chen', 'Children''s Medical Center', 'Regular health checkup. Patient is healthy. Received MMR vaccination.', NULL);

MERGE INTO medical_records r USING (SELECT 2 AS id FROM dual) s ON (r.id = s.id)
WHEN NOT MATCHED THEN INSERT (id, record_id, user_id, record_date, record_type, title, doctor, hospital, details, attachments)
VALUES (2, 'REC002', 1, '2026-02-25', 'prescription', 'Antibiotic Prescription', 'Dr. Lisa Anderson', 'Family Health Clinic', 'Amoxicillin 500mg - Take 3 times daily for 7 days', NULL);

MERGE INTO medical_records r USING (SELECT 3 AS id FROM dual) s ON (r.id = s.id)
WHEN NOT MATCHED THEN INSERT (id, record_id, user_id, record_date, record_type, title, doctor, hospital, details, attachments)
VALUES (3, 'REC003', 1, '2026-02-20', 'lab-result', 'Blood Test Results', NULL, 'Advanced Diagnostic Center', 'Complete Blood Count - All values within normal range', 'blood-test.pdf');

MERGE INTO medical_records r USING (SELECT 4 AS id FROM dual) s ON (r.id = s.id)
WHEN NOT MATCHED THEN INSERT (id, record_id, user_id, record_date, record_type, title, doctor, hospital, details, attachments)
VALUES (4, 'REC004', 1, '2026-02-15', 'note', 'Follow-up Required', 'Dr. Sarah Johnson', 'City Heart Hospital', 'Patient advised to return in 3 months for cardiac follow-up', NULL);

-- Notifications
MERGE INTO notifications n USING (SELECT 1 AS id FROM dual) s ON (n.id = s.id)
WHEN NOT MATCHED THEN INSERT (id, notification_id, user_id, notification_type, title, message, notification_time, is_read)
VALUES (1, 'NOT001', 1, 'reminder', 'Appointment Reminder', 'You have an appointment with Dr. Sarah Johnson tomorrow at 3:00 PM', '2 hours ago', 0);

MERGE INTO notifications n USING (SELECT 2 AS id FROM dual) s ON (n.id = s.id)
WHEN NOT MATCHED THEN INSERT (id, notification_id, user_id, notification_type, title, message, notification_time, is_read)
VALUES (2, 'NOT002', 1, 'payment', 'Payment Confirmed', 'Your payment of $150 has been successfully processed', '1 day ago', 0);

MERGE INTO notifications n USING (SELECT 3 AS id FROM dual) s ON (n.id = s.id)
WHEN NOT MATCHED THEN INSERT (id, notification_id, user_id, notification_type, title, message, notification_time, is_read)
VALUES (3, 'NOT003', 1, 'schedule', 'Schedule Changed', 'Dr. Michael Chen''s appointment has been rescheduled to Mar 10', '2 days ago', 1);

MERGE INTO notifications n USING (SELECT 4 AS id FROM dual) s ON (n.id = s.id)
WHEN NOT MATCHED THEN INSERT (id, notification_id, user_id, notification_type, title, message, notification_time, is_read)
VALUES (4, 'NOT004', 1, 'medication', 'Medication Reminder', 'Time to take your medication - Amoxicillin 500mg', '3 days ago', 1);

MERGE INTO notifications n USING (SELECT 5 AS id FROM dual) s ON (n.id = s.id)
WHEN NOT MATCHED THEN INSERT (id, notification_id, user_id, notification_type, title, message, notification_time, is_read)
VALUES (5, 'NOT005', 1, 'follow-up', 'Follow-up Appointment', 'Schedule your follow-up appointment with Dr. Sarah Johnson', '5 days ago', 1);

-- Insurance
MERGE INTO insurance i USING (SELECT 1 AS id FROM dual) s ON (i.id = s.id)
WHEN NOT MATCHED THEN INSERT (id, user_id, provider, policy_number, coverage, valid_until, status)
VALUES (1, 1, 'HealthCare Plus', 'HC-123456789', 'Family Plan', 'Dec 2026', 'active');

