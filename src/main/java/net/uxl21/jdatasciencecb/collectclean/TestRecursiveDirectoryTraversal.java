package net.uxl21.jdatasciencecb.collectclean;


import java.io.File;
import java.util.HashSet;
import java.util.Set;

import net.uxl21.jdatasciencecb.ConfigSet;


public class TestRecursiveDirectoryTraversal extends CollectCleanRunnable {
	
	
	public TestRecursiveDirectoryTraversal(String[] args) {
		super(args);
	}
	
	
	@Override
	public void run() {
		Set<File> files = this.listFiles(new File(this.configSet.getString("defaultRootDir")));
		StringBuffer filesStr = new StringBuffer();
		
		files.forEach(file -> {
			filesStr.append(file.getAbsolutePath()).append("\n");
		});
		
		this.logger.info(filesStr.toString());
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
