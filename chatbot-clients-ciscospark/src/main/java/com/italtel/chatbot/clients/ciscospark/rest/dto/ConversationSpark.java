package com.italtel.chatbot.clients.ciscospark.rest.dto;

import java.net.URI;
import java.util.List;

import com.ciscospark.Message;
import com.ciscospark.Spark;

public class ConversationSpark {

	private Spark spark;
	private String id;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Spark getSpark() {
		return spark;
	}

	public void setSpark(Spark spark) {
		this.spark = spark;
	}

	public ConversationSpark(Spark spark, String id) {
		super();
		this.spark = spark;
		this.id = id;
	}

	public ConversationSpark write(String text) {
		return write(text, null);
	}

	public ConversationSpark write(String text, List<String> attachments) {
		// Post a text message to the room
		Message message = new Message();
		message.setRoomId(id);
		message.setMarkdown(text);
		if (attachments != null && !attachments.isEmpty()) {
			URI[] files = new URI[attachments.size()];
			for (int i = 0; i < attachments.size(); i++) {
				try {
					URI file = new URI(attachments.get(i));
					files[i] = file;
				} catch (Exception e) {
					// LOGGER.error(e.getMessage(), e);
				}
			}
			message.setFiles(files);
		}
		spark.messages().post(message);
		return this;
	}

}
