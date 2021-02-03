package net.uxl21.jdatasciencecb.lfd1;

import java.io.File;

import net.uxl21.jdatasciencecb.JDSRunnable;
import weka.associations.Apriori;
import weka.core.Instances;
import weka.core.converters.ConverterUtils.DataSource;

public class WekaAR extends JDSRunnable {
	
	private String arffPath = null;
	
	private String arffFile = null;
	
	
	public static final String ARFF_FILE = "supermarket.arff";
	
	
	
	

	public WekaAR(String[] args) {
		super(args);
	}
	

	@Override
	public void run() {
		this.arffPath = this.argsParser.getArgument("wekaDataPath", this.configSet.getString("defaultWekaDataPath"));
		this.arffFile = this.arffPath + File.separator + this.argsParser.getArgument("arffFile", ARFF_FILE);
		
		try {
			DataSource dataSource = new DataSource(this.arffFile);
			Instances superMarket = dataSource.getDataSet();
			
			Apriori apriori = new Apriori();
			apriori.buildAssociations(superMarket);
			
			System.out.println(apriori);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
