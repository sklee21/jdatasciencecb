package net.uxl21.jdatasciencecb;

/**
 * Hello world!
 *
 */
public class App {
	
    public static void main(String[] args) {
    	String classToRun;
    	
    	if( args.length == 0 ) {
    		classToRun = "App";
    		
    	} else {
    		classToRun = args[0];
    	}
    	
        System.out.println( "Hello World! : " + classToRun );
    }
    
}
