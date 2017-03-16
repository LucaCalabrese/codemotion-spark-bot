package com.italtel.chatbot.codemotion.logic.entities;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The persistent class for the users database table.
 * 
 */
@Entity
@Table(name = "users")
@NamedQueries({ @NamedQuery(name = "User.findAll", query = "SELECT u FROM User u"),
		@NamedQuery(name = "User.findByEmail", query = "SELECT u FROM User u where u.email = :email"),
		@NamedQuery(name = "User.findTopScorers", query = "SELECT u FROM User u where u.totalScore is not null order by u.totalScore desc") })
public class User implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String id;

	private String email;

	@Column(name = "last_question_id")
	private Integer lastQuestionId;

	@Column(name = "questions_answered")
	private Integer questionsAnswered;

	@Column(name = "stage_id")
	private Integer stageId;

	private String status;

	@Column(name = "total_score")
	private Integer totalScore;

	private String username;

	private String phone;

	public User() {
	}

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Integer getLastQuestionId() {
		return this.lastQuestionId;
	}

	public void setLastQuestionId(Integer lastQuestionId) {
		this.lastQuestionId = lastQuestionId;
	}

	public Integer getQuestionsAnswered() {
		return this.questionsAnswered;
	}

	public void setQuestionsAnswered(Integer questionsAnswered) {
		this.questionsAnswered = questionsAnswered;
	}

	public Integer getStageId() {
		return this.stageId;
	}

	public void setStageId(Integer stageId) {
		this.stageId = stageId;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Integer getTotalScore() {
		return this.totalScore;
	}

	public void setTotalScore(Integer totalScore) {
		this.totalScore = totalScore;
	}

	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

}