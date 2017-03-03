package com.italtel.chatbot.clients.ciscospark.rest.resources;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.ciscospark.Message;
import com.ciscospark.Person;
import com.italtel.chatbot.client.interfaces.ClientAPI;
import com.italtel.chatbot.clients.ciscospark.SparkClient;
import com.italtel.chatbot.clients.ciscospark.logic.entities.SparkConfig;
import com.italtel.chatbot.clients.ciscospark.logic.service.SparkConfigServiceBean;
import com.italtel.chatbot.clients.ciscospark.rest.dto.TextDTO;
import com.italtel.chatbot.clients.ciscospark.rest.dto.WebhookEnvelop;

@Path("spark")
public class SparkClientAPI implements ClientAPI {
	@Inject
	private SparkConfigServiceBean configBean;

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("messages")
	public Response handleNewMessage(WebhookEnvelop<Message> envelop) {

		Message data = envelop.getData();
		if (data != null) {
			System.out.println("personEmail: ".concat(data.getPersonEmail()));
			if (data.getPersonEmail() != null && data.getPersonEmail().endsWith("@sparkbot.io")) {
				// Message from Bot: skip.
				return Response.ok().build();
			}
			// Build URI
			String botToken = configBean.getConfig("SPARK_BOT_TOKEN");
			String botHost = configBean.getConfig("BOT_HOST");
			String botPort = configBean.getConfig("BOT_PORT");
			String botContextRoot = configBean.getConfig("BOT_CONTEXT_ROOT");
			String baseURI = "http://".concat(botHost).concat(":").concat(botPort).concat(botContextRoot);
			SparkClient sparkClient = new SparkClient(botToken);
			String messageId = data.getId();
			Message message = sparkClient.findMessage(messageId);
			if (message != null) {
				String text = message.getText();
				String botName = sparkClient.getMe().getDisplayName();
				text = text.replaceAll(botName, "");
				Client client = ClientBuilder.newClient();
				WebTarget target = client.target(baseURI.concat("/api/engine/text"));
				TextDTO textDTO = new TextDTO(text);
				textDTO.setUserId(message.getPersonId());
				textDTO.setConversationId(data.getRoomId());
				textDTO.setEmail(data.getPersonEmail());
				String username = sparkClient.getUserProfile(message.getPersonId()).getDisplayName();
				textDTO.setUsername(username);
				Entity<TextDTO> entity = Entity.entity(textDTO, MediaType.APPLICATION_JSON);
				Response response = target.request().post(entity);
				response.close(); // You should close connections!
				System.out.println(message.getText());
				return Response.ok().build();
			} else {
				return Response.status(Status.NOT_FOUND).build();
			}
		} else {
			return Response.status(Status.NOT_FOUND).build();
		}
	}

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("messages/write")
	public Response writeMessage(TextDTO textDTO) {
		System.out.println("reached");
		String botToken = configBean.getConfig("SPARK_BOT_TOKEN");
		SparkClient client = new SparkClient(botToken);
		if (textDTO.getTargetUsername() != null) {
			client.findUser(textDTO.getTargetUsername()).write(textDTO.getText());
		} else if (textDTO.getConversationId() != null) {
			client.findConversation(textDTO.getConversationId()).write(textDTO.getText());
		} else {
			return Response.status(400).build();
		}
		return Response.ok().build();
	}

	@GET
	@Path("status")
	public String getStatus() {
		return "OK";
	}
}