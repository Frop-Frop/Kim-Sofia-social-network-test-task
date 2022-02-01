package com.s7.socialnetwork.models;

import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserListDTO {
	private List<UserDTO> users;

	public UserListDTO(List<UserDTO> userList) {
		this.users = userList;
	}

}
