package com.italtel.chatbot.codemotion.logic.service;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import com.italtel.chatbot.codemotion.logic.entities.Question;
import com.italtel.chatbot.codemotion.logic.entities.UserAnswer;
import com.italtel.chatbot.codemotion.logic.entities.UserAnswerPK;

@Stateless
public class QuestionServiceBean {

	@PersistenceContext(name = "chatbot-codemotion")
	private EntityManager em;

	private static final String DRAW_QUESTION_QUERY = "select q.id from questions q order by random() limit ";

	public Question getNextQuestion(String userId) {
		Question question = null;
		List<UserAnswer> resultList = findAllUserAnswers(userId);
		for (UserAnswer ua : resultList) {
			if (ua.getAnswered() == null || !ua.getAnswered()) {
				Integer questionId = ua.getId().getQuestionId();
				question = em.find(Question.class, questionId);
				return question;
			}
		}
		return question;
	}

	public UserAnswer findAnswerByUserIdAndQuestionId(String userId, Integer questionId) {
		return em.find(UserAnswer.class, new UserAnswerPK(userId, questionId));
	}

	public List<UserAnswer> findAllUserAnswers(String userId) {
		TypedQuery<UserAnswer> uaQuery = em.createNamedQuery("UserAnswer.findByUserId", UserAnswer.class);
		uaQuery.setParameter("userId", userId);
		return uaQuery.getResultList();
	}

	@SuppressWarnings("unchecked")
	public void drawQuestions(String userId, int questionsPerStage, int numberOfStages) {
		// Change if question per stage is variable
		int totalNumberOfQuestions = numberOfStages * questionsPerStage;
		Query questionQuery = em.createNativeQuery(DRAW_QUESTION_QUERY + totalNumberOfQuestions);
		List<Object> resultList = questionQuery.getResultList();
		int counter = 0;
		int stageId = 1;
		for (Object result : resultList) {
			Integer questionId = (Integer) result;
			UserAnswerPK uaPK = new UserAnswerPK(userId, questionId);
			UserAnswer ua = new UserAnswer(uaPK);
			if (counter >= questionsPerStage) {
				stageId++;
			}
			ua.setStageId(stageId);
			em.merge(ua);
			counter++;
		}
	}

	public Question findQuestion(Integer questionId) {
		return em.find(Question.class, questionId);
	}

	public void deleteUserAnswers(String userId) {
		List<UserAnswer> uaList = findAllUserAnswers(userId);
		for (UserAnswer ua : uaList) {
			em.remove(ua);
		}
	}

}
