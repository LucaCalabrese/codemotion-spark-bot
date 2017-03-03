package com.italtel.chatbot.clients.ciscospark.test;

import com.italtel.chatbot.clients.ciscospark.SparkClient;

public class SparkClientTest {

	public static void main(String[] args) {
		final String tokenId = "NTM1NmFjMWMtZTViOS00ZTRiLTk3ZTEtODY5NGRhM2ZjYWFkODdmYmFiYjItODA0";
		final String roomId = "Y2lzY29zcGFyazovL3VzL1JPT00vZmQ4YTBhYzAtZDY1Ny0xMWU2LTk1Y2MtMzFhMTM0Y2RkY2Yy";
		new SparkClient(tokenId).findConversation(roomId).write("Hello from your test!");
	}

}
