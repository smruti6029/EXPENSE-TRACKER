package com.expense.tracker.repository;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.expense.tracker.constant.NotificationType;
import com.expense.tracker.model.Notification;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {

	@Query(value = "select * from notification where user_id=?1", nativeQuery = true)
	List<Notification> getByUserId(long userId);

	@Query(value = "SELECT * FROM notification WHERE user_id = ?1 AND is_seen = false", nativeQuery = true)
	List<Notification> getUnSeenNotificationByUserId(long userId);

	@Query(value = "SELECT * FROM notification WHERE user_id = ?1 ORDER BY created_on DESC", nativeQuery = true)
	List<Notification> getAllByUserId(long userId);

	default HashMap<String, List<Notification>> getAllByUserIdCategorized(long userId) {
		try {
			List<Notification> allNotifications = getAllByUserId(userId);

			HashMap<String, List<Notification>> notificationMap = new HashMap<>();

			// Filtering according to notification type
			List<Notification> expenseNotifications = Optional.ofNullable(allNotifications.stream()
					.filter(notification -> notification.getNotificationType().equals(NotificationType.EXPENSE))
					.collect(Collectors.toList())).orElse(List.of());

			List<Notification> requestNotifications = Optional.ofNullable(allNotifications.stream()
					.filter(notification -> notification.getNotificationType().equals(NotificationType.REQUEST))
					.collect(Collectors.toList())).orElse(List.of());

			notificationMap.put("Expense_Notifications", expenseNotifications);
			notificationMap.put("Request_Notifications", requestNotifications);

			return notificationMap;
		} catch (Exception e) {
			e.printStackTrace();
			return new HashMap<>();
		}

	}
}
