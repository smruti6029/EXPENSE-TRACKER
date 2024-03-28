package com.expense.tracker.serviceImpl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.expense.tracker.constant.NotificationType;
import com.expense.tracker.dto.FriendRequest;
import com.expense.tracker.dto.Response;
import com.expense.tracker.enums.FriendshipEnum;
import com.expense.tracker.model.CredentialMaster;
import com.expense.tracker.model.Friends;
import com.expense.tracker.model.Notification;
import com.expense.tracker.model.User;
import com.expense.tracker.repository.FriendsRepository;
import com.expense.tracker.repository.UserRepository;
import com.expense.tracker.security.JwtUserDetailsService;
import com.expense.tracker.service.FriendshipService;
import com.expense.tracker.service.NotificationService;

@Service
public class FriendshipServiceImpl implements FriendshipService {

	@Autowired
	private FriendsRepository friendshipRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private NotificationService notificationService;

	@Autowired
	private JwtUserDetailsService userDetailsService;

	@Override
	public Response<?> sendFriendStatus(FriendRequest friendRequest) {

		Optional<CredentialMaster> master = userDetailsService.getUserDetails();
		if (master != null && master.isPresent()) {
			if (master.get().getUser().getId().equals(friendRequest.getSenderId())) {

				Friends existingFriendship = friendshipRepository.findByUserID1AndUserID2(friendRequest.getSenderId(),
						friendRequest.getReceiverId());

				if (existingFriendship != null) {
					if (friendRequest.getStatus() == FriendshipEnum.Pending.getId()) {

						if ((friendRequest.getSenderId() == existingFriendship.getUserID1())) {
							return new Response<>(HttpStatus.BAD_REQUEST.value(),
									"Friend Request status is already pending !", null);
						} else if (friendRequest.getSenderId() == existingFriendship.getUserID2()) {
							return new Response<>(HttpStatus.BAD_REQUEST.value(),
									"Friend Request is not Accepted By you !", null);
						}

					}
					Notification notification = new Notification();
					notification.setNotificationType(NotificationType.REQUEST);
					if (existingFriendship.getUserID2() == friendRequest.getSenderId()) {
						existingFriendship.setStatus(friendRequest.getStatus());
						existingFriendship.setUpdatedOn(new Date());

						Friends updatedFriendship = friendshipRepository.save(existingFriendship);

						if (friendRequest.getStatus().equals(FriendshipEnum.Accepted.getId())) {
							notification.setUserId(friendRequest.getReceiverId());
							notification.setText(
									master.get().getUser().getUsername() + " has accepted your friend request.");
						} else {
							if (friendRequest.getStatus().equals(FriendshipEnum.Rejected.getId())) {
								notification.setUserId(friendRequest.getReceiverId());
								notification.setText(
										master.get().getUser().getUsername() + " has rejected your friend request.");
							}
						}
						notificationService.save(notification);

						if (updatedFriendship != null) {
							return new Response<>(HttpStatus.OK.value(), "Friendship status updated successfully",
									updatedFriendship);
						} else {
							return new Response<>(HttpStatus.BAD_REQUEST.value(),
									"Failed to update friendship status !", null);
						}
					} else if (existingFriendship.getUserID1() == friendRequest.getSenderId()
							&& (friendRequest.getStatus() == FriendshipEnum.Accepted.getId()
									|| friendRequest.getStatus() == FriendshipEnum.Rejected.getId())) {
						return new Response<>(HttpStatus.BAD_REQUEST.value(), "You cannot update friendship status",
								null);
					} else {
						return new Response<>(HttpStatus.BAD_REQUEST.value(), "Failed to update friendship status !",
								null);
					}

				} else {

					Notification notification = new Notification();
					notification.setNotificationType(NotificationType.REQUEST);

					Friends newFriendship = new Friends();
					newFriendship.setUserID1(friendRequest.getSenderId());
					newFriendship.setUserID2(friendRequest.getReceiverId());
					newFriendship.setStatus(friendRequest.getStatus());
					newFriendship.setCreatedOn(new Date());
					newFriendship.setUpdatedOn(new Date());
					newFriendship.setIsActive(true);

					Friends savedFriendship = friendshipRepository.save(newFriendship);
					if (friendRequest.getStatus().equals(FriendshipEnum.Pending.getId())) {
						notification.setUserId(friendRequest.getReceiverId());
						notification
								.setText("You have a new friend request from " + master.get().getUser().getUsername());
					}

					notificationService.save(notification);

					if (savedFriendship != null) {
						return new Response<>(HttpStatus.CREATED.value(), "Friendship created successfully",
								savedFriendship);
					} else {
						return new Response<>(HttpStatus.BAD_REQUEST.value(), "Failed to create friendship", null);
					}
				}
			} else {
				return new Response<>(HttpStatus.BAD_REQUEST.value(), "You have no access.", null);
			}
		} else {
			return new Response<>(HttpStatus.BAD_REQUEST.value(), "User not found", null);
		}

	}

