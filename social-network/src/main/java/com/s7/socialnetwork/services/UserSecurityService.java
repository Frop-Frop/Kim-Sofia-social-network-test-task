package com.s7.socialnetwork.services;

import java.util.Optional;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.s7.socialnetwork.domain.RegularUser;
import com.s7.socialnetwork.domain.security.SecurityUser;
import com.s7.socialnetwork.repositories.UserRepository;

@Service("userDetailsService")
public class UserSecurityService implements UserDetailsService {

	private final UserRepository userRepository;

	public UserSecurityService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<RegularUser> optionalUser = userRepository.findByUsername(username);

		if (optionalUser.isEmpty()) {
			throw new ResourceNotFoundException("User doesn't exists");
		}
		return SecurityUser.fromRegularUser(optionalUser.get());

	}

}
