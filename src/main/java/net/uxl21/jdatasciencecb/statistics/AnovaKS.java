package net.uxl21.jdatasciencecb.statistics;

import java.util.ArrayList;

import org.apache.commons.math3.stat.inference.TestUtils;

import net.uxl21.jdatasciencecb.JDSRunnable;

public class AnovaKS extends JDSRunnable {

	public AnovaKS(String[] args) {
		super(args);
	}
	

	@Override
	public void run() {
		double[] calorie = {8, 9, 6, 7, 3};
		double[] fat = {2, 4, 3, 5, 1};
		double[] carb = {3, 5, 4, 2, 3};
		double[] control = {2, 2, -1, 0, 3};
		
		ArrayList<double[]> classes = new ArrayList<>();
		classes.add(calorie);
		classes.add(fat);
		classes.add(carb);
		classes.add(control);
		
		this.logger.info("Anova F-Value = " + TestUtils.oneWayAnovaFValue(classes));
		this.logger.info("Anova P-Value = " + TestUtils.oneWayAnovaPValue(classes));
		this.logger.info("Anova Test    = " + TestUtils.oneWayAnovaTest(classes, 0.05));
		
		double d = TestUtils.kolmogorovSmirnovStatistic(CovarPrsTChiSqrTest.X, CovarPrsTChiSqrTest.Y);
		this.logger.info("Test   = " + TestUtils.kolmogorovSmirnovTest(CovarPrsTChiSqrTest.X, CovarPrsTChiSqrTest.Y, false));
		this.logger.info("ExactP = " + TestUtils.exactP(d, CovarPrsTChiSqrTest.X.length, CovarPrsTChiSqrTest.Y.length, false));
	}

}
