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

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "User controller", description = "User related operations")
@RestController
@RequestMapping("social-network/users/")
public class UserController {

	private final UserService userService;

	public UserController(UserService userService) {
		this.userService = userService;
	}

	@Operation(summary = "Get list of all users", security = @SecurityRequirement(name = "basicAuth"))
	@GetMapping
	@PreAuthorize("hasAuthority('act')")
	public ResponseEntity<UserListDTO> getAllUsers() {
		return new ResponseEntity<UserListDTO>(userService.getAllUsers(), HttpStatus.OK);
	}

	@Operation(summary = "Find users with name matching searchTerm", security = @SecurityRequirement(name = "basicAuth"))
	@GetMapping("search/{searchTearm}")
	@PreAuthorize("hasAuthority('act')")
	public ResponseEntity<UserListDTO> getUsersMatchingName(@PathVariable String searchTearm) {
		return new ResponseEntity<UserListDTO>(userService.getUsersMatchingName(searchTearm), HttpStatus.OK);
	}

	@Operation(summary = "Get list of user's friends", security = @SecurityRequirement(name = "basicAuth"))
	@GetMapping("friends/{id}")
	@PreAuthorize("hasAuthority('act')")
	public ResponseEntity<UserListDTO> getUserFriends(@PathVariable Long id) {
		return new ResponseEntity<UserListDTO>(userService.getUserFriends(id), HttpStatus.OK);
	}

	@Operation(summary = "Get list of users who are friends od user", security = @SecurityRequirement(name = "basicAuth"))
	@GetMapping("friends/friend-of/{id}")
	@PreAuthorize("hasAuthority('act')")
	public ResponseEntity<UserListDTO> getUserFriendOf(@PathVariable Long id) {
		return new ResponseEntity<UserListDTO>(userService.getUserFriendOf(id), HttpStatus.OK);
	}

	@Operation(summary = "Adds user in friends of current logged user", security = @SecurityRequirement(name = "basicAuth"))
	@PutMapping("friends/add/{id}")
	@PreAuthorize("hasAuthority('act')")
	public ResponseEntity<UserDTO> addFriend(@PathVariable Long id, Principal principal) {
		RegularUser loggedInUser = userService.getRegularUserByUsername(principal.getName());
		UserDTO userDTO = userService.addFriend(loggedInUser, id);
		return new ResponseEntity<UserDTO>(userDTO, HttpStatus.OK);
	}

	@Operation(summary = "Gemoves user from friends of current logged user", security = @SecurityRequirement(name = "basicAuth"))
	@PutMapping("friends/remove/{id}")
	@PreAuthorize("hasAuthority('act')")
	public ResponseEntity<UserDTO> removeFriend(@PathVariable Long id, Principal principal) {
		RegularUser loggedInUser = userService.getRegularUserByUsername(principal.getName());
		UserDTO userDTO = userService.removeFriend(loggedInUser, id);
		return new ResponseEntity<UserDTO>(userDTO, HttpStatus.OK);
	}

	@Operation(summary = "Get user by id", security = @SecurityRequirement(name = "basicAuth"))
	@GetMapping("{id}")
	@PreAuthorize("hasAuthority('act')")
	public ResponseEntity<UserDTO> getUserById(@PathVariable Long id) {
		return new ResponseEntity<UserDTO>(userService.getUserById(id), HttpStatus.OK);
	}

	@Operation(summary = "Update user by userDTO", security = @SecurityRequirement(name = "basicAuth"))
	@PutMapping("{id}")
	@PreAuthorize("hasAuthority('act')")
	public ResponseEntity<UserDTO> updateUser(@RequestBody UserDTO userDTO, @PathVariable Long id) {
		return new ResponseEntity<UserDTO>(userService.saveUserByDTO(id, userDTO), HttpStatus.OK);
	}

	@Operation(summary = "Patch user by userDTO", security = @SecurityRequirement(name = "basicAuth"))
	@PatchMapping("{id}")
	@PreAuthorize("hasAuthority('act')")
	public ResponseEntity<UserDTO> patchUser(@RequestBody UserDTO userDTO, @PathVariable Long id) {
		return new ResponseEntity<UserDTO>(userService.patchUser(id, userDTO), HttpStatus.OK);
	}

	@Operation(summary = "Create new user by userDTO")
	@PostMapping()
	public ResponseEntity<UserDTO> createNewUser(@RequestBody UserDTO userDTO) {
		return new ResponseEntity<UserDTO>(userService.createNewUser(userDTO), HttpStatus.OK);
	}

	@Operation(summary = "Delete user by id", security = @SecurityRequirement(name = "basicAuth"))
	@DeleteMapping("{id}")
	@PreAuthorize("hasAuthority('act')")
	public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
		userService.deleteById(id);
		return new ResponseEntity<Void>(HttpStatus.OK);
	}

}
