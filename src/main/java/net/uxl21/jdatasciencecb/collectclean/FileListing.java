package net.uxl21.jdatasciencecb.collectclean;

import java.io.File;
import java.util.Collection;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.TrueFileFilter;

public class FileListing extends CollectCleanRunnable {
	
	public FileListing(String[] args) {
		super(args);
	}
	
	

	@Override
	public void run() {
		Collection<File> files = FileUtils.listFiles(new File(this.configSet.getString("defaultRootDir")), TrueFileFilter.INSTANCE, TrueFileFilter.INSTANCE);
		StringBuffer filesStr = new StringBuffer();
		
		files.forEach(file -> {
			filesStr.append(file.getAbsolutePath()).append("\n");
		});
		
		this.logger.info(filesStr.toString());
	}

	
	
}
