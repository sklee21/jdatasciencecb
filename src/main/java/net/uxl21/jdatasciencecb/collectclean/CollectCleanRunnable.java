package net.uxl21.jdatasciencecb.collectclean;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.uxl21.jdatasciencecb.ConfigSet;



public abstract class CollectCleanRunnable {
	
	protected Logger logger = LogManager.getLogger();
	
	protected ConfigSet configSet = ConfigSet.getInstance();
	
	private String rootDir = null;
	
	
	
	public CollectCleanRunnable(String[] args) {
		if( args.length == 0 ) {
			this.rootDir = ConfigSet.getInstance().getString("defaultRootDir");
		
		} else {
			this.rootDir = args[0];
		}
	}
	
	
	
	public abstract void run();
	
	
	
	public String getRootDir() {
		return this.rootDir;
	}

}
