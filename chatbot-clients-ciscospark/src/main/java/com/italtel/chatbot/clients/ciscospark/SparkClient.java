package com.italtel.chatbot.clients.ciscospark;

import java.net.URI;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.ciscospark.Membership;
import com.ciscospark.Message;
import com.ciscospark.Person;
import com.ciscospark.Spark;
import com.italtel.chatbot.clients.ciscospark.rest.dto.ConversationSpark;
import com.italtel.chatbot.clients.ciscospark.rest.dto.UserSpark;

public class SparkClient {

	private static final String SPARK_API_URI = "https://api.ciscospark.com/v1";
	private String tokenId;
	private Spark spark;

	public String getTokenId() {
		return tokenId;
	}

	public void writeMessage(String text, String conversationId) {
		// Post a text message to the room
		Message message = new Message();
		message.setRoomId(conversationId);
		message.setText(text);
		spark.messages().post(message);
	}

	public void setTokenId(String tokenId) {
		this.tokenId = tokenId;
	}

	public SparkClient(String tokenId) {
		this.tokenId = tokenId;
		// Initialize the client
		this.spark = Spark.builder().baseUrl(URI.create(SPARK_API_URI)).accessToken(tokenId).build();
	}

	public List<String> getConversations() {
		// List the rooms that I'm in
		List<String> rooms = new ArrayList<String>();
		spark.rooms().iterate().forEachRemaining(room -> {
			rooms.add(room.getId());
		});
		return rooms;
	}

	public String getConversation(String personId) {
		String conversationId = null;
		Iterator<Membership> iterator = spark.memberships().queryParam("personEmail", personId).iterate();
		if (iterator.hasNext()) {
			Membership membership = iterator.next();
			if (membership.getPersonId().equals(personId)) {
				conversationId = membership.getRoomId();
			}
		}
		return conversationId;
	}

	public ConversationSpark findConversation(String conversationId) {
		return new ConversationSpark(spark, conversationId);
	}

	public Message findMessage(String messageId) {
		Message message = spark.messages().path("/" + messageId).get();
		return message;
	}

	public UserSpark findUser(String userEmail) {
		return new UserSpark(spark, userEmail);
	}

	public Person getMe() {
		return spark.people().path("/me").get();
	}

	public Person getUserProfile(String userId) {
		return spark.people().path("/" + userId).get();
	}
}
