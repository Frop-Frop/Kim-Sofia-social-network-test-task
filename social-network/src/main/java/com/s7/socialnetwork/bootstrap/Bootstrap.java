package com.s7.socialnetwork.bootstrap;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.s7.socialnetwork.domain.Post;
import com.s7.socialnetwork.domain.RegularUser;
import com.s7.socialnetwork.domain.security.Role;
import com.s7.socialnetwork.domain.security.Status;
import com.s7.socialnetwork.repositories.PostRepository;
import com.s7.socialnetwork.repositories.UserRepository;

@Component
public class Bootstrap implements CommandLineRunner {

	private final UserRepository userRepository;

	private final PasswordEncoder passwordEncoder;

	private final PostRepository postRepository;

	private static final Logger log = LoggerFactory.getLogger(Bootstrap.class);

	public Bootstrap(UserRepository userRepository, PasswordEncoder passwordEncoder, PostRepository postRepository) {
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
		this.postRepository = postRepository;
	}

	@Override
	public void run(String... args) throws Exception {
		if (userRepository.count() == 0l) {
			loadUsers();
			log.debug("Users loaded");
		}
		if (postRepository.count() == 0l) {
			loadPosts();
			log.debug("Users loaded");
		}
	}

	private void loadUsers() {
		List<String> firstNames = Arrays.asList("Marylin", "Ivy", "Duke", "Derrek", "Marcus", "Lynna", "Sandor",
				"Dimitry", "Ernest", "Elsworth");
		List<String> lastNames = Arrays.asList("Bett", "Spieck", "Mabee", "Schuck", "Roderick", "Briatt", "Skipping",
				"Wadlow", "Ragat", "Greger");
		List<String> passwords = Arrays.asList("MayThe4thBeWithYou", "NVJ45-fd", "gGj440_12a", "JNnvk*ikf", "Rick4Roll",
				"jKLr--hj", "HSFHf67j", "uusqms", "kmhdfgn", "password");

		RegularUser user;
		for (int i = 0; i < 10; i++) {
			user = new RegularUser();
			user.setPassword(passwordEncoder.encode(passwords.get(i)));
			user.setStatus(Status.ACTIVE);
			user.setRole(Role.REGULAR_USER);
			user.setFirstName(firstNames.get(i));
			user.setLastName(lastNames.get(i));
			user.setUsername(user.getFirstName() + "_" + user.getLastName());
			user.setBirthday(randomDateBetween(dateFor(1920, 01, 01), dateFor(2009, 01, 01)));

			userRepository.save(user);
		}
		makeFriends();
	}

	private void loadPosts() {
		List<String> postContent = Arrays.asList("Hello", "Lorem ipsum dolor sit amet",
				"Lorem ipsum dolor sit amet, consectetur adipiscing elit", "Lorem ipsum", "Lorem ipsum dolor",
				"Leo in vitae turpis massa sed elementum tempus", "Vel eros donec ac odio.",
				"Porta non pulvinar neque laoreet suspendisse interdum", "Elementum nibh tellus", "Turpis egestas sed");
		userRepository.findAll().stream().forEach(user -> {
			Set<Post> posts = postContent.stream().map(string -> {
				Post post = new Post();
				post = new Post();
				post.setPostContent(string);
				post.setLastUpdate(new Date());
				post = postRepository.save(post);
				post.setUser(user);
				return post;
			}).collect(Collectors.toSet());
			user.setPosts(posts);
			userRepository.save(user);
		});

	}

	private void makeFriends() {

		userRepository.findAll().stream().forEach(user -> {
			RegularUser friend;
			for (int i = 0; i < 3; i++) {
				Long friendId = randomLong(user.getId());
				friend = userRepository.findById(friendId).get();
				if (!user.hasFriend(friend) && !friend.isFriendOf(user)) {
					user.addFriend(friend);
					userRepository.save(friend);
				}

			}
			userRepository.save(user);
		});
	}

	private static Date randomDateBetween(Date from, Date until) {
		long startMillis = from.getTime();
		long endMillis = until.getTime();
		long randomMillis = ThreadLocalRandom.current().nextLong(startMillis, endMillis);

		return new Date(randomMillis);
	}

	private static Date dateFor(int year, int month, int day) {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, year);
		cal.set(Calendar.MONTH, month);
		cal.set(Calendar.DAY_OF_MONTH, day);
		return cal.getTime();
	}

	private static long randomLong(Long excludeNumber) {
		long result = (long) ((Math.random() * (10 - 1)) + 1);
		if (result == excludeNumber) {
			result = randomLong(excludeNumber);
		}
		return result;
	}

}
