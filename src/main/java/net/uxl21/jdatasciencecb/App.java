package net.uxl21.jdatasciencecb;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


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
		this.logger.debug("");
		this.logger.debug("xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx: ");
		this.logger.debug(args.length + " program param(s)");
		Arrays.stream(args).forEach(this.logger::debug);
		
    	if( args.length == 0 ) {
    		this.classToRun += this.configSet.getString("defaultClassToRun");
    		this.params = new String[0];
    		
    	} else if ( args.length == 1 ) {
    		this.classToRun += args[0];
    		this.params = new String[0];
    		
    	} else {
    		this.classToRun += args[0];
    		this.params = Arrays.copyOfRange(args, 1, args.length);
    	}

    	this.logger.debug("");
		this.logger.debug(this.params.length + " application param(s)");
		Arrays.stream(this.params).forEach(this.logger::debug);
		
    	this.logger.debug("xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx\n");
	}
	
	
	public void run() {
		JDSRunnable jdsRun = null;
		
        try {
        	this.logger.debug("");
        	this.logger.debug("=====================  START   =====================");
        	this.logger.debug( "Ready to run " + this.classToRun + "..." );
        	
			Constructor<?> constructor = Class.forName(this.classToRun).getDeclaredConstructor(new Class[] { String[].class });
        	 
			if( constructor != null ) {
				jdsRun = (JDSRunnable)constructor.newInstance(new Object[] { this.params });
        		jdsRun.run();
        	 }
			
		} catch (Exception e) {
			this.logger.error(e);
			e.printStackTrace();
		}
        
        this.logger.debug("===================== FINISHED =====================\n");
	}
	
	
    public static void main(String[] args) throws InterruptedException {
    	App app = new App(args);
    	app.run();
    }
    
}
