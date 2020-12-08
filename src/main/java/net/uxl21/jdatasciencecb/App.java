package net.uxl21.jdatasciencecb;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.uxl21.jdatasciencecb.collectclean.CollectCleanRunnable;


/**
 * Hello world!
 *
 */
public class App {
	
	public static final String DEFAULT_PACKAGE = "net.uxl21.jdatasciencecb.";
	
	
	private Logger logger = LogManager.getLogger();
	
	private ConfigSet configSet = ConfigSet.getInstance();
	
	
	private String classToRun = DEFAULT_PACKAGE;
	
	private String[] params;
	
	
	
	/**
	 * Constructor
	 */
	public App(String[] args) {
    	if( args.length == 0 ) {
    		this.classToRun += this.configSet.getString("defaultClassToRun");
    		this.params = new String[0];
    		
    	} else if ( args.length == 1 ) {
    		this.classToRun += args[0];
    		this.params = new String[0];
    		
    	} else {
    		this.classToRun += args[0];
    		this.params = Arrays.copyOfRange(args, 1, args.length - 1);
    	}
	}
	
	
	public void run() {
        try {
        	this.logger.debug( "Ready to run " + this.classToRun + "..." );
        	
			Constructor<?> constructor = Class.forName(this.classToRun).getDeclaredConstructor(new Class[] { String[].class });
        	 
			if( constructor != null ) {
				this.logger.debug("=======  START   =======");
        		 
        		((CollectCleanRunnable)constructor.newInstance(new Object[] { this.params })).run();
        		 
        		this.logger.debug("======= FINISHED =======");
        	 }
			
		} catch (NoSuchMethodException e) {
			this.logger.error(e);
			e.printStackTrace();
			
		} catch (SecurityException e) {
			this.logger.error(e);
			e.printStackTrace();
			
		} catch (ClassNotFoundException e) {
			this.logger.error(e);
			e.printStackTrace();
			
		} catch (InstantiationException e) {
			this.logger.error(e);
			e.printStackTrace();
			
		} catch (IllegalAccessException e) {
			this.logger.error(e);
			e.printStackTrace();
			
		} catch (IllegalArgumentException e) {
			this.logger.error(e);
			e.printStackTrace();
			
		} catch (InvocationTargetException e) {
			this.logger.error(e);
			e.printStackTrace();
		}
	}
	
	
    public static void main(String[] args) throws InterruptedException {
    	App app = new App(args);
    	app.run();
    }
    
}
