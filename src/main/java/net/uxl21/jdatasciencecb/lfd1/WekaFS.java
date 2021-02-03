package net.uxl21.jdatasciencecb.lfd1;

import java.io.File;
import java.util.Random;

import net.uxl21.jdatasciencecb.JDSRunnable;
import weka.attributeSelection.AttributeSelection;
import weka.attributeSelection.BestFirst;
import weka.attributeSelection.CfsSubsetEval;
import weka.classifiers.Evaluation;
import weka.classifiers.bayes.NaiveBayes;
import weka.classifiers.meta.AttributeSelectedClassifier;
import weka.core.Instances;
import weka.core.Utils;
import weka.core.converters.ConverterUtils.DataSource;
import weka.filters.Filter;

public class WekaFS extends JDSRunnable {
	
	public static final String ARFF_FILE = "iris.arff";
	
	
	
	private String arffPath = null;
	
	private String arffFile = null;
	
	
	private Instances iris = null;
	
	

	public WekaFS(String[] args) {
		super(args);
		
		this.arffPath = this.argsParser.getArgument("wekaDataPath", this.configSet.getString("defaultWekaDataPath"));
		this.arffFile = this.arffPath + File.separator + this.argsParser.getArgument("arffFile", ARFF_FILE);
	}
	

	@Override
	public void run() {
		try {
			//
			// select features
			this.iris = new DataSource(this.arffFile).getDataSet();
			this.iris.setClassIndex(this.iris.numAttributes() - 1);
			
			this.selectFeatures();
			this.selectFeaturesWithFilter();
			this.selectFeaturesWithClassifiers();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	protected void selectFeatures() throws Exception {
		//
		// select features
		DataSource dataSource = new DataSource(this.arffFile);
		Instances iris = dataSource.getDataSet();
		iris.setClassIndex(iris.numAttributes() - 1);
		
		CfsSubsetEval eval = new CfsSubsetEval();
		BestFirst search = new BestFirst();
		AttributeSelection attrSel = new AttributeSelection();
		attrSel.setEvaluator(eval);
		attrSel.setSearch(search);
		attrSel.SelectAttributes(iris);
		
		int[] attrIndex = attrSel.selectedAttributes();
		System.out.println(Utils.arrayToString(attrIndex));
	}
	
	
	
	protected void selectFeaturesWithFilter() throws Exception {
		//
		// select features with filter
		CfsSubsetEval eval = new CfsSubsetEval();
		BestFirst search = new BestFirst();

		weka.filters.supervised.attribute.AttributeSelection filter = new weka.filters.supervised.attribute.AttributeSelection();
		filter.setEvaluator(eval);
		filter.setSearch(search);
		filter.setInputFormat(this.iris);
		
		Instances newData = Filter.useFilter(this.iris, filter);
		System.out.println(newData);
	}
	
	
	
	protected void selectFeaturesWithClassifiers() throws Exception {
		CfsSubsetEval eval = new CfsSubsetEval();
		BestFirst search = new BestFirst();
		NaiveBayes naiveBayes = new NaiveBayes();
		
		AttributeSelectedClassifier classifier = new AttributeSelectedClassifier();
		classifier.setClassifier(naiveBayes);
		classifier.setEvaluator(eval);
		classifier.setSearch(search);
		
		Evaluation evaluation = new Evaluation(this.iris);
		evaluation.crossValidateModel(classifier, this.iris, 10, new Random(1));
		System.out.println(evaluation.toSummaryString());
	}

}
