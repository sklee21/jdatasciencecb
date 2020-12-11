package net.uxl21.jdatasciencecb.statistics;

import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics;

import net.uxl21.jdatasciencecb.JDSRunnable;

public class DescriptiveStats extends JDSRunnable {

	public DescriptiveStats(String[] args) {
		super(args);
	}

	@Override
	public void run() {
		double[] values = { 32, 39, 14, 98, 45, 44, 45, 34, 89, 67, 0, 15, 0, 56, 88 };
		
		DescriptiveStatistics stats = new DescriptiveStatistics();
		
		for( int i=0; i<values.length; i++ ) {
			stats.addValue(values[i]);
		}
		
		
		double mean = stats.getMean();
		double std = stats.getStandardDeviation();
		double median = stats.getPercentile(50);
		
		this.logger.info(String.format("Mean = %f, StdDev = %f, Median = %f", mean, std, median));
	}

}
