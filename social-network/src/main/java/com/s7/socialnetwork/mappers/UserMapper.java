package com.s7.socialnetwork.mappers;

import java.util.HashSet;
import java.util.Set;

import org.springframework.stereotype.Component;

import com.s7.socialnetwork.domain.RegularUser;
import com.s7.socialnetwork.models.UserDTO;

@Component
public class UserMapper {

	public UserDTO userToUserDTO(RegularUser user) {
		if (user == null) {
			return new UserDTO();
		}
		return new UserDTO(user.getId(), user.getUsername(), user.getPassword(), user.getFirstName(),
				user.getLastName(), user.getBirthday());
	}

	public RegularUser userDTOtoUser(UserDTO userDTO) {
		if (userDTO == null) {
			return new RegularUser();
		}
		Set<RegularUser> friends = new HashSet<>(); // add fetching from repository
		Set<RegularUser> friendOf = new HashSet<>(); // add fetching from repository
		RegularUser user = new RegularUser(userDTO.getId(), userDTO.getUsername(), userDTO.getPassword(), userDTO.getFirstName(),
				userDTO.getLastName(), userDTO.getBirthday(), friends, friendOf);
		return user;

	}

}
