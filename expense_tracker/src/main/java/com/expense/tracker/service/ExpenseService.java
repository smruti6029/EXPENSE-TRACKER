package com.expense.tracker.service;

import java.util.Date;
import java.util.List;

import com.expense.tracker.dto.ExpenseDTO;
import com.expense.tracker.dto.Response;
import com.expense.tracker.model.Expense;
import com.expense.tracker.model.SplitExpenses;

public interface ExpenseService {

	Response<?> saveExpense(ExpenseDTO expenseDTO);
	Response<?> getExpenseByUserID(Long userId);
	Response<?> getExpenseByUserIDToday(Long userId);
	List<SplitExpenses> getExpensesForUser(Long userId);
	Response<?> getTotalExpenseByDate(Long userId, Date fromDate, Date toDate);
	

}
