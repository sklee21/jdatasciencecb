package net.uxl21.jdatasciencecb.collectclean;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

import net.uxl21.jdatasciencecb.JDSRunnable;
import net.uxl21.jdatasciencecb.util.FileEncodingDetector;
import net.uxl21.jdatasciencecb.util.TextEncoder;

public class ReadFile extends JDSRunnable {
	
	public ReadFile(String[] args) {
		super(args);
	}
	
	

	@Override
	public void run() {
		String fileName = this.configSet.getString("fileDir") + File.separator;
		
		if( this.params.length > 0 ) {
			fileName += this.params[0];
		} else {
			fileName += this.configSet.getString("txtFileToRead");
		}
		
		String encoding = new FileEncodingDetector(fileName).detect();
		this.logger.debug("fileName = " + fileName + ", (" + encoding + ")");
		
		try( Stream<String> stream = Files.lines(Paths.get(fileName), Charset.forName(encoding)) ) {
			StringBuffer filesStr = new StringBuffer();
			
			stream.forEach(str -> {
				filesStr.append(str).append("\n");
			});
			
			this.logger.info("\n" + new TextEncoder(filesStr.toString()).encode("UTF-8"));
			
		} catch (IOException e) {
			this.logger.error(e);
			e.printStackTrace();
		} 
	}

}
