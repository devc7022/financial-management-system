package com.finance.dashboard.controller;

import com.finance.dashboard.dto.DashboardSummary;
import com.finance.dashboard.service.DashboardService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.Map;

@RestController
@RequestMapping("/api/dashboard")
@PreAuthorize("hasAnyAuthority('ADMIN', 'ANALYST', 'VIEWER')")
public class DashboardController {

    private final DashboardService dashboardService;

    public DashboardController(DashboardService dashboardService) {
        this.dashboardService = dashboardService;
    }

    @GetMapping("/summary")
    public ResponseEntity<DashboardSummary> getSummary() {
        return ResponseEntity.ok(dashboardService.getSummary());
    }

    @GetMapping("/category-wise")
    public ResponseEntity<Map<String, BigDecimal>> getCategoryWiseSummary() {
        return ResponseEntity.ok(dashboardService.getCategoryWise());
    }

    @GetMapping("/recent")
    public ResponseEntity<Object> getRecentTransactions() {
        return ResponseEntity.ok(dashboardService.getRecentTransactions());
    }
}
