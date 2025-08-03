package com.sacer.service;

import com.sacer.entity.Certificate;
import com.sacer.repository.CertificateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class CertificateService {
    
    @Autowired
    private CertificateRepository certificateRepository;
    
    public List<Certificate> getAllCertificates() {
        return certificateRepository.findAll();
    }
    
    public List<Certificate> getCertificatesByName(String certName) {
        return certificateRepository.findByCertNameContainingIgnoreCase(certName);
    }
    
    public List<Certificate> getCertificatesByDaysToExpire(int days) {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime futureDate = now.plusDays(days);
        return certificateRepository.findByExpiryDateBetween(now, futureDate);
    }
    
    public Certificate rotatePassword(Long certId, String password) {
        Certificate cert = certificateRepository.findById(certId)
                .orElseThrow(() -> new RuntimeException("Certificate not found"));
        
        if (isValidPassword(password)) {
            cert.setPassword(password);
            cert.setLastUpdatedDate(LocalDateTime.now());
            return certificateRepository.save(cert);
        } else {
            throw new IllegalArgumentException("Password must contain exactly 3 letters and 12 digits");
        }
    }
    
    public boolean isValidPassword(String password) {
        if (password == null || password.length() != 15) {
            return false;
        }
        
        // Check for exactly 3 letters and 12 digits
        long letterCount = password.chars().filter(Character::isLetter).count();
        long digitCount = password.chars().filter(Character::isDigit).count();
        
        return letterCount == 3 && digitCount == 12;
    }
    
    public void saveCertificate(Certificate certificate) {
        certificateRepository.save(certificate);
    }
    
    public void saveAllCertificates(List<Certificate> certificates) {
        // Clear existing data first
        certificateRepository.deleteAll();
        // Save new data
        certificateRepository.saveAll(certificates);
    }
} 