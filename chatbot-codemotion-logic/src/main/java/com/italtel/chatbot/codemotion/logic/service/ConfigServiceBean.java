package com.italtel.chatbot.codemotion.logic.service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.italtel.chatbot.codemotion.logic.entities.BotConfig;

@Stateless
public class ConfigServiceBean {

	private static Map<String, String> cache = new ConcurrentHashMap<String, String>();

	@PersistenceContext(name = "chatbot-codemotion")
	private EntityManager em;

	public String getConfig(String key) {
		if (cache.containsKey(key)) {
			return cache.get(key);
		}
		String result = null;
		BotConfig configDB = em.find(BotConfig.class, key);
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
