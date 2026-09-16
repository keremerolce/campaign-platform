package com.keremerol.campaign_platform.model;

import jakarta.persistence.*;

@Entity
public class Campaign {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String description;

    @ManyToOne
    @JoinColumn(name = "segment_id")
    private Segment targetSegment; // bu kampanya hangi segmente hitap ediyor

    public Campaign() {}

    public Campaign(String title, String description) {
        this.title = title;
        this.description = description;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public Segment getTargetSegment() { return targetSegment; }
    public void setTargetSegment(Segment targetSegment) { this.targetSegment = targetSegment; }
}