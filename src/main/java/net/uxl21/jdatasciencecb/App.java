package net.uxl21.jdatasciencecb;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;

/**
 * Hello world!
 *
 */
public class App {
	
	public static final String DEFAULT_PACKAGE = "net.uxl21.jdatasciencecb.";
	
	
    public static void main(String[] args) {
    	String classToRun = DEFAULT_PACKAGE;
    	String[] params;
    	
    	if( args.length == 0 ) {
    		classToRun += "collectclean.TestRecursiveDirectoryTraversal";
    		params = new String[0];
    		
    	} else if ( args.length == 1 ) {
    		classToRun += args[0];
    		params = new String[0];
    		
    	} else {
    		classToRun += args[0];
   			params = Arrays.copyOfRange(args, 1, args.length - 1);
    	}
    	
        System.out.println( "Hello World! : " + classToRun );
        
        try {
        	 Constructor constructor = Class.forName(classToRun).getConstructor(null);
        	 System.out.println( "Constructor : " + constructor );
        	 
        	 if( constructor != null ) {
        		 
        		 ((JDSRunnable)constructor.newInstance(null)).run( params );
        	 }
			
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
			
		} catch (SecurityException e) {
			e.printStackTrace();
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			
		} catch (InstantiationException e) {
			e.printStackTrace();
			
		} catch (IllegalAccessException e) {
			e.printStackTrace();
			
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
			
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
    }
    
}
