package com.s7.socialnetwork.models;

import java.util.Date;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import org.springframework.format.annotation.DateTimeFormat;

import com.s7.socialnetwork.domain.security.Role;
import com.s7.socialnetwork.domain.security.Status;

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
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date birthday;
	@Enumerated(value = EnumType.STRING)
	private Role role;
	@Enumerated(value = EnumType.STRING)
	private Status status;

}
