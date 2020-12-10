package net.uxl21.jdatasciencecb.collectclean;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

import net.uxl21.jdatasciencecb.JDSRunnable;
import net.uxl21.jdatasciencecb.util.FileEncodingDetector;

public class TextCleaner extends JDSRunnable {

	public TextCleaner(String[] args) {
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
			
			String contents = filesStr.toString();
			
			this.logger.info("\n>>>>>>>>>>>>>>>>>>>>>>>> ORIGINAL: => \n" + contents);
			this.logger.info("\n>>>>>>>>>>>>>>>>>>>>>>>> CLEANED : => \n" + this.cleanning(contents));
			
			
		} catch (IOException e) {
			this.logger.error(e);
			e.printStackTrace();
		} 
	}
	
	
	public String cleanning(String text) {
		text = text.replaceAll("[^p{ASCII}]", "");
		text = text.replaceAll("s+", " ");
		text = text.replaceAll("p{Cntrl}", "");
		text = text.replaceAll("[^p{Print}]", "");
		text = text.replaceAll("p{C}", "");
		
		return text;
	}

}
