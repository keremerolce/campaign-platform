package com.keremerol.campaign_platform.service;

import com.keremerol.campaign_platform.model.Customer;
import com.keremerol.campaign_platform.model.Segment;
import com.keremerol.campaign_platform.repository.CustomerRepository;
import com.keremerol.campaign_platform.repository.SegmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private SegmentRepository segmentRepository;

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    private static final String TOPIC = "segment-events";

    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    public Customer createCustomer(Customer customer) {
        Segment assignedSegment = determineSegment(customer.getMonthlySpending());
        customer.setSegment(assignedSegment);
        Customer saved = customerRepository.save(customer);

        String message = String.format("Müşteri '%s' segmentine atandı: %s",
                saved.getFullName(), assignedSegment.getName());
        kafkaTemplate.send(TOPIC, message);

        return saved;
    }

    private Segment determineSegment(Double monthlySpending) {
        List<Segment> segments = segmentRepository.findAll();

        return segments.stream()
                .filter(s -> monthlySpending >= s.getMinSpending())
                .max((s1, s2) -> Double.compare(s1.getMinSpending(), s2.getMinSpending()))
                .orElseThrow(() -> new RuntimeException("Uygun segment bulunamadı"));
    }

    public Customer getCustomerById(Long id) {
        return customerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Customer not found: " + id));
    }
}