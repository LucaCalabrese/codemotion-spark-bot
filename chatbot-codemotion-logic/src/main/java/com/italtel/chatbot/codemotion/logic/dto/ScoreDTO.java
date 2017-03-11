package com.italtel.chatbot.codemotion.logic.dto;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class ScoreDTO {

	private Integer rank;
	private Integer score;
	private String name;

	public ScoreDTO() {
		super();
	}

	public Integer getRank() {
		return rank;
	}

	public void setRank(Integer rank) {
		this.rank = rank;
	}

	public Integer getScore() {
		return score;
	}

	public void setScore(Integer score) {
		this.score = score;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
