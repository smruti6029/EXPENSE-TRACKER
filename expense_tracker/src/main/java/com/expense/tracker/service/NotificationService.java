package com.expense.tracker.service;

import com.expense.tracker.dto.Response;
import com.expense.tracker.model.Notification;

public interface NotificationService {

	public void save(Notification notification);

	public Response<?> getByUserId(long userId);

	public Response<?> getAllByUserIdCategorized(long userId);

	public void markNotificationAsSeen(long userId);

}
