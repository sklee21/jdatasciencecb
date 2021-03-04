package net.uxl21.jdatasciencecb.lfd2;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import net.sf.javaml.classification.Classifier;
import net.sf.javaml.classification.KNearestNeighbors;
import net.sf.javaml.classification.evaluation.CrossValidation;
import net.sf.javaml.classification.evaluation.EvaluateDataset;
import net.sf.javaml.classification.evaluation.PerformanceMeasure;
import net.sf.javaml.clustering.Clusterer;
import net.sf.javaml.clustering.KMeans;
import net.sf.javaml.clustering.evaluation.ClusterEvaluation;
import net.sf.javaml.clustering.evaluation.SumOfSquaredErrors;
import net.sf.javaml.core.Dataset;
import net.sf.javaml.distance.PearsonCorrelationCoefficient;
import net.sf.javaml.featureselection.ranking.RecursiveFeatureEliminationSVM;
import net.sf.javaml.featureselection.scoring.GainRatio;
import net.sf.javaml.featureselection.subset.GreedyForwardSelection;
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
			//
			// load
			Dataset data = FileHandler.loadDataset(new File(this.irisDataFile), 4, ",");
			System.out.printf("DATA => %s\n", data.toString());
			
			FileHandler.exportDataset(data, new File(this.jmlDataPath + File.separator + IRIS_OUTPUT_FILE));
			data = FileHandler.loadDataset(new File(this.jmlDataPath + File.separator + IRIS_OUTPUT_FILE), 0, "\t");
			System.out.printf("OUTPUT => %s\n", data.toString());
			
			
			//
			// clustering
			Clusterer km = new KMeans();
			Dataset[] clusters = km.cluster(data);
			
			for( Dataset cluster : clusters ) {
				System.out.printf("CLUSTER => %s\n", cluster.toString());
			}
			
			ClusterEvaluation sse = new SumOfSquaredErrors();
			double score = sse.score(clusters);
			System.out.printf("Score => %f\n", score);
			
			
			//
			// classification
			Classifier knn = new KNearestNeighbors(5);
			knn.buildClassifier(data);
			
			
			//
			// cross validation
			CrossValidation cv = new CrossValidation(knn);
			Map<Object, PerformanceMeasure> cvEvalidation = cv.crossValidation(data);
			System.out.printf("Cross Validation => %s\n", cvEvalidation.toString());
			
			
			//
			// hold out test
			Dataset testData = FileHandler.loadDataset(new File(this.irisDataFile), 4, ",");
			Map<Object, PerformanceMeasure> testEvaluation = EvaluateDataset.testDataset(knn, testData);
			
			for( Object classVariable : testEvaluation.keySet() ) {
				System.out.printf("%s class has %f\n", classVariable, testEvaluation.get(classVariable).getAccuracy());
			}
			
			
			//
			// feature scoring
			GainRatio gainRatio = new GainRatio();
			gainRatio.build(data);
			System.out.println("Scores => ");
			
			for( int i=0; i<gainRatio.noAttributes(); i++ ) {
				System.out.println(gainRatio.score(i));
			}
			
			
			//
			// feature ranking
			RecursiveFeatureEliminationSVM featureRank = new RecursiveFeatureEliminationSVM(0.2);
			featureRank.build(data);
			System.out.println("Rank => ");
			
			for( int i=0; i<featureRank.noAttributes(); i++ ) {
				System.out.println(featureRank.rank(i));
			}
			
			
			//
			// select feature
			GreedyForwardSelection featureSelection = new GreedyForwardSelection(5,  new PearsonCorrelationCoefficient());
			featureSelection.build(data);
			System.out.printf("Selected attributes => %s", featureSelection.selectedAttributes().toString());
			
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
