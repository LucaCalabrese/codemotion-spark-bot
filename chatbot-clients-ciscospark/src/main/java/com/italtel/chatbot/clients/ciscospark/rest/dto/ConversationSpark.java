package com.italtel.chatbot.clients.ciscospark.rest.dto;

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
		// Post a text message to the room
		Message message = new Message();
		message.setRoomId(id);
		message.setMarkdown(text);
		spark.messages().post(message);
		return this;
	}

}
