package com.s7.socialnetwork.services;

import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.s7.socialnetwork.domain.User;
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

	public UserDTO createNewCustomer(UserDTO userDTO) {
		User user = userMapper.userDTOtoUser(userDTO);
		User savedUser = userRepository.save(user);
		return userMapper.userToUserDTO(savedUser);
	}

	public UserDTO saveUserByDTO(Long id, UserDTO userDTO) {
		Optional<User> optionalUser = userRepository.findById(id);
		if (optionalUser.isEmpty()) {
			throw new ResourceNotFoundException();
		}
		User user = optionalUser.get();
		user = userMapper.userDTOtoUser(userDTO);
		user.setId(id);
		return userMapper.userToUserDTO(userRepository.save(user));
	}

	public void deleteById(Long id) {
		User user = userRepository.getById(id);
		userRepository.delete(user);
	}

}
