package net.uxl21.jdatasciencecb.util;

import java.util.HashMap;

public class ArgsParser {
	
	
	private String[] args;
	
	private HashMap<String, String> argsMap;
	
	

	public ArgsParser(String[] args) {
		this.args = args;
		this.argsMap = new HashMap<>();
	}
	

	
	public void parse() {
		String argName;
		String argValue;
		
		for( int i=0; i<this.args.length; i++ ) {
			argName = argValue = null;
			
			if( this.args[i].startsWith("-") && this.args[i].length() > 1 ) {
				argName = this.args[i].substring(1);
				
				if( i + 1 < this.args.length ) {
					argValue = this.args[i + 1];
				}
				
				this.argsMap.put(argName, argValue);
			}
		}
	}
	
	
	public String getArgument(String name) {
		return this.getArgument(name, "");
	}
	
	
	public String getArgument(String name, String defaultValue) {
		String value = this.argsMap.get(name);
		value = value == null ? defaultValue : value;
		
		return value;
	}

}
