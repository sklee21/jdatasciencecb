package net.uxl21.jdatasciencecb.lfd1;

import java.io.File;

import net.uxl21.jdatasciencecb.JDSRunnable;
import weka.classifiers.functions.LinearRegression;
import weka.classifiers.functions.Logistic;
import weka.core.Instances;
import weka.core.converters.ConverterUtils.DataSource;

public class WekaLR extends JDSRunnable {
	
	private String arffPath = null;
	
	private String arffFile = null;
	
	
	public static final String ARFF_FILE = "iris.arff";
	
	
	
	

	public WekaLR(String[] args) {
		super(args);
	}
	

	@Override
	public void run() {
		this.arffPath = this.argsParser.getArgument("wekaDataPath", this.configSet.getString("defaultWekaDataPath"));
		this.arffFile = this.arffPath + File.separator + this.argsParser.getArgument("arffFile", ARFF_FILE);
		
		try {
			DataSource dataSource = new DataSource(this.arffFile);
			
			Instances iris = dataSource.getDataSet();
			iris.setClassIndex(iris.numAttributes() - 1);
			
			Logistic logistic = new Logistic();
			logistic.buildClassifier(iris);
			
			System.out.println(logistic);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
