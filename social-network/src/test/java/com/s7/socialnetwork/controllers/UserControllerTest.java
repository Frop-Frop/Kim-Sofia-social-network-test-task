package com.s7.socialnetwork.controllers;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.s7.socialnetwork.domain.security.Role;
import com.s7.socialnetwork.domain.security.Status;
import com.s7.socialnetwork.mappers.UserMapper;
import com.s7.socialnetwork.models.UserDTO;
import com.s7.socialnetwork.models.UserListDTO;
import com.s7.socialnetwork.repositories.UserRepository;
import com.s7.socialnetwork.services.ResourceNotFoundException;
import com.s7.socialnetwork.services.UserService;

@WebMvcTest(UserController.class)
class UserControllerTest {

	@MockBean
	UserService userService;

	@MockBean
	UserMapper userMapper;

	@MockBean
	UserRepository userRepository;

	@Autowired
	UserController userController;

	@Autowired
	WebApplicationContext webApplicationContext;

	@MockBean(name = "userDetailsServiceImpl")
	UserDetailsService userDetailsService;

	@Autowired
	MockMvc mockMvc;

	ObjectMapper objectMapper = new ObjectMapper();

	@WithMockUser(username = "Marylin_Bett", authorities = "act")
	@Test
	void getAllUsersTest() throws Exception {
		UserDTO userDTO = new UserDTO(1l, "username", "password", "John", "Doe", new Date(), Role.REGULAR_USER,
				Status.ACTIVE);
		UserDTO userDTO1 = new UserDTO(1l, "username1", "password1", "John1", "Doe1", new Date(), Role.REGULAR_USER,
				Status.ACTIVE);
		when(userService.getAllUsers()).thenReturn(new UserListDTO(List.of(userDTO, userDTO1)));
		mockMvc.perform(get("/social-network/").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
	}

	@Test
	void getAllUsersTestShouldThrowAccessForbidden() throws Exception {
		UserDTO userDTO = new UserDTO(1l, "username", "password", "John", "Doe", new Date(), Role.REGULAR_USER,
				Status.ACTIVE);
		UserDTO userDTO1 = new UserDTO(2l, "username1", "password1", "John1", "Doe1", new Date(), Role.REGULAR_USER,
				Status.ACTIVE);
		when(userService.getAllUsers()).thenReturn(new UserListDTO(List.of(userDTO, userDTO1)));
		mockMvc.perform(get("/social-network/").contentType(MediaType.APPLICATION_JSON)).andExpect(status().is(401));
	}

	@WithMockUser(username = "Marylin_Bett", authorities = "act")
	@Test
	void getUsersMatchingNameTest() throws Exception {
		UserDTO userDTO = new UserDTO(1l, "username", "password", "John", "Doe", new Date(), Role.REGULAR_USER,
				Status.ACTIVE);
		when(userService.getUsersMatchingName("Bett")).thenReturn(new UserListDTO(List.of(userDTO)));
		mockMvc.perform(get("/social-network/search/Bett").contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());
	}

	void getUserFriendsTest() throws Exception {
		UserDTO userDTO = new UserDTO(1l, "username", "password", "John", "Doe", new Date(), Role.REGULAR_USER,
				Status.ACTIVE);
		UserDTO userDTO1 = new UserDTO(2l, "username1", "password1", "John1", "Doe1", new Date(), Role.REGULAR_USER,
				Status.ACTIVE);
		when(userService.getUserFriends(3L)).thenReturn(new UserListDTO(List.of(userDTO, userDTO1)));
		mockMvc.perform(get("/social-network/3/friends").contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());

	}

	void getUserFriendOfTest() throws Exception {
		UserDTO userDTO = new UserDTO(1l, "username", "password", "John", "Doe", new Date(), Role.REGULAR_USER,
				Status.ACTIVE);
		UserDTO userDTO1 = new UserDTO(2l, "username1", "password1", "John1", "Doe1", new Date(), Role.REGULAR_USER,
				Status.ACTIVE);
		when(userService.getUserFriendOf(3L)).thenReturn(new UserListDTO(List.of(userDTO, userDTO1)));
		mockMvc.perform(get("/social-network/3/friend-of").contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());

	}

	void addFriendTest() throws Exception {

	}

	void removeFriendTest() {

	}

	@WithMockUser(username = "Marylin_Bett", authorities = "act")
	@Test
	void getUserByIdTest() throws Exception {
		UserDTO userDTO = new UserDTO(1l, "username", "password", "John", "Doe", new Date(), Role.REGULAR_USER,
				Status.ACTIVE);
		when(userService.getUserById(1L)).thenReturn(userDTO);
		mockMvc.perform(get("/social-network/1").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
	}

	@WithMockUser(username = "Marylin_Bett", authorities = "act")
	@Test
	void updateUserTest() throws Exception {
		UserDTO userDTO = new UserDTO(null, "username", "password", "John", "Doe", new Date(), Role.REGULAR_USER,
				Status.ACTIVE);
		UserDTO savedDTO = new UserDTO(1l, "username", "password", "John", "Doe", new Date(), Role.REGULAR_USER,
				Status.ACTIVE);
		String content = objectMapper.writeValueAsString(savedDTO);
		when(userService.saveUserByDTO(1l, userDTO)).thenReturn(savedDTO);
		mockMvc.perform(put("/social-network/1").contentType(MediaType.APPLICATION_JSON).content(content))
				.andExpect(status().isOk());
	}

	@WithMockUser(username = "Marylin_Bett", authorities = "act")
	@Test
	void patchCustomerTest() throws Exception {
		UserDTO userDTO = new UserDTO(null, "username", "password", "John", "Doe", new Date(), Role.REGULAR_USER,
				Status.ACTIVE);
		UserDTO savedDTO = new UserDTO(1l, "username", "password", "John", "Doe", new Date(), Role.REGULAR_USER,
				Status.ACTIVE);
		String content = objectMapper.writeValueAsString(savedDTO);
		when(userService.patchUser(1l, userDTO)).thenReturn(savedDTO);
		mockMvc.perform(patch("/social-network/1").contentType(MediaType.APPLICATION_JSON).content(content))
				.andExpect(status().isOk());
	}

	@Test
	void createNewUserTest() throws Exception {
		UserDTO userDTO = new UserDTO(null, "username", "password", "John", "Doe", new Date(), Role.REGULAR_USER,
				Status.ACTIVE);
		UserDTO savedDTO = new UserDTO(1l, "username", "password", "John", "Doe", new Date(), Role.REGULAR_USER,
				Status.ACTIVE);
		String content = objectMapper.writeValueAsString(savedDTO);
		when(userService.createNewUser(userDTO)).thenReturn(savedDTO);
		mockMvc.perform(post("/social-network/create-user").contentType(MediaType.APPLICATION_JSON).content(content))
				.andExpect(status().isOk());
	}

	@WithMockUser(username = "Marylin_Bett", authorities = "act")
	@Test
	void shouldThrowResourceNotFoundExceptionTest() throws Exception {
		when(userService.getUserById(1l)).thenThrow(ResourceNotFoundException.class);
		mockMvc.perform(get("/social-network/1")).andExpect(status().is(404));
	}

}
