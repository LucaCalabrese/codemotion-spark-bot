package com.italtel.chatbot.codemotion.logic.enums;

public enum AnswerLabel {

	A(1), B(2), C(3), D(4);

	private int id;

	private AnswerLabel(int id) {
		this.id = id;
	}

	public int getValue() {
		return id;
	}

	/**
	 * Returns the the AnswerLabel associated to the given value
	 * 
	 * @param value
	 * @return the found AnswerLabel or null if not found
	 */
	public static AnswerLabel getByValue(int value) {
		AnswerLabel result = null;
		for (AnswerLabel al : values()) {
			if (al.getValue() == value) {
				result = al;
			}
		}
		return result;
	}

	@Override
	public String toString() {
		return name().toLowerCase();
	}

	public static AnswerLabel getByLabel(String label) {
		AnswerLabel result = null;
		AnswerLabel[] allist = AnswerLabel.A.getDeclaringClass().getEnumConstants();
		for (AnswerLabel al : allist) {
			if (al.toString().equalsIgnoreCase(label)) {
				result = al;
			}
		}
		return result;
	}

	public static boolean sameValue(int value, String label) {
		AnswerLabel al1 = getByValue(value);
		AnswerLabel al2 = getByLabel(label);
		if (al1 == null) {
			return false;
		}
		return al1.equals(al2);
	}
}
