package com.s7.socialnetwork.mappers;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.s7.socialnetwork.domain.RegularUser;
import com.s7.socialnetwork.models.UserDTO;
import com.s7.socialnetwork.repositories.UserRepository;

@Component
public class UserMapper {

	private final PasswordEncoder passwordEncoder;
	private final UserRepository userRepository;

	public UserMapper(PasswordEncoder passwordEncoder, UserRepository userRepository) {
		this.passwordEncoder = passwordEncoder;
		this.userRepository = userRepository;
	}

	public UserDTO userToUserDTO(RegularUser user) {
		if (user == null) {
			return new UserDTO();
		}
		return new UserDTO(user.getId(), user.getUsername(), user.getPassword(), user.getFirstName(),
				user.getLastName(), user.getBirthday(), user.getRole(), user.getStatus());
	}

	public RegularUser userDTOtoUser(UserDTO userDTO) {
		if (userDTO == null) {
			return new RegularUser();
		}
		RegularUser user = new RegularUser(userDTO.getId(), userDTO.getUsername(),
				passwordEncoder.encode(userDTO.getPassword()), userDTO.getFirstName(), userDTO.getLastName(),
				userDTO.getBirthday(), userDTO.getRole(), userDTO.getStatus());
		return user;

	}

}
