package com.s7.socialnetwork.domain.security;

import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.security.core.authority.SimpleGrantedAuthority;

public enum Role {

	REGULAR_USER(Set.of(Permission.ACT)), ADMINISTRATOR(Set.of(Permission.ACT, Permission.SERVER_CHANGE));

	private final Set<Permission> permissions;

	Role(Set<Permission> permissions) {
		this.permissions = permissions;
	}

	public Set<Permission> getPermissions() {
		return permissions;
	}

	public Set<SimpleGrantedAuthority> getAuthorities() {
		return getPermissions().stream().map(permission -> new SimpleGrantedAuthority(permission.getPermission()))
				.collect(Collectors.toSet());
	}

}
