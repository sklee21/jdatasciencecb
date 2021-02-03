package net.uxl21.jdatasciencecb.lfd1;

import java.io.File;

import net.uxl21.jdatasciencecb.JDSRunnable;
import weka.clusterers.ClusterEvaluation;
import weka.clusterers.EM;
import weka.core.Instances;
import weka.core.converters.ConverterUtils.DataSource;
import weka.filters.Filter;
import weka.filters.unsupervised.attribute.Remove;

public class WekaC2C extends JDSRunnable {
	
	private String arffPath = null;
	
	private String arffFile = null;
	
	
	public static final String ARFF_FILE = "weather.nominal.arff";
	
	
	
	

	public WekaC2C(String[] args) {
		super(args);
	}
	

	@Override
	public void run() {
		this.arffPath = this.argsParser.getArgument("wekaDataPath", this.configSet.getString("defaultWekaDataPath"));
		this.arffFile = this.arffPath + File.separator + this.argsParser.getArgument("arffFile", ARFF_FILE);
		
		try {
			DataSource dataSource = new DataSource(this.arffFile);
			Instances weather = dataSource.getDataSet();
			weather.setClassIndex(weather.numAttributes() - 1);
			
			Remove removeFilter = new Remove();
			removeFilter.setAttributeIndices(Integer.toString(weather.classIndex() + 1));
			removeFilter.setInputFormat(weather);
			
			Instances dataCluster = Filter.useFilter(weather, removeFilter);
			
			EM clusterer = new EM();
			clusterer.buildClusterer(dataCluster);
			
			ClusterEvaluation eval = new ClusterEvaluation();
			eval.setClusterer(clusterer);
			eval.evaluateClusterer(weather);
			
			System.out.println(eval.clusterResultsToString());
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
