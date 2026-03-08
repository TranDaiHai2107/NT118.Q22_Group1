package com.healthcare.api.model;

import jakarta.persistence.*;

@Entity
@Table(name = "doctors")
public class Doctor {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "doctor_seq")
    @SequenceGenerator(name = "doctor_seq", sequenceName = "DOCTOR_SEQ", allocationSize = 1)
    private Long id;

    @Column(nullable = false)
    private String name;

    private String specialization;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "hospital_id")
    private Hospital hospital;

    private Double rating;

    @Column(name = "review_count")
    private Integer reviewCount;

    private Integer experience;

    @Column(name = "consultation_fee")
    private Double consultationFee;

    private String image;

    @Column(name = "next_available")
    private String nextAvailable;

    @Column(columnDefinition = "CLOB")
    private String bio;

    @Column(name = "available_slots")
    private String availableSlots;

    public Doctor() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getSpecialization() { return specialization; }
    public void setSpecialization(String specialization) { this.specialization = specialization; }

    public Hospital getHospital() { return hospital; }
    public void setHospital(Hospital hospital) { this.hospital = hospital; }

    public Double getRating() { return rating; }
    public void setRating(Double rating) { this.rating = rating; }

    public Integer getReviewCount() { return reviewCount; }
    public void setReviewCount(Integer reviewCount) { this.reviewCount = reviewCount; }

    public Integer getExperience() { return experience; }
    public void setExperience(Integer experience) { this.experience = experience; }

    public Double getConsultationFee() { return consultationFee; }
    public void setConsultationFee(Double consultationFee) { this.consultationFee = consultationFee; }

    public String getImage() { return image; }
    public void setImage(String image) { this.image = image; }

    public String getNextAvailable() { return nextAvailable; }
    public void setNextAvailable(String nextAvailable) { this.nextAvailable = nextAvailable; }

    public String getBio() { return bio; }
    public void setBio(String bio) { this.bio = bio; }

    public String getAvailableSlots() { return availableSlots; }
    public void setAvailableSlots(String availableSlots) { this.availableSlots = availableSlots; }
}
