package net.uxl21.jdatasciencecb.statistics;

import org.apache.commons.math3.stat.correlation.Covariance;
import org.apache.commons.math3.stat.correlation.PearsonsCorrelation;
import org.apache.commons.math3.stat.inference.TestUtils;

import net.uxl21.jdatasciencecb.JDSRunnable;

public class CovarPrsTChiSqrTest extends JDSRunnable {
	
	public static final long[] OBSERVED = {43, 21, 25, 42, 57, 59};
	
	public static final double[] X = {43, 21, 25, 42, 57, 59};
	public static final double[] Y = {99, 65, 79, 75, 87, 81};
	

	public CovarPrsTChiSqrTest(String[] args) {
		super(args);
	}
	

	@Override
	public void run() {
		this.calculateCov(X, Y);		
		this.calculatePearson(X, Y);
		this.getTTest(X, Y);
		this.getChiSquare(OBSERVED, Y);
	}
	
	
	public void calculateCov(double[] x, double[] y) {
		double covariance = new Covariance().covariance(x, y, false);
		this.logger.info("Covariance = " + covariance);
	}
	
	
	public void calculatePearson(double[] x, double[] y) {
		PearsonsCorrelation pCorrelation = new PearsonsCorrelation();
		double cor = pCorrelation.correlation(x, y);
		this.logger.info("Pearson Correlation = " + cor);
	}
	
	
	public void getTTest(double[] sample1, double[] sample2) {
		this.logger.info("Paired T      = " + TestUtils.pairedT(sample1, sample2));
		this.logger.info("Paired T Test = " + TestUtils.pairedTTest(sample1, sample2));
		this.logger.info("Paired T Test = " + TestUtils.pairedTTest(sample1, sample2, 0.05));
	}
	
	
	public void getChiSquare(long[] observed, double[] expected) {
		this.logger.info("Chi Square      = " + TestUtils.chiSquare(expected, observed));
		this.logger.info("Chi Square Test = " + TestUtils.chiSquareTest(expected, observed));
		this.logger.info("Chi Square Test = " + TestUtils.chiSquareTest(expected, observed, 0.05));
	}

}
