package com.italtel.chatbot.codemotion.logic.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;


/**
 * The persistent class for the questions database table.
 * 
 */
@Entity
@Table(name="questions")
@NamedQuery(name="Question.findAll", query="SELECT q FROM Question q")
public class Question implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private Integer id;

	private String ans1;

	private String ans2;

	private String ans3;

	private String ans4;

	@Column(name="correct_ans")
	private Integer correctAns;

	private String text;


	public Question() {
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getAns1() {
		return this.ans1;
	}

	public void setAns1(String ans1) {
		this.ans1 = ans1;
	}

	public String getAns2() {
		return this.ans2;
	}

	public void setAns2(String ans2) {
		this.ans2 = ans2;
	}

	public String getAns3() {
		return this.ans3;
	}

	public void setAns3(String ans3) {
		this.ans3 = ans3;
	}

	public String getAns4() {
		return this.ans4;
	}

	public void setAns4(String ans4) {
		this.ans4 = ans4;
	}

	public Integer getCorrectAns() {
		return this.correctAns;
	}

	public void setCorrectAns(Integer correctAns) {
		this.correctAns = correctAns;
	}

	public String getText() {
		return this.text;
	}

	public void setText(String text) {
		this.text = text;
	}

}