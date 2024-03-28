package com.expense.tracker.service;

import java.util.Date;

import com.expense.tracker.dto.Response;
import com.expense.tracker.dto.SearchDto;

public interface SplitExpenseService {

	Response<?> getDallyBAsicExpense(SearchDto searchDto);

	Response<?> getTotalExpenseByDate(Long userId, Date fromDate, Date toDate);

}
