package net.uxl21.jdatasciencecb.lfd2;

import java.io.File;
import java.io.IOException;

import net.sf.javaml.core.Dataset;
import net.sf.javaml.tools.data.FileHandler;
import net.uxl21.jdatasciencecb.JDSRunnable;

public class JavaML extends JDSRunnable {
	
	public static final String IRIS_DATA_FILE = "iris.data";
	
	public static final String IRIS_OUTPUT_FILE = "iris-output.txt";
	
	private String jmlDataPath = null;
	
	private String irisDataFile = null;
	
	

	public JavaML(String[] args) {
		super(args);
		
		this.jmlDataPath = this.argsParser.getArgument("jmlDataPath", this.configSet.getString("defaultJmlDataPath"));
		this.irisDataFile = this.jmlDataPath + File.separator + this.argsParser.getArgument("irisArffFile", IRIS_DATA_FILE);
	}
	

	@Override
	public void run() {
		try {
			Dataset data = FileHandler.loadDataset(new File(this.irisDataFile), 4, ",");
			System.out.printf("DATA => %s\n", data.toString());
			
			FileHandler.exportDataset(data, new File(this.jmlDataPath + File.separator + IRIS_OUTPUT_FILE));
			data = FileHandler.loadDataset(new File(this.jmlDataPath + File.separator + IRIS_OUTPUT_FILE), 0, "\t");
			System.out.printf("OUTPUT => %s\n", data.toString());
			
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
