package net.uxl21.jdatasciencecb.util;

import java.io.FileInputStream;
import java.io.IOException;

import org.mozilla.universalchardet.UniversalDetector;

public class FileEncodingDetector {
	
	
	private String fileName;
	
	
	private byte[] buffer;
	
	

	public FileEncodingDetector(String fileName) {
		this.fileName = fileName;
		this.buffer = new byte[4096];
	}
	
	
	public String detect() {
		UniversalDetector detector = new UniversalDetector(null);
		String encoding;
		
		try (FileInputStream fis = new FileInputStream(this.fileName)) {
			int nread;
			
			while ((nread = fis.read(buffer)) > 0 && !detector.isDone()) {
			  detector.handleData(buffer, 0, nread);
			}

			detector.dataEnd();
			
			encoding = detector.getDetectedCharset();
			
		} catch (IOException e) {
			e.printStackTrace();
			encoding = "UTF-8";
		
		} finally {
			detector.reset();
		}
		
		return encoding;
	}

}
