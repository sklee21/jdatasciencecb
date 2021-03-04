package net.uxl21.jdatasciencecb.lfd2;

import java.io.File;

import edu.stanford.nlp.classify.Classifier;
import edu.stanford.nlp.classify.ColumnDataClassifier;
import edu.stanford.nlp.classify.Dataset;
import edu.stanford.nlp.classify.GeneralDataset;
import edu.stanford.nlp.ling.Datum;
import edu.stanford.nlp.objectbank.ObjectBank;
import net.uxl21.jdatasciencecb.JDSRunnable;

public class StanfordClassifier extends JDSRunnable {
	
	public static final String CHEESE_PROP_FILE = "cheese2007.prop";
	
	public static final String CHEESE_DISEASE_TRAIN_FILE = "cheeseDisease.train";
	
	public static final String CHEESE_DISEASE_TEST_FILE = "cheeseDisease.test";
	
	
	private String stanfordDataPath = null;
	
	
	private String cheesePropFile = null;
	
	private String cheeseDiseaseTrainFile = null;
	
	private String cheeseDiseaseTestFile = null;
	
	
	

	public StanfordClassifier(String[] args) {
		super(args);
		
		this.stanfordDataPath = this.argsParser.getArgument("stanfordDataPath", this.configSet.getString("defaultStanfordDataPath"));
		this.cheesePropFile = this.stanfordDataPath + File.separator + this.argsParser.getArgument("cheeseDiseaseTrainFile", CHEESE_PROP_FILE);
		this.cheeseDiseaseTrainFile = this.stanfordDataPath + File.separator + this.argsParser.getArgument("cheeseDiseaseTrainFile", CHEESE_DISEASE_TRAIN_FILE);
		this.cheeseDiseaseTestFile = this.stanfordDataPath + File.separator + this.argsParser.getArgument("cheeseDiseaseTrainFile", CHEESE_DISEASE_TEST_FILE);
	}

	@Override
	public void run() {
		ColumnDataClassifier columnDataClassifier = new ColumnDataClassifier(this.cheesePropFile);
		GeneralDataset<String,String> trainDataset = columnDataClassifier.readTrainingExamples(this.cheeseDiseaseTrainFile);
		Classifier<String, String> classifier = columnDataClassifier.makeClassifier(trainDataset);
		
		for( String line : ObjectBank.getLineIterator(this.cheeseDiseaseTestFile, "utf-8") ) {
			Datum<String, String> datum = columnDataClassifier.makeDatumFromLine(line);
			System.out.printf("%s => %s\n", line, classifier.classOf(datum));
		}
	}

}
