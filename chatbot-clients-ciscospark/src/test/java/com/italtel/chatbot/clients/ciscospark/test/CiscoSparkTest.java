package com.italtel.chatbot.clients.ciscospark.test;
import java.net.URI;

import com.ciscospark.Membership;
import com.ciscospark.Message;
import com.ciscospark.Room;
import com.ciscospark.Spark;
import com.ciscospark.Webhook;

public class CiscoSparkTest {

	public static void main(String[] args) {
		// To obtain a developer access token, visit
		// http://developer.ciscospark.com
		String accessToken = "NTM1NmFjMWMtZTViOS00ZTRiLTk3ZTEtODY5NGRhM2ZjYWFkODdmYmFiYjItODA0";

		// Initialize the client
		Spark spark = Spark.builder().baseUrl(URI.create("https://api.ciscospark.com/v1")).accessToken(accessToken)
				.build();

		// List the rooms that I'm in
		spark.rooms().iterate().forEachRemaining(room -> {
			System.out.println(room.getTitle() + ", created " + room.getCreated() + ": " + room.getId());
		});

		// Create a new room
		Room room = new Room();
		room.setTitle("Hello World");
		room = spark.rooms().post(room);

		// Add a coworker to the room
		Membership membership = new Membership();
		membership.setRoomId(room.getId());
		membership.setPersonEmail("luca.calabrese@italtel.com");
		spark.memberships().post(membership);

		// List the members of the room
		spark.memberships().queryParam("roomId", room.getId()).iterate().forEachRemaining(member -> {
			System.out.println(member.getPersonEmail());
		});

		// Post a text message to the room
		Message message = new Message();
		message.setRoomId(room.getId());
		message.setText("Hello World!");
		spark.messages().post(message);

		// Share a file with the room
		// message = new Message();
		// message.setRoomId(room.getId());
		// message.setFiles(URI.create("http://example.com/hello_world.jpg"));
		// spark.messages().post(message);

		// Create a new team
		// Team team = new Team();
		// team.setName("Brand New Team");
		// team = spark.teams().post(team);
		//
		// // Add a coworker to the team
		// TeamMembership teamMembership = new TeamMembership();
		// teamMembership.setTeamId(team.getId());
		// teamMembership.setPersonEmail("wile_e_coyote@acme.com");
		// spark.teamMemberships().post(teamMembership);
		//
		// // List the members of the team
		// spark.teamMemberships().queryParam("teamId",
		// team.getId()).iterate().forEachRemaining(member -> {
		// System.out.println(member.getPersonEmail());
		// });

		// Create webhook if not exists
		Webhook webhook = new Webhook();
		
		spark.webhooks().post(webhook);
		
	}

}
