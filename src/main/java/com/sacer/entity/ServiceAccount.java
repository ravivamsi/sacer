package com.sacer.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "service_accounts")
public class ServiceAccount {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "sa_name", nullable = false, unique = true)
    private String saName;
    
    @ElementCollection
    @CollectionTable(name = "sa_dependent_components", joinColumns = @JoinColumn(name = "sa_id"))
    @Column(name = "component_name")
    private List<String> dependentComponents;
    
    @Column(name = "expiry_date", nullable = false)
    private LocalDateTime expiryDate;
    
    @Column(name = "last_updated_date")
    private LocalDateTime lastUpdatedDate;
    
    @Column(name = "password")
    private String password;
    
    // Constructors
    public ServiceAccount() {}
    
    public ServiceAccount(String saName, List<String> dependentComponents, LocalDateTime expiryDate) {
        this.saName = saName;
        this.dependentComponents = dependentComponents;
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
    
    public String getSaName() {
        return saName;
    }
    
    public void setSaName(String saName) {
        this.saName = saName;
    }
    
    public List<String> getDependentComponents() {
        return dependentComponents;
    }
    
    public void setDependentComponents(List<String> dependentComponents) {
        this.dependentComponents = dependentComponents;
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