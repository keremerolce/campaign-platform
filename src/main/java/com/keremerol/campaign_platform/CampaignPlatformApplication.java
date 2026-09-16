package com.keremerol.campaign_platform;

import com.keremerol.campaign_platform.model.Segment;
import com.keremerol.campaign_platform.repository.SegmentRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class CampaignPlatformApplication {

	public static void main(String[] args) {
		SpringApplication.run(CampaignPlatformApplication.class, args);
	}

	@Bean
	CommandLineRunner initData(SegmentRepository segmentRepository) {
		return args -> {
			if (segmentRepository.count() == 0) {
				segmentRepository.save(new Segment("Standart", 0.0));
				segmentRepository.save(new Segment("Silver", 1000.0));
				segmentRepository.save(new Segment("Gold", 5000.0));
				segmentRepository.save(new Segment("Premium", 15000.0));
			}
		};
	}
}