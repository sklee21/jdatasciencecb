package net.uxl21.jdatasciencecb.lfd1;

import java.io.File;

import net.uxl21.jdatasciencecb.JDSRunnable;
import weka.clusterers.SimpleKMeans;
import weka.core.Instances;
import weka.core.converters.ConverterUtils.DataSource;

public class WekaKM extends JDSRunnable {
	
	private String arffPath = null;
	
	private String arffFile = null;
	
	
	public static final String ARFF_FILE = "cpu.arff";
	
	
	
	

	public WekaKM(String[] args) {
		super(args);
	}
	

	@Override
	public void run() {
		this.arffPath = this.argsParser.getArgument("wekaDataPath", this.configSet.getString("defaultWekaDataPath"));
		this.arffFile = this.arffPath + File.separator + this.argsParser.getArgument("arffFile", ARFF_FILE);
		
		try {
			DataSource dataSource = new DataSource(this.arffFile);
			Instances cpu = dataSource.getDataSet();
			
			SimpleKMeans kmeans = new SimpleKMeans();
			kmeans.setSeed(10);
			kmeans.setPreserveInstancesOrder(true);
			kmeans.setNumClusters(10);
			kmeans.buildClusterer(cpu);
			
			int[] assignments = kmeans.getAssignments();
			int i = 0;
			
			for( int clusterNum : assignments ) {
				System.out.printf("Instance %d → Cluster %d\n", i, clusterNum);
				i++;
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
