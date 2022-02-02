package com.s7.socialnetwork.domain.security;

public enum Permission {

	ACT("act"), SERVER_CHANGE("server_change");

	private final String permission;

	Permission(String permission) {
		this.permission = permission;
	}

	public String getPermission() {
		return permission;
	}
}
