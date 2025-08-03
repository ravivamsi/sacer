package com.sacer.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "certificates")
public class Certificate {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "cert_name", nullable = false, unique = true)
    private String certName;
    
    @ElementCollection
    @CollectionTable(name = "cert_dependent_components", joinColumns = @JoinColumn(name = "cert_id"))
    @Column(name = "component_name")
    private List<String> dependentComponents;
    
    @Column(name = "dn", nullable = false)
    private String dn;
    
    @Column(name = "expiry_date", nullable = false)
    private LocalDateTime expiryDate;
    
    @Column(name = "last_updated_date")
    private LocalDateTime lastUpdatedDate;
    
    @Column(name = "password")
    private String password;
    
    // Constructors
    public Certificate() {}
    
    public Certificate(String certName, List<String> dependentComponents, String dn, LocalDateTime expiryDate) {
        this.certName = certName;
        this.dependentComponents = dependentComponents;
        this.dn = dn;
        this.expiryDate = expiryDate;
        this.lastUpdatedDate = LocalDateTime.now();
    }
    
    // Getters and Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getCertName() {
        return certName;
    }
    
    public void setCertName(String certName) {
        this.certName = certName;
    }
    
    public List<String> getDependentComponents() {
        return dependentComponents;
    }
    
    public void setDependentComponents(List<String> dependentComponents) {
        this.dependentComponents = dependentComponents;
    }
    
    public String getDn() {
        return dn;
    }
    
    public void setDn(String dn) {
        this.dn = dn;
    }
    
    public LocalDateTime getExpiryDate() {
        return expiryDate;
    }
    
    public void setExpiryDate(LocalDateTime expiryDate) {
        this.expiryDate = expiryDate;
    }
    
    public LocalDateTime getLastUpdatedDate() {
        return lastUpdatedDate;
    }
    
    public void setLastUpdatedDate(LocalDateTime lastUpdatedDate) {
        this.lastUpdatedDate = lastUpdatedDate;
    }
    
    public String getPassword() {
        return password;
    }
    
    public void setPassword(String password) {
        this.password = password;
    }
    
    public boolean isExpired() {
        return LocalDateTime.now().isAfter(expiryDate);
    }
    
    public long getDaysToExpire() {
        return java.time.Duration.between(LocalDateTime.now(), expiryDate).toDays();
    }
} 