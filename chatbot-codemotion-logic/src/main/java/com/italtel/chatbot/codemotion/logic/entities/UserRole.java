package com.italtel.chatbot.codemotion.logic.entities;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The persistent class for the user_roles database table.
 * 
 */
@Entity
@Table(name = "user_roles")
@NamedQueries({ @NamedQuery(name = "UserRole.findAll", query = "SELECT u FROM UserRole u"),
		@NamedQuery(name = "UserRole.findByUserId", query = "SELECT u FROM UserRole u where u.userId = :userId") })
public class UserRole implements Serializable {
	private static final long serialVersionUID = 1L;

	private Boolean admin;

	@Id
	private Integer id;

	private Boolean marketing;

	@Column(name = "user_id")
	private String userId;

	public UserRole() {
	}

	public Boolean getAdmin() {
		return this.admin;
	}

	public void setAdmin(Boolean admin) {
		this.admin = admin;
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Boolean getMarketing() {
		return this.marketing;
	}

	public void setMarketing(Boolean marketing) {
		this.marketing = marketing;
	}

	public String getUserId() {
		return this.userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

}