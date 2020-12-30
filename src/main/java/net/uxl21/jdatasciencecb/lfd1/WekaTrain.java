package net.uxl21.jdatasciencecb.lfd1;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import net.uxl21.jdatasciencecb.JDSRunnable;
import weka.classifiers.bayes.NaiveBayes;
import weka.core.Instances;
import weka.core.SerializationHelper;


/**
 * -Dexec.args="lfd1.WekaTrain 
 * 	-wekaDataPath D:\\20_AMAGRAMMING\\JDATASCIENCE\\jdatasciencecb\\src\\main\\resources\\weka 
 * 	-model iris_nb.model
 * 	-training iris.arff
 * 	-testing iris-test.arff
 * 	-output iris-output.arff"
 * 
 * @author uxl21
 *
 */
public class WekaTrain extends JDSRunnable {

	public WekaTrain(String[] args) {
		super(args);
	}
	
	

	@Override
	public void run() {
		BufferedReader reader = null;
		BufferedWriter writer = null;
		
		try {
			//
			// load model
			String wekaDataPath = this.argsParser.getArgument("wekaDataPath", this.argsParser.getArgument("defaultWekaDataPath"));
			String modelFile = wekaDataPath + File.separator + this.argsParser.getArgument("model", "iris_nb.model");
			
			NaiveBayes nb = (NaiveBayes)SerializationHelper.read(modelFile);
			
			
			//
			// load datasets
			String trainingArffFile = wekaDataPath + File.separator + this.argsParser.getArgument("training", "iris.arff");
			reader = new BufferedReader(new FileReader(trainingArffFile));
			Instances train = new Instances(reader);
			train.setClassIndex(train.numAttributes() - 1);

			String testingArffFile = wekaDataPath + File.separator + this.argsParser.getArgument("testing", "iris-test.arff");
			reader = new BufferedReader(new FileReader(testingArffFile));
			Instances test = new Instances(reader);
			test.setClassIndex(train.numAttributes() - 1);
			
			
			//
			// classify
			nb.buildClassifier(train);
			
			Instances labeled = new Instances(test);
			
			for( int i=0; i<test.numInstances(); i++ ) {
				try {
					double clsLabel = nb.classifyInstance(test.instance(i));
					labeled.instance(i).setClassValue(clsLabel);
					
					double[] predictionOutput = nb.distributionForInstance(test.instance(i));
					double predictionProbability = predictionOutput[1];
					
					this.logger.info("Prediction Probability = " + predictionProbability);
					
				} catch (Exception ex) {
					ex.printStackTrace();					
				}
			}
			
			
			//
			// write arff
			String outArffFile = wekaDataPath + File.separator + this.argsParser.getArgument("out", "iris-output.arff");
			writer = new BufferedWriter(new FileWriter(outArffFile));
			writer.write(labeled.toString());
			
			
		} catch (Exception ex) {
			ex.printStackTrace();

		} finally {
			try {
				if( reader != null ) {
					reader.close();
				}
				
				if( writer != null ) {
					writer.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}
