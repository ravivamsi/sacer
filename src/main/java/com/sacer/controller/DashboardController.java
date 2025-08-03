package com.sacer.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DashboardController {
    
    @GetMapping("/")
    public String home() {
        return "redirect:/sa-dashboard";
    }
    
    @GetMapping("/sa-dashboard")
    public String saDashboard() {
        return "sa-dashboard";
    }
    
    @GetMapping("/cert-dashboard")
    public String certDashboard() {
        return "cert-dashboard";
    }
} 