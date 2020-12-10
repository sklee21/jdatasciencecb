package net.uxl21.jdatasciencecb.collectclean;

import java.io.File;
import java.io.FileReader;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import net.uxl21.jdatasciencecb.JDSRunnable;

public class JSONReader extends JDSRunnable {

	public JSONReader(String[] args) {
		super(args);
	}

	@Override
	public void run() {
		String fileName = this.configSet.getString("fileDir") + File.separator;
		
		if( this.params.length > 0 ) {
			fileName += this.params[0];
		} else {
			fileName += this.configSet.getString("jsonFileToRead");
		}
		
		JSONParser parser = new JSONParser();
		
		try {
			Object obj = parser.parse(new FileReader(fileName));
			JSONObject jsonObject = (JSONObject)obj;
			
			String name = (String)jsonObject.get("book");
			this.logger.info("Book: " + name);
			
			String author = (String)jsonObject.get("author");
			this.logger.info("Author: " + author);
			
			JSONArray reviews = (JSONArray)jsonObject.get("messages");
			reviews.forEach(review -> {
				this.logger.info("\t" + review);
			});
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
