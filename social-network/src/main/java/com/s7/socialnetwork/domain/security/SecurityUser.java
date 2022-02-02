package com.s7.socialnetwork.domain.security;

import java.util.List;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.s7.socialnetwork.domain.RegularUser;

import lombok.Data;

@Data
public class SecurityUser implements UserDetails {

	private final String username;
	private final String password;
	private final List<SimpleGrantedAuthority> authorities;
	private final boolean isActive;

	public SecurityUser(String username, String password, List<SimpleGrantedAuthority> authorities, boolean isActive) {
		this.username = username;
		this.password = password;
		this.authorities = authorities;
		this.isActive = isActive;
	}

	@Override
	public boolean isAccountNonExpired() {
		return isActive;
	}

	@Override
	public boolean isAccountNonLocked() {
		return isActive;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return isActive;
	}

	@Override
	public boolean isEnabled() {
		return isActive;
	}

	public static UserDetails fromRegularUser(RegularUser user) {
		return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(),
				user.getStatus().equals(Status.ACTIVE), user.getStatus().equals(Status.ACTIVE),
				user.getStatus().equals(Status.ACTIVE), user.getStatus().equals(Status.ACTIVE),
				user.getRole().getAuthorities());
	}
}
