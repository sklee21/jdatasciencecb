package net.uxl21.jdatasciencecb.collectclean;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;

import net.uxl21.jdatasciencecb.JDSRunnable;

public class ReadFileACIO extends JDSRunnable {

	public ReadFileACIO(String[] args) {
		super(args);
	}
	

	@Override
	public void run() {
		String fileName = this.configSet.getString("fileDir") + File.separator + this.configSet.getString("txtFileToRead");
		File file = new File(fileName);
		
		try {
			String text = FileUtils.readFileToString(file, "UTF-8");
			
			this.logger.debug(text);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
 