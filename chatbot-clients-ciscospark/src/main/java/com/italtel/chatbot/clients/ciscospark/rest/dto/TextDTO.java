package com.italtel.chatbot.clients.ciscospark.rest.dto;

public class TextDTO {

	private String text;
	private String userId;
	private String conversationId;
	private String username;
	private String email;
	private String targetUsername;

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public TextDTO(String text) {
		super();
		this.text = text;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getConversationId() {
		return conversationId;
	}

	public void setConversationId(String conversationId) {
		this.conversationId = conversationId;
	}

	public String getTargetUsername() {
		return targetUsername;
	}

	public void setTargetUsername(String targetUsername) {
		this.targetUsername = targetUsername;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public TextDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

}
