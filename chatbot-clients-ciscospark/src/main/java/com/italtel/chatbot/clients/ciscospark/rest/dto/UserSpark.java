package com.italtel.chatbot.clients.ciscospark.rest.dto;

import com.ciscospark.Message;
import com.ciscospark.Spark;

public class UserSpark {

	private Spark spark;
	private String email;

	public Spark getSpark() {
		return spark;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setSpark(Spark spark) {
		this.spark = spark;
	}

	public UserSpark(Spark spark, String email) {
		super();
		this.spark = spark;
		this.email = email;
	}

	public UserSpark write(String text) {
		// Post a text message to the room
		Message message = new Message();
		message.setToPersonEmail(email);
		message.setMarkdown(text);
		spark.messages().post(message);
		return this;
	}

}
