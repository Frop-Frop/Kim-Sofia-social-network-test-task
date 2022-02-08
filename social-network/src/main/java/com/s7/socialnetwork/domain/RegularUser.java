package com.s7.socialnetwork.domain;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.MapsId;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;

import com.s7.socialnetwork.domain.security.Role;
import com.s7.socialnetwork.domain.security.Status;

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
public class RegularUser extends AbstractEntity {

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
	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.MERGE, mappedBy = "user")
	private Set<Post> posts = new HashSet<>();

	@ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
	@MapsId("user_id")
	@JoinTable(name = "friends", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "friend_id"))
	private Set<RegularUser> friends = new HashSet<>();

	@ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
	@MapsId("friend_id")
	@JoinTable(name = "friends", joinColumns = @JoinColumn(name = "friend_id"), inverseJoinColumns = @JoinColumn(name = "user_id"))
	private Set<RegularUser> friendOf = new HashSet<>();

	public RegularUser(Long id, String username, String password, String firstName, String lastName, Date birthday,
			Role role, Status status, Set<RegularUser> friends, Set<RegularUser> friendOf) {
		super(id);
		this.username = username;
		this.password = password;
		this.firstName = firstName;
		this.lastName = lastName;
		this.birthday = birthday;
		this.role = role;
		this.status = status;
		this.friends = friends;
		this.friendOf = friendOf;
	}

	public RegularUser(Long id, String username, String password, String firstName, String lastName, Date birthday,
			Role role, Status status) {
		super(id);
		this.username = username;
		this.password = password;
		this.firstName = firstName;
		this.lastName = lastName;
		this.birthday = birthday;
		this.role = role;
		this.status = status;
	}

	public boolean hasFriend(RegularUser friend) {
		return friends.contains(friend);
	}

	public boolean isFriendOf(RegularUser friend) {
		return friendOf.contains(friend);
	}

	public void addFriend(RegularUser friend) {
		friends.add(friend);
	}

	public void removeFriend(RegularUser friend) {
		friends.remove(friend);
		friend.friendOf.remove(this);
	}

}
