package net.uxl21.jdatasciencecb.lfd1;

import java.io.File;

import net.uxl21.jdatasciencecb.JDSRunnable;
import weka.classifiers.meta.FilteredClassifier;
import weka.classifiers.trees.RandomForest;
import weka.core.Instances;
import weka.core.converters.ConverterUtils.DataSource;
import weka.filters.unsupervised.attribute.Remove;

public class WekaFC extends JDSRunnable {
	
	private String weatherArffPath = null;
	
	private String weatherArffFile = null;
	
	
	public static final String WEATHER_MODEL_FILE = "weather.nominal.model";
	
	
	public static final String WEATHER_ARFF_FILE = "weather.nominal.arff";
	
	public static final String WEATHER_ARFF_TEST_FILE = "weather.nominal-test.arff";
	
	
	public static final String WEATHER_ARFF_OUTPUT_FILE = "weather.nominal-output.arff";
	
	
	

	public WekaFC(String[] args) {
		super(args);
	}
	

	
	@Override
	public void run() {
		this.weatherArffPath = this.argsParser.getArgument("wekaDataPath", this.configSet.getString("defaultWekaDataPath"));
		this.weatherArffFile = this.weatherArffPath + File.separator + this.argsParser.getArgument("weatherArffFile", WEATHER_ARFF_FILE);
		
		try {
			DataSource dataSource = new DataSource(this.weatherArffFile);
			Instances weatherInstances = dataSource.getDataSet();
			weatherInstances.setClassIndex(weatherInstances.numAttributes() - 1);
			
			RandomForest randomForest = new RandomForest();
			
			Remove remove = new Remove();
			remove.setAttributeIndices("1");
			
			FilteredClassifier classifier = new FilteredClassifier();
			classifier.setFilter(remove);
			classifier.setClassifier(randomForest);
			classifier.buildClassifier(weatherInstances);
			
			for( int i=0; i<weatherInstances.numInstances(); i++  ) {
				double pred = classifier.classifyInstance(weatherInstances.instance(i));
				System.out.print("Given value    : " + weatherInstances.classAttribute().value((int)weatherInstances.instance(i).classValue()));
				System.out.println(", \tPredicted value: " + weatherInstances.classAttribute().value((int)pred));
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
