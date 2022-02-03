package com.s7.socialnetwork.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.s7.socialnetwork.domain.RegularUser;
import com.s7.socialnetwork.domain.security.Role;
import com.s7.socialnetwork.domain.security.Status;
import com.s7.socialnetwork.mappers.UserMapper;
import com.s7.socialnetwork.models.UserDTO;
import com.s7.socialnetwork.models.UserListDTO;
import com.s7.socialnetwork.repositories.UserRepository;

@SpringBootTest(classes = { UserService.class, UserMapper.class, UserRepository.class })
class UserServiceTest {

	@MockBean
	private UserRepository userRepository;

	@MockBean
	private UserMapper userMapper;

	@Autowired
	private UserService userService;

	@Test
	void ShouldReturnUserListDTOofAllUsers() {
		RegularUser user = new RegularUser();
		user.setId(1L);
		user.setFirstName("John");
		RegularUser user1 = new RegularUser();
		user1.setId(2L);
		user1.setFirstName("Agatha");
		UserDTO userDTO = new UserDTO();
		userDTO.setId(1L);
		userDTO.setFirstName("John");
		UserDTO userDTO1 = new UserDTO();
		userDTO1.setId(2L);
		userDTO1.setFirstName("Agatha");
		UserListDTO expected = new UserListDTO(List.of(userDTO, userDTO1));
		when(userMapper.userToUserDTO(user)).thenReturn(userDTO);
		when(userMapper.userToUserDTO(user1)).thenReturn(userDTO1);
		when(userRepository.findAll()).thenReturn(List.of(user, user1));
		UserListDTO actual = userService.getAllUsers();
		verify(userRepository, times(1)).findAll();
		verify(userMapper, times(1)).userToUserDTO(user);
		verify(userMapper, times(1)).userToUserDTO(user1);
		assertEquals(expected, actual);
	}

	@Test
	void shouldReturnUserDTOById() {
		RegularUser user = new RegularUser();
		user.setId(1L);
		user.setFirstName("John");
		Optional<RegularUser> optionalUser = Optional.of(user);
		UserDTO userDTO = new UserDTO();
		userDTO.setId(1L);
		userDTO.setFirstName("John");
		when(userRepository.findById(1l)).thenReturn(optionalUser);
		when(userMapper.userToUserDTO(user)).thenReturn(userDTO);
		UserDTO actual = userService.getUserById(1L);
		verify(userRepository, times(1)).findById(1L);
		assertEquals(userDTO, actual);
	}

	@Test
	void shouldReturnRegularUserByUsername() {
		RegularUser user = new RegularUser();
		user.setId(1L);
		user.setFirstName("John");
		user.setUsername("Username");
		Optional<RegularUser> optionalUser = Optional.of(user);
		when(userRepository.findByUsername("Username")).thenReturn(optionalUser);
		RegularUser actual = userService.getRegularUserByUsername("Username");
		verify(userRepository, times(1)).findByUsername("Username");
		assertEquals(user, actual);
	}

	@Test
	void shouldAddFriend() {
		RegularUser user = new RegularUser();
		user.setId(1L);
		user.setFirstName("John");
		RegularUser user1 = new RegularUser();
		user1.setId(2L);
		user1.setFirstName("Agatha");
		Optional<RegularUser> optionalUser = Optional.of(user1);
		when(userRepository.findById(2l)).thenReturn(optionalUser);
		when(userRepository.save(user)).thenReturn(user);
		userService.addFriend(user, 2l);
		verify(userRepository, times(1)).findById(2l);
		verify(userRepository, times(1)).save(user);
		RegularUser userWithFriend = new RegularUser();
		userWithFriend.setId(1l);
		userWithFriend.setFirstName("John");
		userWithFriend.addFriend(user1);
		assertEquals(userWithFriend, user);
	}

	@Test
	void shouldRemoveFriend() {
		RegularUser user = new RegularUser();
		user.setId(1L);
		user.setFirstName("John");
		RegularUser user1 = new RegularUser();
		user1.setId(2L);
		user1.setFirstName("Agatha");
		user.addFriend(user1);
		Optional<RegularUser> optionalUser = Optional.of(user1);
		when(userRepository.findById(2l)).thenReturn(optionalUser);
		when(userRepository.save(user)).thenReturn(user);
		userService.removeFriend(user, 2l);
		verify(userRepository, times(1)).findById(2l);
		verify(userRepository, times(1)).save(user);
		RegularUser userWithoutFriend = new RegularUser();
		userWithoutFriend.setId(1l);
		userWithoutFriend.setFirstName("John");
		assertEquals(userWithoutFriend, user);
	}

	@Test
	void shouldReturnUserByMatchingName() {
		RegularUser user = new RegularUser();
		user.setId(1L);
		user.setFirstName("John");
		user.setLastName("Doe");
		UserDTO userDTO = new UserDTO();
		userDTO.setId(1l);
		userDTO.setFirstName("John");
		userDTO.setLastName("Doe");
		when(userRepository.search("John")).thenReturn(List.of(user));
		when(userMapper.userToUserDTO(user)).thenReturn(userDTO);
		UserListDTO actual = userService.getUsersMatchingName("John");
		verify(userRepository, times(1)).search("John");
		assertEquals(new UserListDTO(List.of(userDTO)), actual);
	}

