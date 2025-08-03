package com.sacer.controller;

import com.sacer.entity.Certificate;
import com.sacer.service.CertificateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/certificates")
public class CertificateController {
    
    @Autowired
    private CertificateService certificateService;
    
    @GetMapping
    public ResponseEntity<List<Certificate>> getAllCertificates() {
        return ResponseEntity.ok(certificateService.getAllCertificates());
    }
    
    @GetMapping("/search")
    public ResponseEntity<List<Certificate>> searchCertificates(
            @RequestParam(required = false) String certName,
            @RequestParam(required = false) Integer daysToExpire) {
        
        if (certName != null && !certName.trim().isEmpty()) {
            return ResponseEntity.ok(certificateService.getCertificatesByName(certName));
        } else if (daysToExpire != null) {
            return ResponseEntity.ok(certificateService.getCertificatesByDaysToExpire(daysToExpire));
        } else {
            return ResponseEntity.ok(certificateService.getAllCertificates());
        }
    }
    
    @PostMapping("/{id}/rotate-password")
    public ResponseEntity<Certificate> rotatePassword(
            @PathVariable Long id,
            @RequestParam String password) {
        try {
            Certificate updatedCert = certificateService.rotatePassword(id, password);
            return ResponseEntity.ok(updatedCert);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    @GetMapping("/validate-password")
    public ResponseEntity<Boolean> validatePassword(@RequestParam String password) {
        return ResponseEntity.ok(certificateService.isValidPassword(password));
    }
} 