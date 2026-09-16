package com.keremerol.campaign_platform.repository;

import com.keremerol.campaign_platform.model.Segment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SegmentRepository extends JpaRepository<Segment, Long> {
}