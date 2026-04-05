package com.finance.dashboard.service;

import com.finance.dashboard.dto.DashboardSummary;
import com.finance.dashboard.entity.RecordType;
import com.finance.dashboard.entity.Role;
import com.finance.dashboard.repository.FinancialRecordRepository;
import com.finance.dashboard.security.CustomUserDetails;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class DashboardService {

    private final FinancialRecordRepository recordRepository;

    public DashboardService(FinancialRecordRepository recordRepository) {
        this.recordRepository = recordRepository;
    }

    public DashboardSummary getSummary() {
        CustomUserDetails currentUser = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        boolean isAdmin = currentUser.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals(Role.ADMIN.name()));

        BigDecimal totalIncome;
        BigDecimal totalExpense;

        if (isAdmin) {
            totalIncome = recordRepository.getTotalByTypeForAdmin(RecordType.INCOME);
            totalExpense = recordRepository.getTotalByTypeForAdmin(RecordType.EXPENSE);
        } else {
            totalIncome = recordRepository.getTotalByTypeAndUserId(currentUser.getId(), RecordType.INCOME);
            totalExpense = recordRepository.getTotalByTypeAndUserId(currentUser.getId(), RecordType.EXPENSE);
        }

        return new DashboardSummary(totalIncome, totalExpense, totalIncome.subtract(totalExpense));
    }

    public Map<String, BigDecimal> getCategoryWise() {
        CustomUserDetails currentUser = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        boolean isAdmin = currentUser.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals(Role.ADMIN.name()));

        List<Object[]> results;
        if (isAdmin) {
            results = recordRepository.getCategoryWiseSummaryForAdmin();
        } else {
            results = recordRepository.getCategoryWiseSummaryByUserId(currentUser.getId());
        }

        Map<String, BigDecimal> map = new HashMap<>();
        for (Object[] r : results) {
            map.put((String) r[0], (BigDecimal) r[1]);
        }
        return map;
    }

    public Object getRecentTransactions() {
        CustomUserDetails currentUser = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        boolean isAdmin = currentUser.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals(Role.ADMIN.name()));

        PageRequest limit = PageRequest.of(0, 5);
        if (isAdmin) {
            return recordRepository.findTop5RecentForAdmin(limit);
        } else {
            return recordRepository.findTop5RecentByUserId(currentUser.getId(), limit);
        }
    }
}
