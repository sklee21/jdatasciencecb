package net.uxl21.jdatasciencecb;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.uxl21.jdatasciencecb.util.ArgsParser;



public abstract class JDSRunnable {
	
	protected Logger logger = LogManager.getLogger();
	
	protected ConfigSet configSet = ConfigSet.getInstance();
	
	protected ArgsParser argsParser = null;
	
	
	protected String[] params;
	
	
	
	public JDSRunnable(String[] args) {
		this.params = args;
		this.argsParser = new ArgsParser(this.params);
		this.argsParser.parse();
	}
	
	
	
	public abstract void run();

}
