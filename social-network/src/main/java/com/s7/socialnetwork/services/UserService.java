package com.s7.socialnetwork.services;

import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.s7.socialnetwork.mappers.UserMapper;
import com.s7.socialnetwork.models.UserDTO;
import com.s7.socialnetwork.models.UserListDTO;
import com.s7.socialnetwork.repositories.UserRepository;

@Service
public class UserService {

	private final UserRepository userRepository;
	private final UserMapper userMapper;

	public UserService(UserRepository userRepository, UserMapper userMapper) {
		this.userRepository = userRepository;
		this.userMapper = userMapper;
	}

	public UserListDTO getAllUsers() {
		return new UserListDTO(
				userRepository.findAll().stream().map(userMapper::userToUserDTO).collect(Collectors.toList()));
	}

	public UserDTO getUserById(Long id) {
		return userRepository.findById(id).map(userMapper::userToUserDTO).orElseThrow(ResourceNotFoundException::new);
	}

}
