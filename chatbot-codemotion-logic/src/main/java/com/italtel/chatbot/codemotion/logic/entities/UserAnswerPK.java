package com.italtel.chatbot.codemotion.logic.entities;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the user_answers database table.
 * 
 */
@Embeddable
public class UserAnswerPK implements Serializable {
	// default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name = "user_id")
	private String userId;

	@Column(name = "question_id")
	private Integer questionId;

	public UserAnswerPK() {
	}

	public String getUserId() {
		return this.userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public Integer getQuestionId() {
		return this.questionId;
	}

	public void setQuestionId(Integer questionId) {
		this.questionId = questionId;
	}

	public UserAnswerPK(String userId, Integer questionId) {
		super();
		this.userId = userId;
		this.questionId = questionId;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof UserAnswerPK)) {
			return false;
		}
		UserAnswerPK castOther = (UserAnswerPK) other;
		return this.userId.equals(castOther.userId) && this.questionId.equals(castOther.questionId);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.userId.hashCode();
		hash = hash * prime + this.questionId.hashCode();

		return hash;
	}
}