package com.s7.socialnetwork.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
	public ResponseEntity<UserListDTO> getAllUsers() {
		return new ResponseEntity<UserListDTO>(userService.getAllUsers(), HttpStatus.OK);
	}

	@GetMapping("{id}/friends")
	public ResponseEntity<UserListDTO> getUserFriends(@PathVariable Long id) {
		return new ResponseEntity<UserListDTO>(userService.getUserFriends(id), HttpStatus.OK);
	}

	@GetMapping("{id}/friend-of")
	public ResponseEntity<UserListDTO> getUserFriendOf(@PathVariable Long id) {
		return new ResponseEntity<UserListDTO>(userService.getUserFriendOf(id), HttpStatus.OK);
	}

	@GetMapping("{id}")
	public ResponseEntity<UserDTO> getUserById(@PathVariable Long id) {
		return new ResponseEntity<UserDTO>(userService.getUserById(id), HttpStatus.OK);
	}

	@PostMapping
	public ResponseEntity<UserDTO> createNewUser(@RequestBody UserDTO userDTO) {
		return new ResponseEntity<UserDTO>(userService.createNewUser(userDTO), HttpStatus.OK);
	}

	@PutMapping("{id}")
	public ResponseEntity<UserDTO> updateUser(@RequestBody UserDTO userDTO, @PathVariable Long id) {
		return new ResponseEntity<UserDTO>(userService.saveUserByDTO(id, userDTO), HttpStatus.OK);
	}

	@PatchMapping("{id}")
	public ResponseEntity<UserDTO> patchCustomer(@RequestBody UserDTO userDTO, @PathVariable Long id) {
		return new ResponseEntity<UserDTO>(userService.patchUser(id, userDTO), HttpStatus.OK);
	}

	@DeleteMapping("{id}")
	public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
		userService.deleteById(id);
		return new ResponseEntity<Void>(HttpStatus.OK);
	}

}
