package net.uxl21.jdatasciencecb.lfd1;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.Random;

import net.uxl21.jdatasciencecb.JDSRunnable;
import weka.classifiers.Classifier;
import weka.classifiers.Evaluation;
import weka.classifiers.bayes.NaiveBayes;
import weka.classifiers.meta.FilteredClassifier;
import weka.classifiers.trees.RandomForest;
import weka.core.Instances;
import weka.core.SerializationHelper;
import weka.core.converters.ConverterUtils.DataSource;
import weka.filters.unsupervised.attribute.Remove;

public class WekaCV2 extends JDSRunnable {
	
	private String weatherArffPath = null;
	
	private String weatherArffFile = null;
	
	
	public static final String WEATHER_MODEL_FILE = "weather.nominal.model";
	
	
	public static final String WEATHER_ARFF_FILE = "weather.nominal.arff";
	
	public static final String WEATHER_ARFF_TEST_FILE = "weather.nominal-test.arff";
	
	
	public static final String WEATHER_ARFF_OUTPUT_FILE = "weather.nominal-output.arff";
	
	
	

	public WekaCV2(String[] args) {
		super(args);
	}
	

	
	@Override
	public void run() {
		this.weatherArffPath = this.argsParser.getArgument("wekaDataPath", this.configSet.getString("defaultWekaDataPath"));
		this.weatherArffFile = this.weatherArffPath + File.separator + this.argsParser.getArgument("weatherArffFile", WEATHER_ARFF_FILE);
		
		this.generateModel(FilteredClassifier.class);
		this.classify(true);
	}
	
	
	
	
	protected <T> void generateModel(Class<T> clazz) {
		try {
			System.out.println(this.weatherArffPath + File.separator + this.weatherArffFile);
			DataSource dataSource = new DataSource(this.weatherArffFile);
			Instances weatherInstances = dataSource.getDataSet();
			
			if( weatherInstances.classIndex() == -1 ) {
				weatherInstances.setClassIndex(weatherInstances.numAttributes() - 1);
			}
			
			
			Classifier classifier = Classifier.class.cast(clazz.getConstructor().newInstance());
			classifier.buildClassifier(weatherInstances);
			
			SerializationHelper.write(this.weatherArffPath + File.separator + WEATHER_MODEL_FILE, classifier);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	protected void classify(boolean evaluate) {
		try {
			//
			// load model
			Classifier classifier = (Classifier)SerializationHelper.read(this.weatherArffPath + File.separator + WEATHER_MODEL_FILE);
			
			
			//
			// load training file
			BufferedReader reader = new BufferedReader(new FileReader(this.weatherArffFile));
			Instances weatherTrainingInstances = new Instances(reader);
			weatherTrainingInstances.setClassIndex(weatherTrainingInstances.numAttributes() - 1);
			
			
			//
			// load test file
			reader = new BufferedReader(new FileReader(this.weatherArffPath + File.separator + WEATHER_ARFF_TEST_FILE));
			Instances weatherTestInstances = new Instances(reader);
			weatherTestInstances.setClassIndex(weatherTestInstances.numAttributes() - 1);
			
			reader.close();
			
			
			//
			// classify
			classifier.buildClassifier(weatherTrainingInstances);
			
			Instances weatherLabeledInstances = new Instances(weatherTestInstances);
			double clsLabel;
			
			for( int i=0; i<weatherTestInstances.numInstances(); i++ ) {
				clsLabel = classifier.classifyInstance(weatherTestInstances.instance(i));
				weatherLabeledInstances.instance(i).setClassValue(clsLabel);
				
				double[] predictionOutput = classifier.distributionForInstance(weatherTestInstances.instance(i));
				System.out.println("Prediction Probability = " + predictionOutput[1]);
			}
			
			
			//
			// write arff file
			BufferedWriter writer = new BufferedWriter(new FileWriter(this.weatherArffPath + File.separator + WEATHER_ARFF_OUTPUT_FILE));
			writer.write(weatherLabeledInstances.toString());
			writer.close();
			
			
			if( evaluate ) {
				this.logger.debug(this.evaluate(classifier, weatherLabeledInstances));
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	protected void filteredClassify(boolean evaluate) {
		try {
			//
			// load model
			Classifier classifier = (Classifier)SerializationHelper.read(this.weatherArffPath + File.separator + WEATHER_MODEL_FILE);
			
			Remove remove = new Remove();
			remove.setAttributeIndices("1");
			
			FilteredClassifier filteredClassifier = new FilteredClassifier();
			filteredClassifier.setFilter(remove);
			filteredClassifier.setClassifier(classifier);
			
			
			//
			// load training file
			BufferedReader reader = new BufferedReader(new FileReader(this.weatherArffFile));
			Instances weatherTrainingInstances = new Instances(reader);
			weatherTrainingInstances.setClassIndex(weatherTrainingInstances.numAttributes() - 1);
			
			
			//
			// load test file
			reader = new BufferedReader(new FileReader(this.weatherArffPath + File.separator + WEATHER_ARFF_TEST_FILE));
			Instances weatherTestInstances = new Instances(reader);
			weatherTestInstances.setClassIndex(weatherTestInstances.numAttributes() - 1);
			
			reader.close();
			
			
			//
			// classify
			filteredClassifier.buildClassifier(weatherTrainingInstances);
			
			Instances weatherLabeledInstances = new Instances(weatherTestInstances);
			double clsLabel;
			
			for( int i=0; i<weatherTestInstances.numInstances(); i++ ) {
				clsLabel = filteredClassifier.classifyInstance(weatherTestInstances.instance(i));
				weatherLabeledInstances.instance(i).setClassValue(clsLabel);
				
				double[] predictionOutput = filteredClassifier.distributionForInstance(weatherTestInstances.instance(i));
				System.out.println("Prediction Probability = " + predictionOutput[1]);
			}
			
			
			//
			// write arff file
			BufferedWriter writer = new BufferedWriter(new FileWriter(this.weatherArffPath + File.separator + WEATHER_ARFF_OUTPUT_FILE));
			writer.write(weatherLabeledInstances.toString());
			writer.close();
			
			
			if( evaluate ) {
				this.logger.debug(this.evaluate(filteredClassifier, weatherLabeledInstances));
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	protected String evaluate(Classifier classifier, Instances instances) throws Exception {
		Evaluation eval = new Evaluation(instances);
		eval.crossValidateModel(classifier, instances, 10, new Random(1));
		
		return "\n::::::: Evaluation Summary :::::::\n" + eval.toSummaryString();
	}

}
