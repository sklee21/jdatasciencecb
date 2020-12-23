package net.uxl21.jdatasciencecb.statistics;

import java.util.Arrays;

import org.apache.commons.math3.stat.descriptive.AggregateSummaryStatistics;
import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics;
import org.apache.commons.math3.stat.descriptive.SummaryStatistics;

import net.uxl21.jdatasciencecb.JDSRunnable;

public class DescSummAggrStats extends JDSRunnable {

	public DescSummAggrStats(String[] args) {
		super(args);
	}
	

	@Override
	public void run() {
		double[] doubleValues = new double[] { 32d, 39d, 14d, 98d, 45d, 44d, 45d, 34d, 89d, 67d, 0d, 15d, 0d, 56d, 88d };
		
		//
		// descriptive statistics
		DescriptiveStatistics descStats = new DescriptiveStatistics();
		Arrays.stream(doubleValues).forEach(descStats::addValue);
		
		double mean = descStats.getMean();
		double std = descStats.getStandardDeviation();
		double median = descStats.getPercentile(50);
		
		this.logger.debug("[DESCRIPTIVE STATISTICS");
		this.logger.info(String.format("Mean = %f, StdDev = %f, Median = %f\n", mean, std, median));
		
		
		//
		// summary statistics
		SummaryStatistics summStats = new SummaryStatistics();
		Arrays.stream(doubleValues).forEach(summStats::addValue);
		
		mean = summStats.getMean();
		std = summStats.getStandardDeviation();
		
		this.logger.debug("[SUMMARY STATISTICS");
		this.logger.info(String.format("Mean = %f, StdDev = %f\n", mean, std));
		
		
		//
		// aggregate summary statistics
		AggregateSummaryStatistics aggrSummStats = new AggregateSummaryStatistics();
		
		SummaryStatistics aggrSummSet1 = aggrSummStats.createContributingStatistics();
		Arrays.stream(new double[] { 32d, 39d, 14d, 98d, 45d, 44d, 45d }).forEach(aggrSummSet1::addValue);
		
		SummaryStatistics aggrSummSet2 = aggrSummStats.createContributingStatistics();
		Arrays.stream(new double[] { 34d, 89d, 67d, 0d, 15d, 0d, 56d, 88d }).forEach(aggrSummSet2::addValue);	
		
		mean = aggrSummStats.getMean();
		std = aggrSummStats.getStandardDeviation();
		double sum = aggrSummStats.getSum();
		
		this.logger.debug("[AGGREGATE SUMMARY STATISTICS");
		this.logger.info(String.format("Mean = %f, StdDev = %f, Sum = %f\n", mean, std, sum));
	}

}
