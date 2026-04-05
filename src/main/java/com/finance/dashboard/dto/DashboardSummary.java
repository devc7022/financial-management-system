package com.finance.dashboard.dto;

import com.finance.dashboard.entity.FinancialRecord;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public class DashboardSummary {
    private BigDecimal totalIncome;
    private BigDecimal totalExpense;
    private BigDecimal netBalance;
    private Map<String, BigDecimal> categorySummaries;
    private List<FinancialRecord> recentTransactions;

    public DashboardSummary() {}

    public DashboardSummary(BigDecimal totalIncome, BigDecimal totalExpense, BigDecimal netBalance) {
        this.totalIncome = totalIncome;
        this.totalExpense = totalExpense;
        this.netBalance = netBalance;
    }

    public BigDecimal getTotalIncome() { return totalIncome; }
    public void setTotalIncome(BigDecimal totalIncome) { this.totalIncome = totalIncome; }
    public BigDecimal getTotalExpense() { return totalExpense; }
    public void setTotalExpense(BigDecimal totalExpense) { this.totalExpense = totalExpense; }
    public BigDecimal getNetBalance() { return netBalance; }
    public void setNetBalance(BigDecimal netBalance) { this.netBalance = netBalance; }
    public Map<String, BigDecimal> getCategorySummaries() { return categorySummaries; }
    public void setCategorySummaries(Map<String, BigDecimal> categorySummaries) { this.categorySummaries = categorySummaries; }
    public List<FinancialRecord> getRecentTransactions() { return recentTransactions; }
    public void setRecentTransactions(List<FinancialRecord> recentTransactions) { this.recentTransactions = recentTransactions; }
}
