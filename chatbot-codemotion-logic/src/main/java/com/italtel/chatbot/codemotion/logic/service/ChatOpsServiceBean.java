package com.italtel.chatbot.codemotion.logic.service;

import javax.ejb.Stateless;
import javax.inject.Inject;

import com.italtel.chatbot.codemotion.logic.entities.User;

@Stateless
public class ChatOpsServiceBean {

	@Inject
	private UserServiceBean userBean;

	public String cleanUserHistory(String userId, String userEmail) {
		// Clean given user
		String responseText = null;
		if (userBean.isAdmin(userId)) {
			User user = userBean.findUserByEmail(userEmail);
			if (user != null) {
				userBean.deleteUser(user.getId());
				responseText = "Successfully deleted history for user ".concat(userEmail);
			} else {
				responseText = "User not found";
			}
		}
		return responseText;
	}
}
