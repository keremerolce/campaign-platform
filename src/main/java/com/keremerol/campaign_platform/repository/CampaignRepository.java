package com.keremerol.campaign_platform.repository;

import com.keremerol.campaign_platform.model.Campaign;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CampaignRepository extends JpaRepository<Campaign, Long> {
}