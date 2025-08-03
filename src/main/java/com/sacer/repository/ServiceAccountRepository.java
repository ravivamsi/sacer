package com.sacer.repository;

import com.sacer.entity.ServiceAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ServiceAccountRepository extends JpaRepository<ServiceAccount, Long> {
    
    List<ServiceAccount> findBySaNameContainingIgnoreCase(String saName);
    
    @Query("SELECT sa FROM ServiceAccount sa WHERE sa.expiryDate BETWEEN :startDate AND :endDate")
    List<ServiceAccount> findByExpiryDateBetween(@Param("startDate") LocalDateTime startDate, 
                                                @Param("endDate") LocalDateTime endDate);
    
    @Query("SELECT sa FROM ServiceAccount sa WHERE sa.expiryDate <= :expiryDate")
    List<ServiceAccount> findByExpiryDateBefore(@Param("expiryDate") LocalDateTime expiryDate);
} 