package com.s7.socialnetwork.domain;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
public class User extends AbstractEntity {

	private String username;
	private String password;
	private String firstName;
	private String lastName;
	private Date birthday;

	public User(Long id, String username, String password, String firstName, String lastName, Date birthday) {
		super(id);
		this.username = username;
		this.password = password;
		this.firstName = firstName;
		this.lastName = lastName;
		this.birthday = birthday;
	}

}
