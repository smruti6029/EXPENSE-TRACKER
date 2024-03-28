package com.expense.tracker.dto;

import java.util.Date;

import com.expense.tracker.model.Expense;
import com.expense.tracker.model.SplitExpenses;

public class AllResponseConversion {
	
	public static SplitExpenses expensetoSplit(Expense expense)
	{
		
		SplitExpenses obj=new SplitExpenses();
		obj.setExpense(expense);
		obj.setTotalAmount(expense.getAmount());
		obj.setCreatedOn(new Date());
		obj.setUserId(expense.getExpenseBy());	
		
		return obj;
	}

}
