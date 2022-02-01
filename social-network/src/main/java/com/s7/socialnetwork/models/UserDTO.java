package com.s7.socialnetwork.models;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {

	private Long id;
	private String username;
	private String password;
	private String firstName;
	private String lastName;
	private Date birthday;

}
