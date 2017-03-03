package com.italtel.chatbot.clients.ciscospark.logic.service;

import java.util.HashMap;
import java.util.Map;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.italtel.chatbot.clients.ciscospark.logic.entities.SparkConfig;

@Stateless
public class SparkConfigServiceBean {
	private static Map<String, String> cache = new HashMap<String, String>();

	@PersistenceContext(name = "chatbot-clients-ciscospark")
	private EntityManager em;

	public String getConfig(String key) {
		if (cache.containsKey(key)) {
			return cache.get(key);
		}
		String result = null;
		SparkConfig configDB = em.find(SparkConfig.class, key);
		if (configDB != null) {
			result = configDB.getValue();
			cache.put(key, result);
		}
		return result;
	}

	public void emptyCache() {
		for (String key : cache.keySet()) {
			cache.remove(key);
		}
	}

}
