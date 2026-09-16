package com.keremerol.campaign_platform.model;

import jakarta.persistence.*;

@Entity
public class Segment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name; // örn: "Premium", "Standart", "Risk Altında"

    private Double minSpending; // bu segmente girmek için minimum harcama eşiği

    public Segment() {}

    public Segment(String name, Double minSpending) {
        this.name = name;
        this.minSpending = minSpending;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public Double getMinSpending() { return minSpending; }
    public void setMinSpending(Double minSpending) { this.minSpending = minSpending; }
}