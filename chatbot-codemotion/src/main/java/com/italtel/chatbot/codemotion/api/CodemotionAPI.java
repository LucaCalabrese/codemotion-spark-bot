package com.italtel.chatbot.codemotion.api;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.italtel.chatbot.codemotion.logic.dto.ScoreDTO;
import com.italtel.chatbot.codemotion.logic.dto.TextDTO;
import com.italtel.chatbot.codemotion.logic.entities.User;
import com.italtel.chatbot.codemotion.logic.service.ChatOpsServiceBean;
import com.italtel.chatbot.codemotion.logic.service.ConfigServiceBean;
import com.italtel.chatbot.codemotion.logic.service.GameServiceBean;
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
		String responseText = null;
		System.out.println("Text has come! " + text);
		if (text != null) {
			String normalized = text.toLowerCase().trim();
			String userId = textDTO.getUserId();
			User user = userBean.findUser(userId);
			if (user != null && user.getPhone() == null) {
				user.setPhone(normalized);
				String help = gameBean.getHelp();
				responseText = "So your phone number is ".concat(normalized)
						.concat(". Thank you! Type **play** again to start with the first question!<br>").concat(help);
				String ctaMsg = configBean.getConfig("CTA_START_MSG");
				if (ctaMsg != null) {
					responseText = responseText.concat("<br>" + ctaMsg);
				}
				gameBean.sendResponse(textDTO, responseText);
			} else if ("phone".equals(normalized) && user != null) {
				user.setPhone(null);
				responseText = "What's your mobile phone number?";
				gameBean.sendResponse(textDTO, responseText);
			} else if (START_LABELS.contains(normalized)) {
				responseText = gameBean.startGame(userId, textDTO.getUsername(), textDTO.getEmail());
				gameBean.sendResponse(textDTO, responseText);
			} else if (ANSWER_LABELS.contains(normalized)) {
				if (user != null) {
					if ("WAITING".equals(user.getStatus())) {
						responseText = gameBean.processAnswer(user, normalized);
						if (responseText != null) {
							gameBean.sendResponse(textDTO, responseText);
							String report = gameBean.getReport(user);
							if (report != null) {
								// Send report
								try {
									Thread.sleep(5000);
								} catch (InterruptedException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
								String ctaMsg = configBean.getConfig("CTA_END_MSG");
								if (ctaMsg != null) {
									report = report.concat(ctaMsg);
								}
								gameBean.sendResponse(textDTO, report);
								// Add map
								TextDTO mapDTO = new TextDTO("The Lab location is marked by a cross on the map!");
								List<String> attachments = new ArrayList<String>();
								String mapURL = configBean.getConfig("MAP_URL");
								attachments.add(mapURL);
								mapDTO.setAttachments(attachments);
								mapDTO.setEmail(textDTO.getEmail());
								mapDTO.setConversationId(textDTO.getConversationId());
								gameBean.sendResponse(mapDTO, mapDTO.getText());
							}
						}
					} else if ("FINISHED".equals(user.getStatus())) {
						if (gameBean.isGameComplete(user)) {
							// Game completed
							responseText = "You have already completed the game!<br>Come to **Cisco** Lab to see if you are the geek-est of Codemotion!<br>Best of luck!";
						} else {
							responseText = "Type **next** for the next question!";
						}
						gameBean.sendResponse(textDTO, responseText);
					}
				} else {
					// No user
					responseText = "Type **play** to start the game!";
					gameBean.sendResponse(textDTO, responseText);
				}
			} else if (SCORE_LABELS.contains(normalized)) {
				responseText = gameBean.getScore(userId);
				gameBean.sendResponse(textDTO, responseText);
				// ChatOps
			} else if (text.startsWith("/clean")) {
				// Clean given user
				String cmd = new String(text);
				String userEmail = cmd.replaceAll("/clean", "").trim();
				responseText = chatOpsBean.cleanUserHistory(userId, userEmail);
				gameBean.sendResponse(textDTO, responseText);
			} else if ("/clearcache".equals(normalized) && userBean.isAdmin(userId)) {
				responseText = chatOpsBean.clearCache(userId);
				gameBean.sendResponse(textDTO, responseText);
			} else if ("/winners".equals(normalized)) {
				responseText = chatOpsBean.getWinners(userId);
				gameBean.sendResponse(textDTO, responseText);
			} else if ("help".equals(normalized)) {
				responseText = gameBean.getHelp();
				gameBean.sendResponse(textDTO, responseText);
			} else if ("now".equals(normalized)) {
				responseText = gameBean.getEvents();
				gameBean.sendResponse(textDTO, responseText);
			} else {
				responseText = "Sorry, I don't understand.<br>".concat(gameBean.getHelp());
				gameBean.sendResponse(textDTO, responseText);
			}
		}
	}

	@POST
	@Path("events/new-room")
	@Consumes(MediaType.APPLICATION_JSON)
	public void handleNewRoomEvent(TextDTO input) {
		TextDTO textDTO = new TextDTO();
		textDTO.setConversationId(input.getConversationId());
		String text = configBean.getConfig("WELCOME_MSG");
		if (text != null) {
			textDTO.setText(text);
			gameBean.sendResponse(textDTO, text);
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

}
