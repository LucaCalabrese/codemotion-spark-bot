package com.italtel.chatbot.codemotion.logic.service;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import com.italtel.chatbot.codemotion.logic.entities.User;
import com.italtel.chatbot.codemotion.logic.entities.UserRole;

@Stateless
public class UserServiceBean {

	@PersistenceContext(name = "chatbot-codemotion")
	private EntityManager em;

	@Inject
	public QuestionServiceBean questionBean;

	@Inject
	private ConfigServiceBean configBean;

	public User findUser(String userId) {
		return em.find(User.class, userId);
	}

	public User updateUser(User user) {
		return em.merge(user);
	}

	public User deleteUser(String userId) {
		questionBean.deleteUserAnswers(userId);
		User user = em.find(User.class, userId);
		if (user != null) {
			em.remove(user);
		}
		em.flush();
		return user;
	}

	public User findUserByEmail(String email) {
		User u = null;
		TypedQuery<User> query = em.createNamedQuery("User.findByEmail", User.class);
		query.setParameter("email", email);
		List<User> resultList = query.getResultList();
		if (resultList != null && !resultList.isEmpty()) {
			u = resultList.get(0);
		}
		return u;
	}

	public boolean isAdmin(String userId) {
		Boolean admin = false;
		if (userId != null) {
			UserRole ur = getRole(userId);
			if (ur != null) {
				admin = ur.getAdmin();
			}
		}
		return admin;
	}

	public boolean isMarketing(String userId) {
		UserRole ur = getRole(userId);
		Boolean mrk = ur.getMarketing();
		if (mrk != null) {
			return mrk;
		}
		return false;
	}

	public UserRole getRole(String userId) {
		UserRole role = null;
		if (userId != null) {
			TypedQuery<UserRole> query = em.createNamedQuery("UserRole.findByUserId", UserRole.class);
			query.setParameter("userId", userId);
			List<UserRole> resultList = query.getResultList();
			if (resultList != null && !resultList.isEmpty()) {
				role = resultList.get(0);
			}
		}
		return role;
	}

	public User addNewUser(String userId, String email, String username) {
		User user = new User();
		user.setId(userId);
		user.setStatus("FINISHED");
		user.setTotalScore(0);
		user.setQuestionsAnswered(0);
		user.setEmail(email);
		user.setUsername(username);
		user = updateUser(user);
		int questionsPerStage = Integer.valueOf(configBean.getConfig("QUESTIONS_PER_STAGE"));
		int numberOfStages = Integer.valueOf(configBean.getConfig("NUMBER_OF_STAGES"));
		questionBean.drawQuestions(userId, questionsPerStage, numberOfStages);
		return user;
	}

	public Integer getTotalScoreByUserId(String userId) {
		Integer score = 0;
		User user = findUser(userId);
		if (user != null && user.getTotalScore() != null) {
			score = user.getTotalScore();
		}
		return score;
	}

	public List<User> getTopScorers(int max) {
		List<User> users = new ArrayList<>();
		TypedQuery<User> q = em.createNamedQuery("User.findTopScorers", User.class);
		List<User> resultList = q.setMaxResults(max).getResultList();
		if (resultList != null) {
			users = resultList;
		}
		return users;
	}

	public int getUserCount() {
		return ((Number) em.createNamedQuery("User.countAll").getSingleResult()).intValue();
	}
}
