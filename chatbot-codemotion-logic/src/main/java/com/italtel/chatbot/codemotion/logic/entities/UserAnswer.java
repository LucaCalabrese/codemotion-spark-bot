package com.italtel.chatbot.codemotion.logic.entities;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The persistent class for the user_answers database table.
 * 
 */
@Entity
@Table(name = "user_answers")
@NamedQueries({ @NamedQuery(name = "UserAnswer.findAll", query = "SELECT u FROM UserAnswer u"),
		@NamedQuery(name = "UserAnswer.findByUserId", query = "SELECT u FROM UserAnswer u where u.id.userId = :userId order by u.seq asc") })
public class UserAnswer implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private UserAnswerPK id;

	private Boolean answered;

	@Column(name = "ans_given")
	private Integer ansGiven;

	private Boolean correct;

	private Integer score;

	@Column(name = "stage_id")
	private Integer stageId;

	private Integer seq;

	public UserAnswer() {
	}

	public UserAnswer(UserAnswerPK id) {
		this.id = id;
	}

	public UserAnswerPK getId() {
		return this.id;
	}

	public void setId(UserAnswerPK id) {
		this.id = id;
	}

	public Integer getAnsGiven() {
		return this.ansGiven;
	}

	public void setAnsGiven(Integer ansGiven) {
		this.ansGiven = ansGiven;
	}

	public Boolean getCorrect() {
		return this.correct;
	}

	public void setCorrect(Boolean correct) {
		this.correct = correct;
	}

	public Integer getScore() {
		return this.score;
	}

	public void setScore(Integer score) {
		this.score = score;
	}

	public Integer getStageId() {
		return this.stageId;
	}

	public void setStageId(Integer stageId) {
		this.stageId = stageId;
	}

	public Boolean getAnswered() {
		return answered;
	}

	public void setAnswered(Boolean answered) {
		this.answered = answered;
	}

	public Integer getSeq() {
		return seq;
	}

	public void setSeq(Integer seq) {
		this.seq = seq;
	}

}