package com.italtel.chatbot.codemotion.logic.enums;

public enum Role {

	ADMIN, MARKETING;

	public static boolean contains(Role role) {
		for (Role value : values()) {
			if (value.equals(role)) {
				return true;
			}
		}
		return false;
	}
}
