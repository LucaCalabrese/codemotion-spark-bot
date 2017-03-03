package com.italtel.chatbot.codemotion.logic.utils;

public class MessageUtils {

	public static String buildMessage(String text, String ans1, String ans2, String ans3, String ans4) {
		String message = "**" + text + "**" + "<br><br>";
		String ans1Built = "A: ".concat(ans1).concat("<br>");
		String ans2Built = "B: ".concat(ans2).concat("<br>");
		String ans3Built = "C: ".concat(ans3).concat("<br>");
		String ans4Built = "D: ".concat(ans4).concat("<br>");
		message = message + ans1Built + ans2Built + ans3Built + ans4Built;
		return message;
	}

	public static String buildReport(String text, String ans1, String ans2, String ans3, String ans4, int ansCorrect,
			int ansGiven, int score) {
		String message = "**" + text + "**" + "<br><br>";
		String ans1Built = "A: ".concat(ans1);
		String ans2Built = "B: ".concat(ans2);
		String ans3Built = "C: ".concat(ans3);
		String ans4Built = "D: ".concat(ans4);
		String scoreStr = "Score: **".concat(String.valueOf(score)).concat(" points**<br><br>");
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
		if (ansCorrect != ansGiven) {
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
}
