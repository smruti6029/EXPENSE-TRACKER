package com.expense.tracker.serviceImpl;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScanPackages;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.expense.tracker.constant.NotificationType;
import com.expense.tracker.dto.AllResponseConversion;
import com.expense.tracker.dto.CategoryDTO;
import com.expense.tracker.dto.ExpenseDTO;
import com.expense.tracker.dto.Response;
import com.expense.tracker.dto.SplitExpensesDTO;
import com.expense.tracker.dto.UserDto;
import com.expense.tracker.model.Category;
import com.expense.tracker.model.CredentialMaster;
import com.expense.tracker.model.Expense;
import com.expense.tracker.model.Notification;
import com.expense.tracker.model.SplitExpenses;
import com.expense.tracker.model.User;
import com.expense.tracker.repository.CategoryRepository;
import com.expense.tracker.repository.ExpenseRepository;
import com.expense.tracker.repository.SplitExpenseRepository;
import com.expense.tracker.repository.UserRepository;
import com.expense.tracker.security.JwtUserDetailsService;
import com.expense.tracker.service.ExpenseService;
import com.expense.tracker.service.NotificationService;
import com.expense.tracker.setter.EntitySetter;
import com.expense.tracker.util.Utility;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class ExpenseServiceImp implements ExpenseService {

	@Autowired
	private ExpenseRepository expenseRepository;

	@Autowired
	private SplitExpenseRepository splitExpenseRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private CategoryRepository categoryRepository;

	@Autowired
	private EntitySetter entitySetter;

	@Autowired
	private NotificationService notificationService;

	@Autowired
	private JwtUserDetailsService userDetailsService;

	@Override
	public Response<?> saveExpense(ExpenseDTO expenseDTO) {

		Optional<CredentialMaster> master = userDetailsService.getUserDetails();
		if (master != null && master.isPresent()) {
			if (master.get().getUser().getId().equals(expenseDTO.getUserId())) {
				if (!expenseDTO.getIsSplit()) {
					Optional<Expense> findByCategoryIdAndExpenseBy = expenseRepository
							.findByCategoryIdAndExpenseBy(expenseDTO.getCategoryId(), expenseDTO.getUserId());

					if (findByCategoryIdAndExpenseBy.isPresent()) {
						Expense expense = findByCategoryIdAndExpenseBy.get();
						expense.setAmount(expense.getAmount() + expenseDTO.getAmount());
						expense.setUpdatedBy(expenseDTO.getUserId());
						expense.setExpenseDate(expenseDTO.getExpenseDate());
						expense.setUpdatedOn(new Date());
						expense.setExpenseBy(expenseDTO.getUserId());
						expense.setCreatedBy(expenseDTO.getUserId());
						expense.setIsActive(true);
						Expense exitingExpense = expenseRepository.save(expense);
						SplitExpenses expensetoSplit = AllResponseConversion.expensetoSplit(exitingExpense);
						expensetoSplit.setUserId(expenseDTO.getUserId());
						expensetoSplit.setCreatedOn(new Date());
						expensetoSplit.setIsActive(true);
						expensetoSplit.setUpdatedBy(expenseDTO.getUserId());
						expensetoSplit.setCreatedBy(expenseDTO.getUserId());
						expensetoSplit.setSplitAmount(0);
						expensetoSplit.setIsPaid(true);
						expensetoSplit.setIsSplit(false);
						splitExpenseRepository.save(expensetoSplit);

					} else {
						Expense expense = ExpenseDTO.fromDtoToEntity(expenseDTO);
						expense.setAmount(expenseDTO.getAmount());
						expense.setUpdatedBy(expenseDTO.getUserId());
						expense.setExpenseDate(expenseDTO.getExpenseDate());
						expense.setUpdatedOn(new Date());
						expense.setExpenseBy(expenseDTO.getUserId());
						expense.setCreatedBy(expenseDTO.getUserId());
						expense.setIsActive(true);
						Expense exitingExpense = expenseRepository.save(expense);
						SplitExpenses splitExpenses = new SplitExpenses();
						SplitExpenses expensetoSplit = AllResponseConversion.expensetoSplit(exitingExpense);
						expensetoSplit.setUserId(expenseDTO.getUserId());
						expensetoSplit.setCreatedOn(new Date());
						expensetoSplit.setIsActive(true);
						expensetoSplit.setUpdatedBy(expenseDTO.getUserId());
						expensetoSplit.setCreatedBy(expenseDTO.getUserId());
						expensetoSplit.setSplitAmount(0);
						expensetoSplit.setIsSplit(false);
						expensetoSplit.setIsPaid(true);
						splitExpenseRepository.save(expensetoSplit);

					}

				} else if (expenseDTO.getIsSplit()) {

					ObjectMapper objectMapper = new ObjectMapper();

					List<Long> splitUsers = expenseDTO.getSplitUsers();
					String splitUsersJson = null;

					try {
						splitUsersJson = objectMapper.writeValueAsString(splitUsers);
					} catch (Exception e) {
						e.printStackTrace();
					}

					System.out.println(splitUsersJson);

					double amount = expenseDTO.getAmount() / splitUsers.size();

					for (Long userID : splitUsers) {
						Optional<Expense> findByCategoryIdAndExpenseBy = expenseRepository
								.findByCategoryIdAndExpenseBy(expenseDTO.getCategoryId(), userID);
						if (findByCategoryIdAndExpenseBy.isPresent()) {
							Expense expense = findByCategoryIdAndExpenseBy.get();
							expense.setAmount(expense.getAmount() + expenseDTO.getAmount());
							expense.setUpdatedBy(expenseDTO.getUserId());
							expense.setExpenseDate(expenseDTO.getExpenseDate());
							expense.setUpdatedOn(new Date());
							expense.setExpenseBy(userID);
							expense.setCreatedBy(expenseDTO.getUserId());
							expense.setIsActive(true);
							Expense exitingExpense = expenseRepository.save(expense);
							SplitExpenses expensetoSplit = AllResponseConversion.expensetoSplit(exitingExpense);
							expensetoSplit.setUserId(userID);
							expensetoSplit.setCreatedOn(new Date());
							expensetoSplit.setIsActive(true);
							expensetoSplit.setUpdatedBy(expenseDTO.getUserId());
							expensetoSplit.setCreatedBy(expenseDTO.getUserId());
							expensetoSplit.setIsSplit(true);
							expensetoSplit.setSplitAmount(amount);
							if (userID.equals(expenseDTO.getUserId())) {
								expensetoSplit.setIsPaid(true);
							} else {

								Notification notification = new Notification();
								notification.setUserId(userID);
								notification.setNotificationType(NotificationType.EXPENSE);
								notification.setText("You friend " + master.get().getUser().getUsername()
										+ " has split the bill with you.");
								notification.setSplitExpenseId(expensetoSplit.getId());
								notificationService.save(notification);

								expensetoSplit.setIsPaid(false);
							}
							expensetoSplit.setSplitusers(splitUsersJson);
							splitExpenseRepository.save(expensetoSplit);

						} else {
							Expense expense = ExpenseDTO.fromDtoToEntity(expenseDTO);
							expense.setAmount(expenseDTO.getAmount());
							expense.setUpdatedBy(expenseDTO.getUserId());
							expense.setExpenseDate(expenseDTO.getExpenseDate());
							expense.setUpdatedOn(new Date());
							expense.setCreatedOn(new Date());
							expense.setExpenseBy(userID);
							expense.setCreatedBy(expenseDTO.getUserId());
							expense.setIsActive(true);
							Expense exitingExpense = expenseRepository.save(expense);
							SplitExpenses expensetoSplit = AllResponseConversion.expensetoSplit(exitingExpense);
							expensetoSplit.setUserId(userID);
							expensetoSplit.setCreatedOn(new Date());
							expensetoSplit.setIsActive(true);
							expensetoSplit.setUpdatedBy(expenseDTO.getUserId());
							expensetoSplit.setCreatedBy(expenseDTO.getUserId());
							expensetoSplit.setSplitAmount(amount);
							expensetoSplit.setIsSplit(true);
							if (userID.equals(expenseDTO.getUserId())) {
								expensetoSplit.setIsPaid(true);
							} else {

								Notification notification = new Notification();
								notification.setUserId(userID);
								notification.setSplitExpenseId(expensetoSplit.getId());
								notification.setNotificationType(NotificationType.EXPENSE);
								notification.setText("You friend " + master.get().getUser().getUsername()
										+ " has split the bill with you.");
								notificationService.save(notification);

								expensetoSplit.setIsPaid(false);
							}
							expensetoSplit.setSplitusers(splitUsersJson);
							splitExpenseRepository.save(expensetoSplit);

						}

					}

				}

				return new Response<>(200, "Expese Add Succesfully", null);
			} else {
				return new Response<>(HttpStatus.BAD_REQUEST.value(), "You have no access to add others expenses",
						null);
			}
		} else {
			return new Response<>(HttpStatus.BAD_REQUEST.value(), "User not found", null);
		}

	}

	@Override
	public Response<?> getExpenseByUserID(Long userId) {
		Optional<CredentialMaster> master = userDetailsService.getUserDetails();
		if (master != null && master.isPresent()) {
			if (master.get().getUser().getId().equals(userId)) {
				List<Expense> findByExpenseBy = expenseRepository.findByExpenseBy(userId);
				List<ExpenseDTO> allExpenseByuserId = new ArrayList<>();
				if (!findByExpenseBy.isEmpty() && findByExpenseBy.size() > 0) {

					for (Expense x : findByExpenseBy) {

						ExpenseDTO fromEntityToDto = Expense.fromEntityToDto(x);

						List<SplitExpenses> findByExpense = splitExpenseRepository.findByExpense(x);

						if (!findByExpense.isEmpty() && findByExpense.size() > 0) {
							List<SplitExpensesDTO> splitExpensesDTOs = new ArrayList<>();

							for (SplitExpenses splitExpense : findByExpense) {
								SplitExpensesDTO dto = SplitExpensesDTO.toDTO(splitExpense);

								System.out.println(splitExpense.getSplitusers());

								String splitusers = splitExpense.getSplitusers();

								System.out.println(splitusers);

								Optional<Category> findById = categoryRepository
										.findById(fromEntityToDto.getCategoryId());
								dto.setCategoryDTO(CategoryDTO.convertEntityToDto(findById.get()));

								if (splitExpense.getIsSplit()) {
									List<Long> jsonToList = Utility.jsonToList(splitusers);
									List<UserDto> users = new ArrayList<>();

								for (Long user_id : jsonToList) {
									Optional<User> findById2 = userRepository.findById(userId);
									users.add(UserDto.toDTO(findById2.get()));
									dto.setSplitUsers(users);
								}
								}

								splitExpensesDTOs.add(dto);

							}
							fromEntityToDto.setSplitExpensesDTOs(splitExpensesDTOs);
						}
						allExpenseByuserId.add(fromEntityToDto);
					}

					return new Response<>(200, "Success", allExpenseByuserId);
				}

				return new Response<>(200, "No Result Found", null);
			} else {
				return new Response<>(HttpStatus.BAD_REQUEST.value(), "You have no access to view others expenses",
						null);
			}
		} else {
			return new Response<>(HttpStatus.BAD_REQUEST.value(), "User not found", null);
		}

	}

	@Override
	public Response<?> getExpenseByUserIDToday(Long userId) {

		Optional<CredentialMaster> master = userDetailsService.getUserDetails();
		if (master != null && master.isPresent()) {
			if (master.get().getUser().getId().equals(userId)) {
				Date today = java.sql.Date.valueOf(LocalDate.now());

				List<Expense> findByExpenseBy = expenseRepository.findByExpenseBy(userId);

				List<ExpenseDTO> allExpenseByuserId = new ArrayList<>();
				if (!findByExpenseBy.isEmpty() && findByExpenseBy.size() > 0) {

					for (Expense x : findByExpenseBy) {

						ExpenseDTO fromEntityToDto = Expense.fromEntityToDto(x);

						Optional<Category> catagoris = categoryRepository.findById(x.getCategoryId());
						Category category = catagoris.get();

						fromEntityToDto.setCategory(entitySetter.toDTO(category));

						List<SplitExpenses> findByExpense = splitExpenseRepository.findByExpense(x);

						if (!findByExpense.isEmpty() && findByExpense.size() > 0) {
							List<SplitExpensesDTO> splitExpensesDTOs = new ArrayList<>();

							fromEntityToDto.setSplitExpensesDTOs(splitExpensesDTOs);
						}
						allExpenseByuserId.add(fromEntityToDto);
					}

					return new Response<>(200, "Success", allExpenseByuserId);
				}

				return new Response<>(400, "No Result Found", null);
			} else {
				return new Response<>(HttpStatus.BAD_REQUEST.value(), "You have no access to view others expenses",
						null);
			}
		} else {
			return new Response<>(HttpStatus.BAD_REQUEST.value(), "User not found", null);
		}

	}

	@Override
	public List<SplitExpenses> getExpensesForUser(Long userId) {

		// return expenseRepository.findByExpenseDashBoard(userId);

		return null;
	}

	@Override
	public Response<?> getTotalExpenseByDate(Long userId, Date fromDate, Date toDate) {
		// TODO Auto-generated method stub
		return null;
	}

}