	@Test
	void shouldReturnUserFriends() {
		RegularUser user = new RegularUser();
		user.setId(1L);
		user.setFirstName("John");
		RegularUser user1 = new RegularUser();
		user1.setId(2L);
		user1.setFirstName("Agatha");
		user.addFriend(user1);
		UserDTO userDTO1 = new UserDTO();
		userDTO1.setId(2L);
		userDTO1.setFirstName("Agatha");
		UserListDTO expected = new UserListDTO(List.of(userDTO1));
		when(userMapper.userToUserDTO(user1)).thenReturn(userDTO1);
		when(userRepository.findFriends(1l)).thenReturn(List.of(user1));
		UserListDTO actual = userService.getUserFriends(1l);
		verify(userRepository, times(1)).findFriends(1l);
		verify(userMapper, times(1)).userToUserDTO(user1);
		assertEquals(expected, actual);
	}

	@Test
	void shouldReturnUserFriendOf() {
		RegularUser user = new RegularUser();
		user.setId(1L);
		user.setFirstName("John");
		RegularUser user1 = new RegularUser();
		user1.setId(2L);
		user1.setFirstName("Agatha");
		UserDTO userDTO1 = new UserDTO();
		userDTO1.setId(2L);
		userDTO1.setFirstName("Agatha");
		UserListDTO expected = new UserListDTO(List.of(userDTO1));
		when(userMapper.userToUserDTO(user1)).thenReturn(userDTO1);
		when(userRepository.findFriendOf(1l)).thenReturn(List.of(user1));
		UserListDTO actual = userService.getUserFriendOf(1l);
		verify(userRepository, times(1)).findFriendOf(1l);
		verify(userMapper, times(1)).userToUserDTO(user1);
		assertEquals(expected, actual);
	}

	@Test
	void shouldCreateNewUser() {
		RegularUser user = new RegularUser();
		user.setFirstName("John");
		RegularUser userWithId = new RegularUser();
		userWithId.setId(1l);
		userWithId.setFirstName("John");
		UserDTO userDTO = new UserDTO();
		userDTO.setFirstName("John");
		UserDTO userDTOwithId = new UserDTO();
		userDTOwithId.setId(1l);
		userDTOwithId.setFirstName("John");
		when(userMapper.userDTOtoUser(userDTO)).thenReturn(user);
		when(userRepository.save(user)).thenReturn(userWithId);
		when(userMapper.userToUserDTO(userWithId)).thenReturn(userDTOwithId);
		UserDTO actual = userService.createNewUser(userDTO);
		verify(userMapper, times(1)).userDTOtoUser(userDTO);
		verify(userRepository, times(1)).save(user);
		verify(userMapper, times(1)).userToUserDTO(userWithId);

		assertEquals(userDTOwithId, actual);
	}

	@Test
	void shouldSaveUserByDTO() {
		RegularUser user = new RegularUser();
		user.setFirstName("John");
		RegularUser userWithId = new RegularUser();
		userWithId.setId(1l);
		userWithId.setFirstName("John");
		Optional<RegularUser> optionalUser = Optional.of(user);
		UserDTO userDTO = new UserDTO();
		userDTO.setFirstName("John");
		UserDTO userDTOwithId = new UserDTO();
		userDTOwithId.setId(1l);
		userDTOwithId.setFirstName("John");
		when(userRepository.findById(1l)).thenReturn(optionalUser);
		when(userMapper.userDTOtoUser(userDTO)).thenReturn(user);
		when(userRepository.save(user)).thenReturn(userWithId);
		when(userMapper.userToUserDTO(userWithId)).thenReturn(userDTOwithId);
		userService.saveUserByDTO(1L, userDTO);
		verify(userRepository, times(1)).findById(1l);
		verify(userMapper, times(1)).userDTOtoUser(userDTO);
		verify(userRepository, times(1)).save(user);
		verify(userMapper, times(1)).userToUserDTO(userWithId);
	}

	@Test
	void shouldPatchUser() {
		RegularUser user = new RegularUser();
		user.setId(1l);
		RegularUser patchedUser = new RegularUser(1l, "username", "password", "John", "Doe", new Date(),
				Role.REGULAR_USER, Status.ACTIVE);
		UserDTO userDTO = new UserDTO(null, "username", "password", "John", "Doe", new Date(), Role.REGULAR_USER,
				Status.ACTIVE);
		when(userRepository.getById(1l)).thenReturn(user);
		when(userMapper.userToUserDTO(patchedUser)).thenReturn(userDTO);
		when(userRepository.save(user)).thenReturn(patchedUser);
		userService.patchUser(1l, userDTO);
		verify(userRepository, times(1)).getById(1l);
		verify(userMapper, times(1)).userToUserDTO(patchedUser);
		verify(userRepository, times(1)).save(user);
	}

}
