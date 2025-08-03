package com.sacer.controller;

import com.sacer.entity.ServiceAccount;
import com.sacer.service.ServiceAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/service-accounts")
public class ServiceAccountController {
    
    @Autowired
    private ServiceAccountService serviceAccountService;
    
    @GetMapping
    public ResponseEntity<List<ServiceAccount>> getAllServiceAccounts() {
        return ResponseEntity.ok(serviceAccountService.getAllServiceAccounts());
    }
    
    @GetMapping("/search")
    public ResponseEntity<List<ServiceAccount>> searchServiceAccounts(
            @RequestParam(required = false) String saName,
            @RequestParam(required = false) Integer daysToExpire) {
        
        if (saName != null && !saName.trim().isEmpty()) {
            return ResponseEntity.ok(serviceAccountService.getServiceAccountsByName(saName));
        } else if (daysToExpire != null) {
            return ResponseEntity.ok(serviceAccountService.getServiceAccountsByDaysToExpire(daysToExpire));
        } else {
            return ResponseEntity.ok(serviceAccountService.getAllServiceAccounts());
        }
    }
    
    @PostMapping("/{id}/rotate-password")
    public ResponseEntity<ServiceAccount> rotatePassword(
            @PathVariable Long id,
            @RequestParam String password) {
        try {
            ServiceAccount updatedSa = serviceAccountService.rotatePassword(id, password);
            return ResponseEntity.ok(updatedSa);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    @GetMapping("/validate-password")
    public ResponseEntity<Boolean> validatePassword(@RequestParam String password) {
        return ResponseEntity.ok(serviceAccountService.isValidPassword(password));
    }
} 