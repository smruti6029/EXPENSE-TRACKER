package com.expense.tracker.serviceImpl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.TreeMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.expense.tracker.dto.CategoryDTO;
import com.expense.tracker.dto.ExpenseDTO;
import com.expense.tracker.dto.Response;
import com.expense.tracker.dto.SearchDto;
import com.expense.tracker.dto.SplitExpensesDTO;
import com.expense.tracker.model.Category;
import com.expense.tracker.model.CredentialMaster;
import com.expense.tracker.model.Expense;
import com.expense.tracker.model.SplitExpenses;
import com.expense.tracker.repository.CategoryRepository;
import com.expense.tracker.repository.SplitExpenseRepository;
import com.expense.tracker.security.JwtUserDetailsService;
import com.expense.tracker.service.NotificationService;
import com.expense.tracker.service.SplitExpenseService;

@Service
public class SplitServiceImp implements SplitExpenseService {

	@Autowired
	private SplitExpenseRepository expenseRepository;

	@Autowired
	private CategoryRepository categoryRepository;

	@Autowired
	private NotificationService notificationService;

	@Autowired
	private JwtUserDetailsService userDetailsService;

	@Override
	public Response<?> getDallyBAsicExpense(SearchDto searchDto) {

		Optional<CredentialMaster> master = userDetailsService.getUserDetails();
		if (master != null && master.isPresent()) {
			if (master.get().getUser().getId().equals(searchDto.getUserId())) {
				List<SplitExpenses> findByCatagoryName = expenseRepository.findByCategoryName(searchDto);

				List<SplitExpensesDTO> expensesDTOs = new ArrayList<>();

				if (!findByCatagoryName.isEmpty() && findByCatagoryName.size() > 0) {
					for (SplitExpenses expenses : findByCatagoryName) {
						SplitExpensesDTO dto = SplitExpensesDTO.toDTO(expenses);

						ExpenseDTO fromEntityToDto = Expense.fromEntityToDto(expenses.getExpense());
						Optional<Category> findById = categoryRepository.findById(fromEntityToDto.getCategoryId());
						dto.setCategoryDTO(CategoryDTO.convertEntityToDto(findById.get()));
						dto.setExpenseDTO(Expense.fromEntityToDto(expenses.getExpense()));
						expensesDTOs.add(dto);

					}
					return new Response<>(200, "Success", expensesDTOs);
				}

				return new Response<>(200, "No Result Found", null);
			} else {
				return new Response<>(HttpStatus.BAD_REQUEST.value(), "You have no access", null);
			}
		} else {
			return new Response<>(HttpStatus.BAD_REQUEST.value(), "User not found", null);
		}

	}

	@Override
	public Response<?> getTotalExpenseByDate(Long userId, Date fromDate, Date toDate) {

		Optional<CredentialMaster> master = userDetailsService.getUserDetails();
		if (master != null && master.isPresent()) {
			if (master.get().getUser().getId().equals(userId)) {
				List<Object[]> expenses = expenseRepository.getTotalExpenseByDate(userId, fromDate, toDate);

				Map<String, Double> totalExpensesByDate = new HashMap<>();

				SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

				Calendar calendar = Calendar.getInstance();
				calendar.setTime(fromDate);
				while (!calendar.getTime().after(toDate)) {
					totalExpensesByDate.put(dateFormat.format(calendar.getTime()), 0.0);
					calendar.add(Calendar.DATE, 1);
				}

				for (Object[] expense : expenses) {
					String formattedDate = dateFormat.format(expense[0]);
					Double totalAmount = (Double) expense[1];
					totalExpensesByDate.put(formattedDate, totalAmount);
				}

				TreeMap<String, Double> sortedExpensesByDate = new TreeMap<>(totalExpensesByDate);

				List<Map<String, String>> expensesByDate = new ArrayList<>();
				for (Map.Entry<String, Double> entry : sortedExpensesByDate.entrySet()) {
					Map<String, String> expenseMap = new HashMap<>();
					expenseMap.put("date", entry.getKey());
					expenseMap.put("totalExpense", String.valueOf(entry.getValue()));
					expensesByDate.add(expenseMap);
				}

				return new Response<>(200, "Total expenses by date", expensesByDate);
			} else {
				return new Response<>(HttpStatus.BAD_REQUEST.value(), "You have no access", null);
			}
		} else {
			return new Response<>(HttpStatus.BAD_REQUEST.value(), "User not found", null);
		}

	}

}
