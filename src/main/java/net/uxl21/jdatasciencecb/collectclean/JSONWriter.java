package net.uxl21.jdatasciencecb.collectclean;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class JSONWriter extends CollectCleanRunnable {

	public JSONWriter(String[] args) {
		super(args);
	}

	@Override
	public void run() {
		String fileName = this.configSet.getString("fileDir") + File.separator;
		
		if( this.params.length > 0 ) {
			fileName += this.params[0];
		} else {
			fileName += this.configSet.getString("jsonFileToWrite");
		}
		
		JSONObject obj = new JSONObject();
		obj.put("book", "Harry Potter and the Philosopher's Stone");
		obj.put("author", "J. K. Rowlong");
		
		JSONArray list = new JSONArray();
		list.add("Good 1");
		list.add("Good 2");
		list.add("Good 3");
		
		obj.put("messages", list);
		
		
		try (FileWriter fileWriter = new FileWriter(fileName);) {
			String contents = obj.toJSONString();
			fileWriter.write(contents);
			fileWriter.flush();
			
			this.logger.debug(contents);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
