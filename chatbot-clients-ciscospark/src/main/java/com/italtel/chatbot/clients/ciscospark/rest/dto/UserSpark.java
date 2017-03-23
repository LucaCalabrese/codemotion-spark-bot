package com.italtel.chatbot.clients.ciscospark.rest.dto;

import java.net.URI;
import java.util.List;

import org.apache.log4j.Logger;

import com.ciscospark.Message;
import com.ciscospark.Spark;

public class UserSpark {

	private static final Logger LOGGER = Logger.getLogger(UserSpark.class);

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
		return write(text, null);
	}

	public UserSpark write(String text, List<String> attachments) {
		// Post a text message to the room
		Message message = new Message();
		message.setToPersonEmail(email);
		message.setMarkdown(text);
		if (attachments != null && !attachments.isEmpty()) {
			URI[] files = new URI[attachments.size()];
			for (int i = 0; i < attachments.size(); i++) {
				try {
					URI file = new URI(attachments.get(i));
					files[i] = file;
				} catch (Exception e) {
					LOGGER.error(e.getMessage(), e);
				}
			}
			message.setFiles(files);
		}
		spark.messages().post(message);
		return this;
	}

}
