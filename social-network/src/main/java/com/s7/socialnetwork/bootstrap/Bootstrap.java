package com.s7.socialnetwork.bootstrap;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.s7.socialnetwork.domain.RegularUser;
import com.s7.socialnetwork.repositories.UserRepository;

@Component
public class Bootstrap implements CommandLineRunner {

	private final UserRepository userRepository;

	public Bootstrap(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@Override
	public void run(String... args) throws Exception {
		if (userRepository.count() == 0l) {
			loadUsers();
		}
	}

	private void loadUsers() {
		List<String> firstNames = Arrays.asList("Marylin", "Ivy", "Duke", "Derrek", "Marcus", "Lynna", "Sandor",
				"Dimitry", "Ernest", "Elsworth");
		List<String> lastNames = Arrays.asList("Bett", "Spieck", "Mabee", "Schuck", "Roderick", "Briatt", "Skipping",
				"Wadlow", "Ragat", "Elsworth Greger");
		List<String> passwords = Arrays.asList("MayThe4thBeWithYou", "NVJ45-fd", "gGj440_12a", "JNnvk*ikf", "Rick4Roll",
				"jKLr--hj", "HSFHf67j", "uusqms", "kmhdfgn", "password");

		RegularUser user;
		for (int i = 0; i < 10; i++) {
			user = new RegularUser();
			user.setPassword(passwords.get(i)); // implement encoder here
			user.setFirstName(firstNames.get(i));
			user.setLastName(lastNames.get(i));
			user.setUsername(user.getFirstName() + "_" + user.getLastName());
			user.setBirthday(randomDateBetween(dateFor(1920, 01, 01), dateFor(2009, 01, 01)));

			userRepository.save(user);
		}
		makeFriends();
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
