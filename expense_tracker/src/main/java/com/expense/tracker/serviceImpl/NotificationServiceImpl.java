package com.expense.tracker.serviceImpl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.expense.tracker.dto.Response;
import com.expense.tracker.model.CredentialMaster;
import com.expense.tracker.model.Notification;
import com.expense.tracker.repository.NotificationRepository;
import com.expense.tracker.security.JwtUserDetailsService;
import com.expense.tracker.service.NotificationService;

@Service
public class NotificationServiceImpl implements NotificationService {

	@Autowired
	private NotificationRepository notificationRepository;

	@Autowired
	private JwtUserDetailsService userDetailsService;

	@Override
	public void save(Notification notification) {
		notification.setIsSeen(false);
		notification.setCreatedOn(new Date());
		notification.setUpdatedOn(new Date());
		notificationRepository.save(notification);
	}

	@Override
	public Response<?> getByUserId(long userId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void markNotificationAsSeen(long userId) {
		try {
			Optional<CredentialMaster> master = userDetailsService.getUserDetails();
			if (master != null && master.isPresent()) {
				if (master.get().getUser().getId() == userId) {
					List<Notification> notificationList = notificationRepository
							.getUnSeenNotificationByUserId(master.get().getUser().getId());
					notificationList.forEach(notification -> {
						notification.setIsSeen(true);
						notification.setUpdatedOn(new Date());
					});
					notificationRepository.saveAll(notificationList);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public Response<?> getAllByUserIdCategorized(long userId) {
		try {
			Optional<CredentialMaster> master = userDetailsService.getUserDetails();
			if (master != null && master.isPresent()) {
				if (master.get().getUser().getId() == userId) {
					HashMap<String, List<Notification>> notificationMap = notificationRepository
							.getAllByUserIdCategorized(master.get().getId());
					return new Response<>(HttpStatus.OK.value(), "success", notificationMap);
				} else {
					return new Response<>(HttpStatus.BAD_REQUEST.value(), "You are not authorized", null);
				}
			} else {
				return new Response<>(HttpStatus.BAD_REQUEST.value(), "User not found", null);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new Response<>(HttpStatus.BAD_REQUEST.value(), "Something went wrong", null);
		}

	}

}
