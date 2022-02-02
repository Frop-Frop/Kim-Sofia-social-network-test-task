package com.s7.socialnetwork.repositories;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.s7.socialnetwork.domain.RegularUser;
import com.s7.socialnetwork.domain.security.Role;
import com.s7.socialnetwork.domain.security.Status;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class UserRepositoryTest {

	@Autowired
	private UserRepository userRepository;

	@Test
	void shouldFindOneUserByFirstName() {
		RegularUser user = new RegularUser(null, "username", "password", "firstname", "lastname", new Date(),
				Role.REGULAR_USER, Status.ACTIVE);
		List<RegularUser> expected = List.of(userRepository.save(user));
		List<RegularUser> actual = userRepository.search("firstname");
		assertEquals(expected, actual);
	}

	@Test
	void shouldFindOneUserByLastName() {
		RegularUser user = new RegularUser(null, "username", "password", "firstname", "lastname", new Date(),
				Role.REGULAR_USER, Status.ACTIVE);
		List<RegularUser> expected = List.of(userRepository.save(user));
		List<RegularUser> actual = userRepository.search("lastname");
		assertEquals(expected, actual);
	}

	@Test
	void shouldFindOneUserByFirstnameLastname() {
		RegularUser user = new RegularUser(null, "username", "password", "firstname", "lastname", new Date(),
				Role.REGULAR_USER, Status.ACTIVE);
		List<RegularUser> expected = List.of(userRepository.save(user));
		List<RegularUser> actual = userRepository.search("firstname lastname");
		assertEquals(expected, actual);
	}

	@Test
	void shouldFindOneUserByLastnameFirstname() {
		RegularUser user = new RegularUser(null, "username", "password", "firstname", "lastname", new Date(),
				Role.REGULAR_USER, Status.ACTIVE);
		List<RegularUser> expected = List.of(userRepository.save(user));
		List<RegularUser> actual = userRepository.search("lastname firstname");
		assertEquals(expected, actual);
	}

	@Test
	void shouldFindTwoUserByFirstName() {
		RegularUser user1 = new RegularUser(null, "username", "password", "firstname", "lastname", new Date(),
				Role.REGULAR_USER, Status.ACTIVE);
		RegularUser user2 = new RegularUser(null, "username2", "password2", "firstname", "otherlastname", new Date(),
				Role.REGULAR_USER, Status.ACTIVE);
		List<RegularUser> expected = List.of(userRepository.save(user1), userRepository.save(user2));
		List<RegularUser> actual = userRepository.search("firstname");
		assertEquals(expected, actual);
	}

	@Test
	void shouldFindUserFriends() {
		RegularUser user = new RegularUser(null, "username", "password", "firstname", "lastname", new Date(),
				Role.REGULAR_USER, Status.ACTIVE);
		RegularUser friend1 = userRepository.save(new RegularUser(null, "username1", "password1", "firstname1",
				"lastname1", new Date(), Role.REGULAR_USER, Status.ACTIVE));
		RegularUser friend2 = userRepository.save(new RegularUser(null, "username2", "password2", "firstname",
				"lastname2", new Date(), Role.REGULAR_USER, Status.ACTIVE));
		user.addFriend(friend1);
		user.addFriend(friend2);
		List<RegularUser> expected = List.of(friend1, friend2);
		RegularUser savedUser = userRepository.save(user);
		List<RegularUser> actual = userRepository.findFriends(savedUser.getId());
		assertEquals(expected, actual);

	}

	@Test
	void shouldFindUserFriendOf() {
		RegularUser user = userRepository.save(new RegularUser(null, "username", "password", "firstname", "lastname",
				new Date(), Role.REGULAR_USER, Status.ACTIVE));
		RegularUser friend1 = userRepository.save(new RegularUser(null, "username1", "password1", "firstname1",
				"lastname1", new Date(), Role.REGULAR_USER, Status.ACTIVE));
		RegularUser friend2 = userRepository.save(new RegularUser(null, "username2", "password2", "firstname",
				"lastname2", new Date(), Role.REGULAR_USER, Status.ACTIVE));
		friend1.addFriend(user);
		friend2.addFriend(user);
		friend1 = userRepository.save(friend1);
		friend2 = userRepository.save(friend2);
		List<RegularUser> expected = List.of(friend1, friend2);
		RegularUser savedUser = userRepository.findById(user.getId()).get();
		List<RegularUser> actual = userRepository.findFriendOf(savedUser.getId());
		assertEquals(expected, actual);

	}

}
