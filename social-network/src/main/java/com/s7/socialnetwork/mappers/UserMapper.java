package com.s7.socialnetwork.mappers;

import java.util.HashSet;
import java.util.Set;

import org.springframework.stereotype.Component;

import com.s7.socialnetwork.domain.User;
import com.s7.socialnetwork.models.UserDTO;

@Component
public class UserMapper {

	public UserDTO userToUserDTO(User user) {
		if (user == null) {
			return new UserDTO();
		}
		return new UserDTO(user.getId(), user.getUsername(), user.getPassword(), user.getFirstName(),
				user.getLastName(), user.getBirthday());
	}

	public User userDTOtoUser(UserDTO userDTO) {
		if (userDTO == null) {
			return new User();
		}
		Set<User> friends = new HashSet<>(); // add fetching from repository
		Set<User> friendOf = new HashSet<>(); // add fetching from repository
		User user = new User(userDTO.getId(), userDTO.getUsername(), userDTO.getPassword(), userDTO.getFirstName(),
				userDTO.getLastName(), userDTO.getBirthday(), friends, friendOf);
		return user;

	}

}
