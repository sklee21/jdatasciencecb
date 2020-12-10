package net.uxl21.jdatasciencecb;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;



public abstract class JDSRunnable {
	
	protected Logger logger = LogManager.getLogger();
	
	protected ConfigSet configSet = ConfigSet.getInstance();
	
	
	protected String[] params;
	
	
	
	public JDSRunnable(String[] args) {
		this.params = args;
	}
	
	
	
	public abstract void run();

}
