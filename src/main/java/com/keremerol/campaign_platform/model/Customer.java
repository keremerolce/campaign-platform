package com.keremerol.campaign_platform.model;

import jakarta.persistence.*;

@Entity
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String fullName;
    private Double monthlySpending;

    @ManyToOne
    @JoinColumn(name = "segment_id")
    private Segment segment;

    public Customer() {}

    public Customer(String fullName, Double monthlySpending) {
        this.fullName = fullName;
        this.monthlySpending = monthlySpending;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }

    public Double getMonthlySpending() { return monthlySpending; }
    public void setMonthlySpending(Double monthlySpending) { this.monthlySpending = monthlySpending; }

    public Segment getSegment() { return segment; }
    public void setSegment(Segment segment) { this.segment = segment; }
}