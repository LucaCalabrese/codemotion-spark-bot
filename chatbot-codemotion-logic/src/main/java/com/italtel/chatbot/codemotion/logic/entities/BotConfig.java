package com.italtel.chatbot.codemotion.logic.entities;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the bot_config database table.
 * 
 */
@Entity
@Table(name="bot_config")
@NamedQuery(name="BotConfig.findAll", query="SELECT b FROM BotConfig b")
public class BotConfig implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String key;

	private String value;

	public BotConfig() {
	}

	public String getKey() {
		return this.key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getValue() {
		return this.value;
	}

	public void setValue(String value) {
		this.value = value;
	}

}