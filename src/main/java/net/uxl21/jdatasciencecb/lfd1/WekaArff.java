package net.uxl21.jdatasciencecb.lfd1;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import net.uxl21.jdatasciencecb.JDSRunnable;
import net.uxl21.jdatasciencecb.util.ArgsParser;
import weka.core.Attribute;
import weka.core.DenseInstance;
import weka.core.Instances;

public class WekaArff extends JDSRunnable {
	
	protected ArgsParser argsParser;
	
	

	public WekaArff(String[] args) {
		super(args);
		
		this.argsParser = new ArgsParser(args);
		this.argsParser.parse();
	}
	

	@Override
	public void run() {
		BufferedWriter writer = null;
		
		try {
			//
			// create attributes
			ArrayList<Attribute> attributes = new ArrayList<>();
			
			// numeric
			attributes.add(new Attribute("age"));
			
			// string
			ArrayList<String> empty = null;
			attributes.add(new Attribute("name", empty));
			
			// date
			attributes.add(new Attribute("dob", "yyyy-MM-dd"));
			
			
			//
			//
			ArrayList<String> classVals = new ArrayList<>();
			
			for(int i=0; i<5; i++) {
				classVals.add("class" + (i + 1));
			}
			
			attributes.add(new Attribute("class", classVals));
			
			
			//
			// creates data
			Instances data = new Instances("MyRelation", attributes, 0);
			
			// data #1
			double[] values = new double[] {
				35,
				data.attribute(1).addStringValue("John Price"),
				data.attribute(2).parseDate("1981-01-16"),
				classVals.indexOf("class3")
			};
			
			data.add(new DenseInstance(1.0, values));
			
			
			// data #2
			values = new double[] {
				30,
				data.attribute(1).addStringValue("Harry Potter"),
				data.attribute(2).parseDate("1986-07-05"),
				classVals.indexOf("class1")
			};
			
			data.add(new DenseInstance(1.0, values));
			
			
			// create arff
			String arffFilePath = this.argsParser.getArgument("wekaDataPath", this.configSet.getString("defaultWekaDataPath")) + 
					              File.separator + this.argsParser.getArgument("arffFile", "training1.arff");
			
			writer = new BufferedWriter(new FileWriter(arffFilePath));
			writer.write(data.toString());
			writer.close();
			
			this.logger.info("@data =>\n " + data);
			
		} catch (Exception e) {
			this.logger.error(e);
		
		} finally {
			if(writer != null) {
				try {
					writer.close();
				} catch (IOException e) {
					this.logger.error(e);
				}
			}
		}
		
	}

}
