package io.traderx.darkness.config;

import java.io.FileInputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class ConfigLoader {

	public static String CONFIG_PATH = "";
	
	private static Map<String, String> configs = null;
	
	private static ConfigLoader instance = null;
	
	private ConfigLoader() {}
	
	public static ConfigLoader getInstance() {
		if(instance == null) {
			instance = new ConfigLoader();
		}
		return instance;
	}
	
	
	private static void init() {
		try {
			Properties properties = new Properties();
			configs = new HashMap<String, String>();
			properties.load(new FileInputStream(CONFIG_PATH));
			for (Object key : properties.keySet()) {
				configs.put((String)key, properties.getProperty((String)key));
			}
			
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	public static String get(String key) {
		if(configs == null) {
			init();
		}
		return configs.get(key);
	}
}