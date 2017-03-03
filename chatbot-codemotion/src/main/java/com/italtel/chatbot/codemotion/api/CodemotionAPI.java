package com.italtel.chatbot.codemotion.api;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.italtel.chatbot.codemotion.api.dto.ScoreDTO;
import com.italtel.chatbot.codemotion.api.dto.TextDTO;
import com.italtel.chatbot.codemotion.logic.entities.Question;
import com.italtel.chatbot.codemotion.logic.entities.User;
import com.italtel.chatbot.codemotion.logic.entities.UserAnswer;
import com.italtel.chatbot.codemotion.logic.enums.AnswerLabel;
import com.italtel.chatbot.codemotion.logic.service.ChatOpsServiceBean;
import com.italtel.chatbot.codemotion.logic.service.ConfigServiceBean;
import com.italtel.chatbot.codemotion.logic.service.GameServiceBean;
import com.italtel.chatbot.codemotion.logic.service.QuestionServiceBean;
import com.italtel.chatbot.codemotion.logic.service.UserServiceBean;

@Path("engine")
@Stateless
public class CodemotionAPI {

	// Constants
	private static final List<String> ANSWER_LABELS = Arrays.asList("a", "b", "c", "d");
	private static final List<String> START_LABELS = Arrays.asList("start", "next", "play", "game");
	private static final List<String> SCORE_LABELS = Arrays.asList("score", "points");
	private static final int MAX_TOP_SCORERS_DEFAULT = 10;

	@Inject
	private GameServiceBean gameBean;

	@Inject
	private UserServiceBean userBean;

	@Inject
	private ConfigServiceBean configBean;

	@Inject
	private ChatOpsServiceBean chatOpsBean;

	/**
	 * Handles the game.<br>
	 * <br>
	 * 
	 * 1. User says "start"<br>
	 * 2. Check if user is registered or not (search in the users list)<br>
	 * 2a. If user is not registered: create user and create his/her game (load
	 * all random questions) <br>
	 * 3. Check the user status:<br>
	 * 3a. If finished, pick the first question from the list, switch the status
	 * to waiting, copy last_question_id and stage_id from question to user<br>
	 * 3b. If status is waiting, say something to the user like 'game has
	 * already started'<br>
	 * 4. User gives a response (a, b, c or d)<br>
	 * 5. Pick the question from table user (last_question_id) and check the
	 * answer<br>
	 * 6. Update UserAnswers table with correct answer and score<br>
	 * 7. Update user status to finished<br>
	 * 8. Give the correct answer to the user<br>
	 * 9. If it is the last question of the stage
	 * (questions_answered=questions_per_stage), show a report<br>
	 * 10. User says "next"<br>
	 * 
	 * @param textDTO
	 */
	@POST
	@Path("text")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public void process(TextDTO textDTO) {
		String text = textDTO.getText();
		String responseText = "Sorry, I don't understand.";
		System.out.println("Text has come! " + text);
		if (text != null) {
			String normalized = text.toLowerCase().trim();
			String userId = textDTO.getUserId();
			if (START_LABELS.contains(normalized)) {
				responseText = gameBean.startGame(userId, textDTO.getUsername(), textDTO.getEmail());
			} else if (ANSWER_LABELS.contains(normalized)) {
				responseText = gameBean.processAnswer(userId, normalized);
			} else if (SCORE_LABELS.contains(normalized)) {
				responseText = gameBean.getScore(userId);
				// ChatOps
			} else if (text.startsWith("/clean")) {
				// Clean given user
				String cmd = new String(text);
				String userEmail = cmd.replaceAll("/clean", "").trim();
				responseText = chatOpsBean.cleanUserHistory(userId, userEmail);
			} else {
				// Other cases
				// NLP?
			}
			// Send response to the user
			sendResponse(textDTO, responseText);
		}
	}

	@GET
	@Path("status")
	public String getStatus() {
		return "OK";
	}

	@GET
	@Path("scores")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getHighScores() {
		List<ScoreDTO> scores = new ArrayList<>();
		int max = MAX_TOP_SCORERS_DEFAULT;
		String maxStr = configBean.getConfig("MAX_TOP_SCORERS");
		if (maxStr != null) {
			try {
				max = Integer.valueOf(maxStr);
			} catch (NumberFormatException e) {
				// Skip
				System.out.println(e.getMessage());
			}
		}
		List<User> topScorers = userBean.getTopScorers(max);
		for (int i = 0; i < topScorers.size(); i++) {
			User user = topScorers.get(i);
			ScoreDTO score = new ScoreDTO();
			score.setRank(i + 1);
			score.setName(user.getUsername());
			score.setScore(user.getTotalScore());
			scores.add(score);
		}
		return Response.ok().entity(scores).header("Access-Control-Allow-Origin", "*")
				.header("Access-Control-Allow-Methods", "GET").build();
	}

	public void sendResponse(TextDTO request, String responseText) {
		System.out.println(responseText);
		String sparkHost = configBean.getConfig("SPARK_HOST");
		String sparkPort = configBean.getConfig("SPARK_PORT");
		String sparkCR = configBean.getConfig("SPARK_CONTEXT_ROOT");
		String baseURI = "http://".concat(sparkHost).concat(":").concat(sparkPort).concat(sparkCR);
		Client client = ClientBuilder.newClient();
		WebTarget target = client.target(baseURI + "/api/spark/messages/write");
		TextDTO newResponse = new TextDTO(responseText);
		newResponse.setConversationId(request.getConversationId());
		newResponse.setTargetUsername(request.getEmail());
		Entity<TextDTO> entity = Entity.entity(newResponse, MediaType.APPLICATION_JSON);
		Response response = target.request().post(entity);
		response.close(); // You should close connections!
	}
}
