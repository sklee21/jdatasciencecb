package net.uxl21.jdatasciencecb.collectclean;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.AutoDetectParser;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.sax.BodyContentHandler;

public class TikaReader extends CollectCleanRunnable {

	public TikaReader(String[] args) {
		super(args);
	}
	

	@Override
	public void run() {
		String fileName = this.configSet.getString("fileDir") + File.separator + this.configSet.getString("pdfFileToRead");
		
		try (InputStream stream = new FileInputStream(fileName)) {
			AutoDetectParser parser = new AutoDetectParser();
			BodyContentHandler handler = new BodyContentHandler(-1);
			Metadata metadata = new Metadata();
			
			parser.parse(stream, handler, metadata, new ParseContext());
			
			this.logger.info(handler.toString());
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}	
	
	

}
