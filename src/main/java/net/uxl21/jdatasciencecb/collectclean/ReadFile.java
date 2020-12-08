package net.uxl21.jdatasciencecb.collectclean;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

public class ReadFile extends CollectCleanRunnable {
	
	public ReadFile(String[] args) {
		super(args);
	}
	
	

	@Override
	public void run() {
		String fileName = this.configSet.getString("defaultFileDir") + File.separator + this.configSet.getString("defaultFileToRead");
		
		try( Stream<String> stream = Files.lines(Paths.get(fileName)) ) {
			StringBuffer filesStr = new StringBuffer();
			
			stream.forEach(str -> {
				filesStr.append(str).append("\n");
			});
			
			this.logger.info("\n" + filesStr.toString());
			
		} catch (IOException e) {
			this.logger.error(e);
			e.printStackTrace();
		} 
	}

}
