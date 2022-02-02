package com.s7.socialnetwork.controllers;

import java.security.Principal;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.s7.socialnetwork.domain.RegularUser;
import com.s7.socialnetwork.models.UserDTO;
import com.s7.socialnetwork.models.UserListDTO;
import com.s7.socialnetwork.services.UserService;

@RestController
@RequestMapping("social-network/")
public class UserController {

	private final UserService userService;

	public UserController(UserService userService) {
		this.userService = userService;
	}

	@GetMapping
	@PreAuthorize("hasAuthority('act')")
	public ResponseEntity<UserListDTO> getAllUsers() {
		return new ResponseEntity<UserListDTO>(userService.getAllUsers(), HttpStatus.OK);
	}

	@GetMapping("search/{searchTearm}")
	@PreAuthorize("hasAuthority('act')")
	public ResponseEntity<UserListDTO> getUsersMatchingName(@PathVariable String searchTearm) {
		return new ResponseEntity<UserListDTO>(userService.getUsersMatchingName(searchTearm), HttpStatus.OK);
	}

	@GetMapping("{id}/friends")
	@PreAuthorize("hasAuthority('act')")
	public ResponseEntity<UserListDTO> getUserFriends(@PathVariable Long id) {
		return new ResponseEntity<UserListDTO>(userService.getUserFriends(id), HttpStatus.OK);
	}

	@GetMapping("{id}/friend-of")
	@PreAuthorize("hasAuthority('act')")
	public ResponseEntity<UserListDTO> getUserFriendOf(@PathVariable Long id) {
		return new ResponseEntity<UserListDTO>(userService.getUserFriendOf(id), HttpStatus.OK);
	}

	@GetMapping("friends/add/{id}")
	@PreAuthorize("hasAuthority('act')")
	public ResponseEntity<Void> addFriend(@PathVariable Long id, Principal principal) {
		RegularUser loggedInUser = userService.getRegularUserByUsername(principal.getName());
		userService.addFriend(loggedInUser, id);
		return new ResponseEntity<Void>(HttpStatus.OK);
	}

	@GetMapping("friends/remove/{id}")
	@PreAuthorize("hasAuthority('act')")
	public ResponseEntity<Void> removeFriend(@PathVariable Long id, Principal principal) {
		RegularUser loggedInUser = userService.getRegularUserByUsername(principal.getName());
		userService.addFriend(loggedInUser, id);
		return new ResponseEntity<Void>(HttpStatus.OK);
	}

	@GetMapping("{id}")
	@PreAuthorize("hasAuthority('act')")
	public ResponseEntity<UserDTO> getUserById(@PathVariable Long id) {
		return new ResponseEntity<UserDTO>(userService.getUserById(id), HttpStatus.OK);
	}

	@PutMapping("{id}")
	@PreAuthorize("hasAuthority('act')")
	public ResponseEntity<UserDTO> updateUser(@RequestBody UserDTO userDTO, @PathVariable Long id) {
		return new ResponseEntity<UserDTO>(userService.saveUserByDTO(id, userDTO), HttpStatus.OK);
	}

	@PatchMapping("{id}")
	@PreAuthorize("hasAuthority('act')")
	public ResponseEntity<UserDTO> patchCustomer(@RequestBody UserDTO userDTO, @PathVariable Long id) {
		return new ResponseEntity<UserDTO>(userService.patchUser(id, userDTO), HttpStatus.OK);
	}

	@PostMapping("create-user")
	public ResponseEntity<UserDTO> createNewUser(@RequestBody UserDTO userDTO) {
		return new ResponseEntity<UserDTO>(userService.createNewUser(userDTO), HttpStatus.OK);
	}

	@DeleteMapping("{id}")
	@PreAuthorize("hasAuthority('act')")
	public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
		userService.deleteById(id);
		return new ResponseEntity<Void>(HttpStatus.OK);
	}

}
