package net.uxl21.jdatasciencecb.collectclean;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.uxl21.jdatasciencecb.ConfigSet;



public abstract class CollectCleanRunnable {
	
	protected Logger logger = LogManager.getLogger();
	
	protected ConfigSet configSet = ConfigSet.getInstance();
	
	
	protected String[] params;
	
	
	
	public CollectCleanRunnable(String[] args) {
		this.params = args;
	}
	
	
	
	public abstract void run();

}
