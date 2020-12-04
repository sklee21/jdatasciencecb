package net.uxl21.jdatasciencecb.collectclean;


import java.io.*;
import java.util.*;

import net.uxl21.jdatasciencecb.JDSRunnable;


public class TestRecursiveDirectoryTraversal implements JDSRunnable {

	public void run(String[] args) {
		String rootDir;
		
		if( args.length == 0 ) {
			rootDir = "D:\\20_AMAGRAMMING\\JDATASCIENCE\\jdatasciencecb";
		
		} else {
			rootDir = args[0];
		}
		
		System.out.println( listFiles(new File(rootDir)) );
	}
	
	
	public Set<File> listFiles(File dir) {
		Set<File> fileSet = new HashSet<>();
		
		if( dir == null || dir.listFiles() == null ) {
			return fileSet;
		}
		
		for( File fileOrDir : dir.listFiles() ) {
			if( fileOrDir.isFile() ) {
				fileSet.add(fileOrDir);
			} else {
				fileSet.addAll(listFiles(fileOrDir));
			}
		}
		
		return fileSet;
	}
	
}
