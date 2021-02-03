package net.uxl21.jdatasciencecb.lfd1;

import java.io.File;

import net.uxl21.jdatasciencecb.JDSRunnable;
import weka.classifiers.functions.LinearRegression;
import weka.core.Instances;
import weka.core.converters.ConverterUtils.DataSource;

public class WekaLC extends JDSRunnable {
	
	private String arffPath = null;
	
	private String arffFile = null;
	
	
	public static final String ARFF_FILE = "cpu.arff";
	
	
	
	

	public WekaLC(String[] args) {
		super(args);
	}
	

	@Override
	public void run() {
		this.arffPath = this.argsParser.getArgument("wekaDataPath", this.configSet.getString("defaultWekaDataPath"));
		this.arffFile = this.arffPath + File.separator + this.argsParser.getArgument("arffFile", ARFF_FILE);
		
		try {
			DataSource dataSource = new DataSource(this.arffFile);
			
			Instances cpu = dataSource.getDataSet();
			cpu.setClassIndex(cpu.numAttributes() - 1);
			
			LinearRegression linearRegression = new LinearRegression();
			linearRegression.buildClassifier(cpu);
			
			System.out.println(linearRegression);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
