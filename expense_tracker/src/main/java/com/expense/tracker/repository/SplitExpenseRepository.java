package com.expense.tracker.repository;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.persistence.criteria.Predicate;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.expense.tracker.dto.SearchDto;
import com.expense.tracker.model.Expense;
import com.expense.tracker.model.SplitExpenses;

public interface SplitExpenseRepository extends JpaRepository<SplitExpenses, Long> {

	List<SplitExpenses> findByExpense(Expense expense);

	List<SplitExpenses> findAll(Specification<SplitExpenses> specification);

    default List<SplitExpenses> findByCategoryName(SearchDto searchDto) {
        Specification<SplitExpenses> specification = (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            // Filter by user ID
            if (searchDto.getUserId() != null) {
                predicates.add(criteriaBuilder.equal(root.get("expense").get("expenseBy"), searchDto.getUserId()));
            }

            if (searchDto.getDate() != null) {
                Date startDate, endDate;
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(searchDto.getDate());
                calendar.set(Calendar.HOUR_OF_DAY, 0);
                calendar.set(Calendar.MINUTE, 0);
                calendar.set(Calendar.SECOND, 0);
                startDate = calendar.getTime();

                calendar.set(Calendar.HOUR_OF_DAY, 23);
                calendar.set(Calendar.MINUTE, 59);
                calendar.set(Calendar.SECOND, 59);
                endDate = calendar.getTime();

                predicates.add(criteriaBuilder.between(root.get("createdOn"), startDate, endDate));
            }

            // Filter by category ID
            if (searchDto.getCatagoryId() != null) {
                predicates.add(criteriaBuilder.equal(root.get("expense").get("categoryId"), searchDto.getCatagoryId()));
            }

            // Add additional filters if needed

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };

        return findAll(specification);
    }
    
    
    @Query("SELECT SUM(se.totalAmount) FROM SplitExpenses se WHERE se.userId = :userId AND se.createdOn BETWEEN :startDate AND :endDate")
    Double getTotalExpenseForLast7Days(@Param("userId") Long userId, @Param("startDate") Date startDate, @Param("endDate") Date endDate);
    
    
    @Query("SELECT se.createdOn, SUM(se.totalAmount) " +
            "FROM SplitExpenses se " +
            "WHERE se.userId = :userId " +
            "AND se.createdOn BETWEEN :startDate AND :endDate " +
            "GROUP BY se.createdOn")
    List<Object[]> getTotalExpenseByDate(@Param("userId") Long userId,
                                         @Param("startDate") Date startDate,
                                         @Param("endDate") Date endDate);

}
