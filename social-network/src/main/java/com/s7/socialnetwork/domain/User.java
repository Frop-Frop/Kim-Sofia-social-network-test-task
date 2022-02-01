package com.s7.socialnetwork.domain;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.MapsId;
import javax.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;

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
	@DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
	private Date birthday;

	@ManyToMany(fetch = FetchType.LAZY)
	@MapsId("user_id")
	@JoinTable(name = "friends", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "friend_id"))
	private Set<User> friends = new HashSet<>();

	@ManyToMany(fetch = FetchType.LAZY)
	@MapsId("friend_id")
	@JoinTable(name = "friends", joinColumns = @JoinColumn(name = "friend_id"), inverseJoinColumns = @JoinColumn(name = "user_id"))
	private Set<User> friendOf = new HashSet<>();

	public User(Long id, String username, String password, String firstName, String lastName, Date birthday,
			Set<User> friends, Set<User> friendOf) {
		super(id);
		this.username = username;
		this.password = password;
		this.firstName = firstName;
		this.lastName = lastName;
		this.birthday = birthday;
		this.friends = friends;
		this.friendOf = friendOf;
	}

	public boolean hasFriend(User friend) {
		return friends.contains(friend);
	}

	public void addFriend(User friend) {
		friends.add(friend);
		friend.friendOf.add(this);
	}

	public void removeFriend(User friend) {
		friends.remove(friend);
		friend.friendOf.remove(this);
	}

}
