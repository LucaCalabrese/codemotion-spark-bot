package com.italtel.chatbot.codemotion.logic.service;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import com.italtel.chatbot.codemotion.logic.entities.Question;
import com.italtel.chatbot.codemotion.logic.entities.User;
import com.italtel.chatbot.codemotion.logic.entities.UserAnswer;
import com.italtel.chatbot.codemotion.logic.enums.AnswerLabel;
import com.italtel.chatbot.codemotion.logic.utils.MessageUtils;

@Stateless
public class GameServiceBean {

	@Inject
	private UserServiceBean userBean;

	@Inject
	private QuestionServiceBean questionBean;

	public String startGame(String userId, String username, String email) {
		String responseText = null;
		User user = userBean.findUser(userId);
		if (user == null) {
			user = userBean.addNewUser(userId, email, username);
		}
		if ("FINISHED".equals(user.getStatus())) {
			Question nextQuestion = questionBean.getNextQuestion(userId);
			if (nextQuestion != null) {
				user.setStatus("WAITING");
				user.setLastQuestionId(nextQuestion.getId());
				user.setStageId(nextQuestion.getId());
				if (nextQuestion != null) {
					responseText = MessageUtils.buildMessage(nextQuestion.getText(), nextQuestion.getAns1(),
							nextQuestion.getAns2(), nextQuestion.getAns3(), nextQuestion.getAns4());
				}
			} else {
				responseText = "You have already completed the game!<br>Come to **Cisco** Lab to see if you are the geek-est of Codemotion!<br>Best of luck!";
			}
		} else if ("WAITING".equals(user.getStatus())) {
			responseText = "Game has already started! Hurry!";
		}
		return responseText;
	}

	public String processAnswer(String userId, String text) {
		String responseText = null;
		User user = userBean.findUser(userId);
		if (user != null) {
			// Check status
			if ("WAITING".equals(user.getStatus())) {
				Integer lastQuestionId = user.getLastQuestionId();
				if (lastQuestionId != null) {
					Question lastQuestion = questionBean.findQuestion(lastQuestionId);
					Integer correctAns = lastQuestion.getCorrectAns();
					AnswerLabel correctLabel = AnswerLabel.getByValue(correctAns);
					AnswerLabel ansLabel = AnswerLabel.getByLabel(text);
					UserAnswer ua = questionBean.findAnswerByUserIdAndQuestionId(userId, lastQuestionId);
					boolean correct = correctLabel != null && correctLabel.equals(ansLabel);
					Integer score = 0;
					if (correct) {
						// Correct answer
						score = 100;
						responseText = "And the answer is... correct! Congrats!<br>You earned **"
								.concat(score.toString()).concat("** points!");
					} else {
						// Wrong answer
						score = 0;
						responseText = "Wrong answer! The correct answer was: **".concat(correctLabel.name())
								.concat("**");
					}
					ua.setCorrect(correct);
					Integer ansGiven = null;
					if (ansLabel != null) {
						ansGiven = ansLabel.getValue();
					}
					ua.setAnsGiven(ansGiven);
					ua.setAnswered(true);
					ua.setScore(score);
					Integer totalScore = score;
					if (user.getTotalScore() != null) {
						totalScore += user.getTotalScore();
					}
					user.setTotalScore(totalScore);
					user.setStatus("FINISHED");
					if (questionBean.getNextQuestion(userId) == null) {
						// The game is complete, show report
						responseText = "You have completed the game!<br>Here are your results:<br><br>";
						List<UserAnswer> answers = questionBean.findAllUserAnswers(userId);
						for (UserAnswer answer : answers) {
							Integer qId = answer.getId().getQuestionId();
							Question q = questionBean.findQuestion(qId);
							String report = MessageUtils.buildReport(q.getText(), q.getAns1(), q.getAns2(), q.getAns3(),
									q.getAns4(), q.getCorrectAns(), answer.getAnsGiven(), answer.getScore());
							responseText = responseText.concat(report);
						}
						responseText = responseText.concat("**YOUR TOTAL SCORE IS: ")
								.concat(user.getTotalScore().toString()).concat(" POINTS**<br>")
								.concat("Come to **Cisco** Lab to see if you are the geek-est of Codemotion!<br>");
					}
				}
			} else {
				// Finished
			}
		}
		return responseText;
	}

	public String getScore(String userId) {
		// Tell total score
		String responseText = null;
		User user = userBean.findUser(userId);
		Integer score = 0;
		if (user != null && user.getTotalScore() != null) {
			score = user.getTotalScore();
		}
		responseText = "Your score is: **".concat(String.valueOf(score)).concat("** points");
		return responseText;
	}

}
