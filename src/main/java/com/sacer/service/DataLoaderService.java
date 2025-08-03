package com.sacer.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.sacer.entity.ServiceAccount;
import com.sacer.entity.Certificate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;

@Service
public class DataLoaderService {
    
    @Autowired
    private ServiceAccountService serviceAccountService;
    
    @Autowired
    private CertificateService certificateService;
    
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final Random random = new Random();
    
    public DataLoaderService() {
        // Configure ObjectMapper to handle Java 8 date/time types
        objectMapper.registerModule(new JavaTimeModule());
        // Configure ObjectMapper to ignore unknown properties
        objectMapper.configure(com.fasterxml.jackson.databind.DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }
    
    @Scheduled(fixedRate = 300000) // 5 minutes = 300000 milliseconds
    public void loadMockData() {
        try {
            loadServiceAccounts();
            loadCertificates();
            System.out.println("Mock data loaded successfully at " + LocalDateTime.now());
        } catch (Exception e) {
            System.err.println("Error loading mock data: " + e.getMessage());
        }
    }
    
    private void loadServiceAccounts() {
        try {
            // Load service accounts from JSON file
            ClassPathResource resource = new ClassPathResource("sa.json");
            
            List<ServiceAccount> serviceAccounts = objectMapper.readValue(
                resource.getInputStream(),
                new TypeReference<List<ServiceAccount>>() {}
            );
            
            // Update expiry dates with random days to expire (20-180 days)
            for (ServiceAccount sa : serviceAccounts) {
                int daysToExpire = random.nextInt(161) + 20; // 20 to 180 days
                sa.setExpiryDate(LocalDateTime.now().plusDays(daysToExpire));
                sa.setLastUpdatedDate(LocalDateTime.now().minusDays(random.nextInt(30)));
            }
            
            // Clear existing data and save new data
            serviceAccountService.saveAllServiceAccounts(serviceAccounts);
            System.out.println("Loaded " + serviceAccounts.size() + " service accounts with random expiry dates (20-180 days)");
        } catch (Exception e) {
            System.err.println("Error loading service accounts from JSON: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    private void loadCertificates() {
        try {
            // Load certificates from JSON file
            ClassPathResource resource = new ClassPathResource("cert.json");
            List<Certificate> certificates = objectMapper.readValue(
                resource.getInputStream(),
                new TypeReference<List<Certificate>>() {}
            );
            
            // Update expiry dates with random days to expire (20-180 days)
            for (Certificate cert : certificates) {
                int daysToExpire = random.nextInt(161) + 20; // 20 to 180 days
                cert.setExpiryDate(LocalDateTime.now().plusDays(daysToExpire));
                cert.setLastUpdatedDate(LocalDateTime.now().minusDays(random.nextInt(30)));
            }
            
            // Clear existing data and save new data
            certificateService.saveAllCertificates(certificates);
            System.out.println("Loaded " + certificates.size() + " certificates with random expiry dates (20-180 days)");
        } catch (Exception e) {
            System.err.println("Error loading certificates from JSON: " + e.getMessage());
            e.printStackTrace();
        }
    }
} 