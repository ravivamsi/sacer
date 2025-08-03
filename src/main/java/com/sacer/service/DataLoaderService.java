package com.sacer.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
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
            // Generate 50 mock service accounts
            for (int i = 1; i <= 50; i++) {
                ServiceAccount sa = new ServiceAccount();
                sa.setSaName("SA-" + String.format("%03d", i));
                sa.setDependentComponents(List.of(
                    "Component-" + (i % 10 + 1),
                    "Service-" + (i % 5 + 1),
                    "App-" + (i % 3 + 1)
                ));
                
                // Random expiry date between 1 and 365 days from now
                int daysToExpire = random.nextInt(365) + 1;
                sa.setExpiryDate(LocalDateTime.now().plusDays(daysToExpire));
                sa.setLastUpdatedDate(LocalDateTime.now().minusDays(random.nextInt(30)));
                
                serviceAccountService.saveServiceAccount(sa);
            }
        } catch (Exception e) {
            System.err.println("Error loading service accounts: " + e.getMessage());
        }
    }
    
    private void loadCertificates() {
        try {
            // Generate 50 mock certificates
            for (int i = 1; i <= 50; i++) {
                Certificate cert = new Certificate();
                cert.setCertName("CERT-" + String.format("%03d", i));
                cert.setDependentComponents(List.of(
                    "Component-" + (i % 10 + 1),
                    "Service-" + (i % 5 + 1),
                    "App-" + (i % 3 + 1),
                    "Module-" + (i % 7 + 1)
                ));
                cert.setDn("CN=CERT-" + String.format("%03d", i) + ",OU=IT,O=Company,DC=example,DC=com");
                
                // Random expiry date between 1 and 365 days from now
                int daysToExpire = random.nextInt(365) + 1;
                cert.setExpiryDate(LocalDateTime.now().plusDays(daysToExpire));
                cert.setLastUpdatedDate(LocalDateTime.now().minusDays(random.nextInt(30)));
                
                certificateService.saveCertificate(cert);
            }
        } catch (Exception e) {
            System.err.println("Error loading certificates: " + e.getMessage());
        }
    }
} 