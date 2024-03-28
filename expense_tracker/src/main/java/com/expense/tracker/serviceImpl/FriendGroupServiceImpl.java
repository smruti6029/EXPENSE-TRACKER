package com.expense.tracker.serviceImpl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.expense.tracker.dto.GroupDTO;
import com.expense.tracker.dto.Response;
import com.expense.tracker.enums.FriendshipEnum;
import com.expense.tracker.model.CredentialMaster;
import com.expense.tracker.model.Friends;
import com.expense.tracker.model.Groups;
import com.expense.tracker.model.User;
import com.expense.tracker.repository.FriendsGroupRepository;
import com.expense.tracker.repository.FriendsRepository;
import com.expense.tracker.repository.UserRepository;
import com.expense.tracker.security.JwtUserDetailsService;
import com.expense.tracker.service.FriendGroupService;
import com.expense.tracker.service.FriendshipService;

@Service
public class FriendGroupServiceImpl implements FriendGroupService {

	@Autowired
	private FriendsGroupRepository groupRepository;

	@Autowired
	private FriendsRepository friendshipRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private JwtUserDetailsService userDetailsService;

	@Override
	public Response<?> createGroupAndAddFriends(String groupName, Integer createdByUserId, List<Integer> friendIds) {

		try {
			Optional<CredentialMaster> master = userDetailsService.getUserDetails();
			if (master != null && master.isPresent()) {
				if (master.get().getUser().getId().equals(Long.getLong(createdByUserId.toString()))) {
					List<Groups> existingGroupsCreatedByUser = groupRepository
							.findByFriendsListsContaining(createdByUserId.toString());

					List<Friends> confirmedFriends = friendshipRepository
							.findByUserID1OrUserID2AndStatus(createdByUserId, FriendshipEnum.Accepted.getId());

					Set<Integer> confirmedFriendIds = new HashSet<>();
					for (Friends friendship : confirmedFriends) {
						confirmedFriendIds.add((int) friendship.getUserID1());
						confirmedFriendIds.add((int) friendship.getUserID2());
					}

					boolean allFriendsConfirmed = confirmedFriendIds.containsAll(friendIds);

					if (!allFriendsConfirmed) {
						return new Response<>(HttpStatus.BAD_REQUEST.value(),
								"Some friend IDs are not confirmed friends", null);
					}

					Groups groupToUpdateOrCreate = null;
					for (Groups group : existingGroupsCreatedByUser) {
						if (!group.getGroupName().equals(groupName) || group.getGroupCreatedByid() == createdByUserId) {
							groupToUpdateOrCreate = group;
							break;
						}
					}

					if (groupToUpdateOrCreate != null) {
						groupToUpdateOrCreate.setGroupName(groupName);
						groupToUpdateOrCreate.setFriendsLists(
								friendIds.stream().map(String::valueOf).collect(Collectors.joining(",")));
						groupToUpdateOrCreate.setUpdatedOn(new Date());

						Groups updatedGroup = groupRepository.save(groupToUpdateOrCreate);

						return new Response<>(HttpStatus.OK.value(),
								"Group updated and current user added successfully", updatedGroup);
					} else {
						Groups newGroup = new Groups();
						newGroup.setGroupName(groupName);
						newGroup.setGroupCreatedByid(createdByUserId);
						newGroup.setFriendsLists(
								friendIds.stream().map(String::valueOf).collect(Collectors.joining(",")));
						newGroup.setCreatedOn(new Date());
						newGroup.setIsActive(true);
						newGroup.setUpdatedOn(new Date());

						Groups savedGroup = groupRepository.save(newGroup);

						return new Response<>(HttpStatus.OK.value(),
								"Group created and current user added successfully", savedGroup);
					}
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
	public Response<?> getAllGroups(Integer createdByUserId) {
		try {
			Optional<CredentialMaster> master = userDetailsService.getUserDetails();
			if (master != null && master.isPresent()) {
				if (master.get().getUser().getId().equals(Long.getLong(createdByUserId.toString()))) {
					List<Groups> groups = groupRepository.findBygroupCreatedByid(createdByUserId);
					List<GroupDTO> groupDTOs = new ArrayList<>();
					for (Groups group : groups) {
						GroupDTO groupDTO = convertToDTO(group);
						groupDTOs.add(groupDTO);
					}
					return new Response<>(HttpStatus.OK.value(), "Groups retrieved successfully", groupDTOs);
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
	public Response<?> getUserGroups(Long userID) {
		try {
			Optional<CredentialMaster> master = userDetailsService.getUserDetails();
			if (master != null && master.isPresent()) {
				if (master.get().getUser().getId().equals(userID)) {
					List<Groups> groups = groupRepository.findByFriendsListsContaining(userID.toString());
					List<GroupDTO> groupDTOs = new ArrayList<>();
					for (Groups group : groups) {
						GroupDTO groupDTO = convertToDTO(group);
						groupDTOs.add(groupDTO);
					}
					return new Response<>(HttpStatus.OK.value(), "Groups retrieved successfully", groupDTOs);
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

	public GroupDTO convertToDTO(Groups group) {
		GroupDTO groupDTO = new GroupDTO();

		groupDTO.setId(group.getId());
		groupDTO.setGroupName(group.getGroupName());
		groupDTO.setGroupCreatedBy(group.getGroupCreatedByid());
		List<Long> friendsList = convertStringToList(group.getFriendsLists());
		groupDTO.setFriendsList(friendsList);

		List<User> userList = userRepository.findAllById(friendsList);
		groupDTO.setUserList(userList);

		return groupDTO;
	}

	public List<Long> convertStringToList(String friendsLists) {
		List<Long> friendsList = new ArrayList<>();
		if (friendsLists != null && !friendsLists.isEmpty()) {
			String[] friendIdsArray = friendsLists.split(",");
			for (String friendId : friendIdsArray) {
				friendsList.add((long) Integer.parseInt(friendId.trim()));
			}
		}
		return friendsList;
	}

}
