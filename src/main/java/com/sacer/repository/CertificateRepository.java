package com.sacer.repository;

import com.sacer.entity.Certificate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface CertificateRepository extends JpaRepository<Certificate, Long> {
    
    List<Certificate> findByCertNameContainingIgnoreCase(String certName);
    
    @Query("SELECT c FROM Certificate c WHERE c.expiryDate BETWEEN :startDate AND :endDate")
    List<Certificate> findByExpiryDateBetween(@Param("startDate") LocalDateTime startDate, 
                                             @Param("endDate") LocalDateTime endDate);
    
    @Query("SELECT c FROM Certificate c WHERE c.expiryDate <= :expiryDate")
    List<Certificate> findByExpiryDateBefore(@Param("expiryDate") LocalDateTime expiryDate);
} 