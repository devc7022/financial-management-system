package com.finance.dashboard.repository;

import com.finance.dashboard.entity.FinancialRecord;
import com.finance.dashboard.entity.RecordType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface FinancialRecordRepository extends JpaRepository<FinancialRecord, Long>, JpaSpecificationExecutor<FinancialRecord> {

    @Query("SELECT COALESCE(SUM(r.amount), 0) FROM FinancialRecord r WHERE r.createdBy.id = :userId AND r.type = :type")
    BigDecimal getTotalByTypeAndUserId(@Param("userId") Long userId, @Param("type") RecordType type);

    @Query("SELECT COALESCE(SUM(r.amount), 0) FROM FinancialRecord r WHERE r.type = :type")
    BigDecimal getTotalByTypeForAdmin(@Param("type") RecordType type);

    Page<FinancialRecord> findByCreatedById(Long userId, Pageable pageable);

    @Query("SELECT r.category, SUM(r.amount) FROM FinancialRecord r WHERE r.createdBy.id = :userId GROUP BY r.category")
    List<Object[]> getCategoryWiseSummaryByUserId(@Param("userId") Long userId);

    @Query("SELECT r.category, SUM(r.amount) FROM FinancialRecord r GROUP BY r.category")
    List<Object[]> getCategoryWiseSummaryForAdmin();

    @Query("SELECT r FROM FinancialRecord r WHERE r.createdBy.id = :userId ORDER BY r.date DESC, r.createdAt DESC")
    List<FinancialRecord> findTop5RecentByUserId(@Param("userId") Long userId, Pageable pageable);

    @Query("SELECT r FROM FinancialRecord r ORDER BY r.date DESC, r.createdAt DESC")
    List<FinancialRecord> findTop5RecentForAdmin(Pageable pageable);
}
