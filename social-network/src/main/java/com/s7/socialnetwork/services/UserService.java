package com.s7.socialnetwork.services;

import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.s7.socialnetwork.domain.RegularUser;
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

	public UserListDTO getUserFriends(Long friendId) {
		return new UserListDTO(userRepository.findFriends(friendId).stream().map(userMapper::userToUserDTO)
				.collect(Collectors.toList()));
	}

	public UserListDTO getUserFriendOf(Long friendId) {
		return new UserListDTO(userRepository.findFriendOf(friendId).stream().map(userMapper::userToUserDTO)
				.collect(Collectors.toList()));
	}

	public UserDTO getUserById(Long id) {
		return userRepository.findById(id).map(userMapper::userToUserDTO).orElseThrow(ResourceNotFoundException::new);
	}

	public UserDTO createNewUser(UserDTO userDTO) {
		RegularUser user = userMapper.userDTOtoUser(userDTO);
		RegularUser savedUser = userRepository.save(user);
		return userMapper.userToUserDTO(savedUser);
	}

	public UserDTO saveUserByDTO(Long id, UserDTO userDTO) {
		Optional<RegularUser> optionalUser = userRepository.findById(id);
		if (optionalUser.isEmpty()) {
			throw new ResourceNotFoundException();
		}
		RegularUser user = optionalUser.get();
		user = userMapper.userDTOtoUser(userDTO);
		user.setId(id);
		return userMapper.userToUserDTO(userRepository.save(user));
	}

	public UserDTO patchUser(Long id, UserDTO userDTO) {
		RegularUser user = userRepository.getById(id);
		if (user == null) {
			throw new ResourceNotFoundException();
		}
		if (user.getUsername() == null) {
			user.setUsername(userDTO.getUsername());
		}
		if (user.getFirstName() == null) {
			user.setFirstName(userDTO.getFirstName());
		}
		if (user.getLastName() == null) {
			user.setLastName(userDTO.getLastName());
		}
		if (user.getPassword() == null) {
			user.setPassword(userDTO.getPassword());
		}
		if (user.getBirthday() == null) {
			user.setBirthday(userDTO.getBirthday());
		}
		return userMapper.userToUserDTO(userRepository.save(user));
	}

	public void deleteById(Long id) {
		RegularUser user = userRepository.getById(id);
		userRepository.delete(user);
	}

}
