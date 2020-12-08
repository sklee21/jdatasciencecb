package net.uxl21.jdatasciencecb;

import java.io.InputStreamReader;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class ConfigSet {
	
	private static ConfigSet instance = null;
	
	
	private JsonElement rootElement = null;
	private JsonObject rootObj = null;
	
	

	/*
	 * Constructor 
	 */
	private ConfigSet() {
    	InputStreamReader inReader = new InputStreamReader(this.getClass().getResourceAsStream("/config/jds-config.json"));    	
    	this.rootElement = JsonParser.parseReader(inReader);
    	this.rootObj = this.rootElement.getAsJsonObject();
	}
	
	
	public static ConfigSet getInstance() {
		if( instance == null ) {
			instance = new ConfigSet();
		}
		
		return instance;
	}
	
	
	public String getString(String property) {
		return this.rootObj.get(property).getAsString();
	}
	
	
	public String getString(String property, String defaultValue) {
		String value = this.rootObj.get(property).getAsString();
		
		return value == null ? defaultValue : value;
	}
	
}
