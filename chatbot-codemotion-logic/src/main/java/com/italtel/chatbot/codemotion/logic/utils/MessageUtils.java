package com.italtel.chatbot.codemotion.logic.utils;

import java.util.Arrays;

public class MessageUtils {

	private static final String[] CORRECT_ANSWER_MSGS = new String[] { "And the answer is... correct! Congrats!",
			"You rock!", "Wow that wasn't easy! Well done!",
			"I guess that was too easy for you! I'll look for a more difficult question next time...",
			"That's correct! Amazing!", "Exactly.", "Like a boss!", "We have a true geek here!" };

	private static final String[] WRONG_ANSWER_MSGS = new String[] { "Wrong answer!", "Not really...",
			"I'm afraid you had a mistake.", "That's wrong unfortunately...",
			"Wrong! But I'm sure you will do better next time!", "That's not the correct answer unfortunately.",
			"It was so close... but not correct." };

	public static String buildMessage(String text, String ans1, String ans2, String ans3, String ans4) {
		String message = "**" + text + "**" + "<br><br>";
		String ans1Built = "A: ".concat(ans1).concat("<br>");
		String ans2Built = "B: ".concat(ans2).concat("<br>");
		String ans3Built = "C: ".concat(ans3).concat("<br>");
		String ans4Built = "D: ".concat(ans4).concat("<br>");
		String help = "<br>Type A, B, C or D";
		message = message + ans1Built + ans2Built + ans3Built + ans4Built + help;
		return message;
	}

	public static String buildReport(String text, String ans1, String ans2, String ans3, String ans4,
			Integer ansCorrect, Integer ansGiven, int score) {
		String message = "**".concat(text).concat("**<br><br>");
		String ans1Built = "A: ".concat(ans1);
		String ans2Built = "B: ".concat(ans2);
		String ans3Built = "C: ".concat(ans3);
		String ans4Built = "D: ".concat(ans4);
		String scoreStr = "Score: **".concat(String.valueOf(score)).concat(" points**");
		if (ansGiven == null) {
			scoreStr = scoreStr.concat("* (timeout)*");
		}
		scoreStr = scoreStr.concat("<br><br>");
		switch (ansCorrect) {
		case 1:
			ans1Built = "**".concat(ans1Built).concat("**");
			break;
		case 2:
			ans2Built = "**".concat(ans2Built).concat("**");
			break;
		case 3:
			ans3Built = "**".concat(ans3Built).concat("**");
			break;
		case 4:
			ans4Built = "**".concat(ans4Built).concat("**");
			break;
		}
		if (ansGiven != null && ansCorrect != ansGiven) {
			switch (ansGiven) {
			case 1:
				ans1Built = "*".concat(ans1Built).concat(" (wrong)*");
				break;
			case 2:
				ans2Built = "*".concat(ans2Built).concat(" (wrong)*");
				break;
			case 3:
				ans3Built = "*".concat(ans3Built).concat(" (wrong)*");
				break;
			case 4:
				ans4Built = "*".concat(ans4Built).concat(" (wrong)*");
				break;
			}
		}
		ans1Built = ans1Built.concat("<br>");
		ans2Built = ans2Built.concat("<br>");
		ans3Built = ans3Built.concat("<br>");
		ans4Built = ans4Built.concat("<br>");
		message = message + ans1Built + ans2Built + ans3Built + ans4Built + scoreStr;
		return message;
	}

	private static String pickMessage(String[] values) {
		String msg = null;
		if (values != null && values.length > 0) {
			int random = (int) (Math.random() * values.length);
			msg = values[random];
		}
		return msg;
	}

	public static String pickCorrectMsg() {
		return pickMessage(CORRECT_ANSWER_MSGS);
	}

	public static String pickWrongMsg() {
		return pickMessage(WRONG_ANSWER_MSGS);
	}
}
