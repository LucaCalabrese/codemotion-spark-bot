package com.italtel.chatbot.clients.ciscospark.logic.entities;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the spark_config database table.
 * 
 */
@Entity
@Table(name="spark_config")
@NamedQuery(name="SparkConfig.findAll", query="SELECT s FROM SparkConfig s")
public class SparkConfig implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String key;

	private String value;

	public SparkConfig() {
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