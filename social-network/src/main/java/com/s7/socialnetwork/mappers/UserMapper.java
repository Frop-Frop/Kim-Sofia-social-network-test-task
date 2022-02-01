package com.s7.socialnetwork.mappers;

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
		User user = new User(userDTO.getId(), userDTO.getUsername(), userDTO.getPassword(), userDTO.getFirstName(),
				userDTO.getLastName(), userDTO.getBirthday());
		return user;

	}

}
