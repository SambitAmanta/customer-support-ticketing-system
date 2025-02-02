package com.support.customer.support.system.controller;

import com.support.customer.support.system.model.Ticket;
import com.support.customer.support.system.model.User;
import com.support.customer.support.system.service.DashboardService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/dashboard")
public class DashboardController {

    private final DashboardService dashboardService;

    public DashboardController(DashboardService dashboardService) {
        this.dashboardService = dashboardService;
    }

    // Admin Dashboard
    @GetMapping("/admin")
    public ResponseEntity<?> getAdminDashboard(@AuthenticationPrincipal User user) {
        if (!user.getRole().equals(User.Role.ADMIN)) {
            return ResponseEntity.status(403).body("Access denied!");
        }

        Map<String, Object> dashboard = dashboardService.getAdminDashboard();
        return ResponseEntity.ok(dashboard);
    }

    // Agent Dashboard
    @GetMapping("/agent")
    public ResponseEntity<?> getAgentDashboard(@AuthenticationPrincipal User user) {
        if (!user.getRole().equals(User.Role.AGENT)) {
            return ResponseEntity.status(403).body("Access denied!");
        }

        Map<String, Object> dashboard = dashboardService.getAgentDashboard(user);
        return ResponseEntity.ok(dashboard);
    }

    // Customer Dashboard
    @GetMapping("/customer")
    public ResponseEntity<?> getCustomerDashboard(@AuthenticationPrincipal User user) {
        if (!user.getRole().equals(User.Role.CUSTOMER)) {
            return ResponseEntity.status(403).body("Access denied!");
        }

        List<Ticket> tickets = dashboardService.getCustomerDashboard(user);
        return ResponseEntity.ok(tickets);
    }
}

