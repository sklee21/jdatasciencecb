package net.uxl21.jdatasciencecb.lfd1;

import java.io.File;
import java.util.Random;

import net.uxl21.jdatasciencecb.JDSRunnable;
import net.uxl21.jdatasciencecb.util.ArgsParser;
import weka.classifiers.Evaluation;
import weka.classifiers.bayes.NaiveBayes;
import weka.core.Instances;
import weka.core.SerializationHelper;
import weka.core.converters.ConverterUtils.DataSource;

public class WekaCV extends JDSRunnable {
	
	protected ArgsParser argsParser;
	
	

	public WekaCV(String[] args) {
		super(args);
		
		this.argsParser = new ArgsParser(args);
		this.argsParser.parse();
	}
	

	@Override
	public void run() {
		String irisArffPath = this.argsParser.getArgument("wekaDataPath", this.argsParser.getArgument("defaultWekaDataPath"));
		String irisArffFile = irisArffPath + File.separator + this.argsParser.getArgument("irisArffFile", "iris.arff");
		
		DataSource source = null;
		Instances iris = null;
		NaiveBayes nb = null;
		
		try {
			//
			// load arff
			source = new DataSource(irisArffFile);
			
			iris = source.getDataSet();
			
			if( iris.classIndex() == -1 ) {
				iris.setClassIndex(iris.numAttributes() - 1);
			}
			
			
			//
			//  generate model
			nb = new NaiveBayes();
			nb.buildClassifier(iris);
			
			
			//
			// save model
			SerializationHelper.write(irisArffPath + File.separator + "iris_nb.model", nb);
			
			
			//
			// cross-validation
			Evaluation eval = new Evaluation(iris);
			eval.crossValidateModel(nb, iris, 10, new Random(1));
			
			this.logger.info("\n::::::: Evaluation Summary :::::::\n" + eval.toSummaryString());
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
