package com.healthcare.api.model;

import jakarta.persistence.*;

@Entity
@Table(name = "insurance")
public class Insurance {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "insurance_seq")
    @SequenceGenerator(name = "insurance_seq", sequenceName = "INSURANCE_SEQ", allocationSize = 1)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;

    private String provider;

    @Column(name = "policy_number", unique = true)
    private String policyNumber;

    private String coverage;

    @Column(name = "valid_until")
    private String validUntil;

    private String status;

    public Insurance() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }

    public String getProvider() { return provider; }
    public void setProvider(String provider) { this.provider = provider; }

    public String getPolicyNumber() { return policyNumber; }
    public void setPolicyNumber(String policyNumber) { this.policyNumber = policyNumber; }

    public String getCoverage() { return coverage; }
    public void setCoverage(String coverage) { this.coverage = coverage; }

    public String getValidUntil() { return validUntil; }
    public void setValidUntil(String validUntil) { this.validUntil = validUntil; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}
