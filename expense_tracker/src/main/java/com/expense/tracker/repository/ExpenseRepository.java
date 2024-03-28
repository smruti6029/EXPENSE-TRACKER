package com.expense.tracker.repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.expense.tracker.model.Expense;
import com.expense.tracker.model.SplitExpenses;

@Repository
public interface ExpenseRepository extends JpaRepository<Expense, Long> {

	Optional<Expense> findByCategoryId(Long categoryId);
	
	Optional<Expense> findByCategoryIdAndExpenseBy(Long categoryId, Long expenseBy);
	
	List<Expense> findByExpenseBy(long expenseBy);
	
	List<Expense> findByExpenseByAndExpenseDate(long userId, Date expenseDate);
	
	List<Expense> findByExpenseByOrderByUpdatedOnAsc(long userId);
	
	@Query("SELECT e FROM Expense e WHERE e.expenseDate >= ?1 AND e.expenseDate < ?2")
    List<Expense> findByExpenseDateRange(Date startDate, Date endDate);
	
	//List<SplitExpenses> findByExpenseDashBoard(Long userId);

}
