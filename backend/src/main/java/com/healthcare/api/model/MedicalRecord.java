package com.healthcare.api.model;

import jakarta.persistence.*;

@Entity
@Table(name = "medical_records")
public class MedicalRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "medical_record_seq")
    @SequenceGenerator(name = "medical_record_seq", sequenceName = "MEDICAL_RECORD_SEQ", allocationSize = 1)
    private Long id;

    @Column(name = "record_id", unique = true)
    private String recordId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "record_date")
    private String date;

    @Column(name = "record_type")
    private String type;

    private String title;

    private String doctor;

    private String hospital;

    @Column(columnDefinition = "CLOB")
    private String details;

    private String attachments;

    public MedicalRecord() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getRecordId() { return recordId; }
    public void setRecordId(String recordId) { this.recordId = recordId; }

    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }

    public String getDate() { return date; }
    public void setDate(String date) { this.date = date; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDoctor() { return doctor; }
    public void setDoctor(String doctor) { this.doctor = doctor; }

    public String getHospital() { return hospital; }
    public void setHospital(String hospital) { this.hospital = hospital; }

    public String getDetails() { return details; }
    public void setDetails(String details) { this.details = details; }

    public String getAttachments() { return attachments; }
    public void setAttachments(String attachments) { this.attachments = attachments; }
}
