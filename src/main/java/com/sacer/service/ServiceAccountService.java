package com.sacer.service;

import com.sacer.entity.ServiceAccount;
import com.sacer.repository.ServiceAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.regex.Pattern;

@Service
public class ServiceAccountService {
    
    @Autowired
    private ServiceAccountRepository serviceAccountRepository;
    
    public List<ServiceAccount> getAllServiceAccounts() {
        return serviceAccountRepository.findAll();
    }
    
    public List<ServiceAccount> getServiceAccountsByName(String saName) {
        return serviceAccountRepository.findBySaNameContainingIgnoreCase(saName);
    }
    
    public List<ServiceAccount> getServiceAccountsByDaysToExpire(int days) {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime futureDate = now.plusDays(days);
        return serviceAccountRepository.findByExpiryDateBetween(now, futureDate);
    }
    
    public ServiceAccount rotatePassword(Long saId, String password) {
        ServiceAccount sa = serviceAccountRepository.findById(saId)
                .orElseThrow(() -> new RuntimeException("Service Account not found"));
        
        if (isValidPassword(password)) {
            sa.setPassword(password);
            sa.setLastUpdatedDate(LocalDateTime.now());
            return serviceAccountRepository.save(sa);
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
    
    public void saveServiceAccount(ServiceAccount serviceAccount) {
        serviceAccountRepository.save(serviceAccount);
    }
    
    public void saveAllServiceAccounts(List<ServiceAccount> serviceAccounts) {
        serviceAccountRepository.saveAll(serviceAccounts);
    }
} 