package com.finance.dashboard.service;

import com.finance.dashboard.dto.FinancialRecordRequest;
import com.finance.dashboard.entity.FinancialRecord;
import com.finance.dashboard.entity.RecordType;
import com.finance.dashboard.entity.Role;
import com.finance.dashboard.entity.User;
import com.finance.dashboard.exception.ResourceNotFoundException;
import com.finance.dashboard.exception.UnauthorizedException;
import com.finance.dashboard.repository.FinancialRecordRepository;
import com.finance.dashboard.repository.UserRepository;
import com.finance.dashboard.security.CustomUserDetails;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class FinancialRecordService {

    private final FinancialRecordRepository recordRepository;
    private final UserRepository userRepository;

    public FinancialRecordService(FinancialRecordRepository recordRepository, UserRepository userRepository) {
        this.recordRepository = recordRepository;
        this.userRepository = userRepository;
    }

    public FinancialRecord createRecord(FinancialRecordRequest request) {
        User currentUser = getCurrentUser();
        
        FinancialRecord record = new FinancialRecord(request.getAmount(), request.getType(), request.getCategory(), request.getDate(), request.getDescription(), currentUser);

        return recordRepository.save(record);
    }

    public FinancialRecord updateRecord(Long id, FinancialRecordRequest request) {
        FinancialRecord record = getRecordAuthorized(id);
        
        record.setAmount(request.getAmount());
        record.setType(request.getType());
        record.setCategory(request.getCategory());
        record.setDate(request.getDate());
        record.setDescription(request.getDescription());

        return recordRepository.save(record);
    }

    public void deleteRecord(Long id) {
        FinancialRecord record = getRecordAuthorized(id);
        recordRepository.delete(record);
    }

    public Page<FinancialRecord> getFilteredRecords(RecordType type, String category, LocalDate startDate, LocalDate endDate, Pageable pageable) {
        User currentUser = getCurrentUser();
        
        Specification<FinancialRecord> spec = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            
            if (currentUser.getRole() != Role.ADMIN) {
                predicates.add(cb.equal(root.get("createdBy").get("id"), currentUser.getId()));
            }

            if (type != null) {
                predicates.add(cb.equal(root.get("type"), type));
            }
            if (category != null && !category.isEmpty()) {
                predicates.add(cb.equal(root.get("category"), category));
            }
            if (startDate != null) {
                predicates.add(cb.greaterThanOrEqualTo(root.get("date"), startDate));
            }
            if (endDate != null) {
                predicates.add(cb.lessThanOrEqualTo(root.get("date"), endDate));
            }
            
            return cb.and(predicates.toArray(new Predicate[0]));
        };

        return recordRepository.findAll(spec, pageable);
    }

    private User getCurrentUser() {
        CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return userRepository.findById(userDetails.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Logged in user not found"));
    }

    private FinancialRecord getRecordAuthorized(Long recordId) {
        FinancialRecord record = recordRepository.findById(recordId)
                .orElseThrow(() -> new ResourceNotFoundException("Record not found"));
        
        User currentUser = getCurrentUser();
        if (currentUser.getRole() != Role.ADMIN && !record.getCreatedBy().getId().equals(currentUser.getId())) {
            throw new UnauthorizedException("You do not have permission to modify this record");
        }
        return record;
    }
}