	@Override
	public Response<List<?>> getPendingFriendRequests(Long userID) {

		try {

			Optional<CredentialMaster> master = userDetailsService.getUserDetails();
			if (master != null && master.isPresent()) {
				if (master.get().getUser().getId().equals(userID)) {
					List<Friends> pendingFriends = friendshipRepository.findByUserID1OrUserID2AndStatus(userID,
							FriendshipEnum.Pending.getId());

					if (pendingFriends.isEmpty()) {
						return new Response<>(HttpStatus.OK.value(), "No pending friends found", pendingFriends);
					}

					List<Long> userIds = new ArrayList<>();
					for (Friends friendship : pendingFriends) {
						if (friendship.getUserID1() != userID) {
							userIds.add(friendship.getUserID1());
						}
						if (friendship.getUserID2() != userID) {
							userIds.add(friendship.getUserID2());
						}
					}

					List<User> users = userRepository.findAllById(userIds);

					return new Response<>(HttpStatus.OK.value(), "pending Friends retrieved successfully", users);
				} else {
					return new Response<>(HttpStatus.BAD_REQUEST.value(), "You have no access", null);
				}
			} else {
				return new Response<>(HttpStatus.BAD_REQUEST.value(), "User not found", null);
			}

		} catch (Exception e) {
			return new Response<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Internal server error", null);
		}
	}

	@Override
	public Response<List<?>> getAllFriends(Long userID) {
		try {
			Optional<CredentialMaster> master = userDetailsService.getUserDetails();
			if (master != null && master.isPresent()) {
				if (master.get().getUser().getId().equals(userID)) {
					List<Friends> confirmedFriends = friendshipRepository.findByUserID1OrUserID2AndStatus(userID,
							FriendshipEnum.Accepted.getId());

					if (confirmedFriends.isEmpty()) {
						return new Response<>(200, "No friends found", null);
					}

					List<Long> userIds = new ArrayList<>();
					for (Friends friendship : confirmedFriends) {
						if (friendship.getUserID1() != userID) {
							userIds.add(friendship.getUserID1());
						}
						if (friendship.getUserID2() != userID) {
							userIds.add(friendship.getUserID2());
						}
					}

					List<User> users = userRepository.findAllById(userIds);

					return new Response<>(HttpStatus.OK.value(), "Friends retrieved successfully", users);
				} else {
					return new Response<>(HttpStatus.BAD_REQUEST.value(), "You have no access", null);
				}
			} else {
				return new Response<>(HttpStatus.BAD_REQUEST.value(), "User not found", null);
			}

		} catch (Exception e) {
			return new Response<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Internal server error", null);
		}
	}

	@Override
	public Response<?> searchFriendsByName(Long userID, String name) {
		try {
			Optional<CredentialMaster> master = userDetailsService.getUserDetails();
			if (master != null && master.isPresent()) {
				if (master.get().getUser().getId().equals(userID)) {
					List<Friends> confirmedFriends = friendshipRepository.findByUserID1AndStatus(userID,
							FriendshipEnum.Accepted.getId());
					List<User> matchedFriends = new ArrayList<>();

					for (Friends friendship : confirmedFriends) {
						User friendUser = userRepository.findById(friendship.getUserID2()).orElse(null);
						if (friendUser != null && friendUser.getUsername().contains(name)) {
							matchedFriends.add(friendUser);
						}
					}

					return new Response<>(HttpStatus.OK.value(), "Friends retrieved successfully", matchedFriends);
				} else {
					return new Response<>(HttpStatus.BAD_REQUEST.value(), "You have no access", null);
				}
			} else {
				return new Response<>(HttpStatus.BAD_REQUEST.value(), "User not found", null);
			}

		} catch (Exception e) {
			return new Response<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Internal server error", null);
		}
	}

	@Override
	public Response<?> sendFriendStatus(Long userID1, Long userID2, Long status) {
		// TODO Auto-generated method stub
		return null;
	}

}
