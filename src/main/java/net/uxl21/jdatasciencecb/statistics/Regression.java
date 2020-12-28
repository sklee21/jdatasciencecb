package net.uxl21.jdatasciencecb.statistics;

import java.util.Arrays;
import java.util.stream.Stream;

import org.apache.commons.math3.stat.regression.GLSMultipleLinearRegression;
import org.apache.commons.math3.stat.regression.OLSMultipleLinearRegression;
import org.apache.commons.math3.stat.regression.SimpleRegression;

import net.uxl21.jdatasciencecb.JDSRunnable;

public class Regression extends JDSRunnable {
	
	public static final double[][] DATA_REG = {
		{1, 3}, {2, 5}, {3, 7}, {4, 14}, {5, 11}
	};
	
	
	public static final double[] DATA_LSREG_Y = {
		11.0d, 12.0d, 13.0d, 14.0d, 15.0, 16.0d
	};
	
	public static final double[][] DATA_LSREG_X = {
		{0, 0, 0, 0, 0}  , {2.0, 0, 0, 0, 0}, {0, 3.0, 0, 0, 0},
		{0, 0, 4.0, 0, 0}, {0, 0, 0, 5.0, 0}, {0, 0, 0, 0, 6.0}
	};
	
	
	public static final double[][] OMEGA = {
		{1.1, 0, 0, 0, 0, 0}, {0, 2.2, 0, 0, 0, 0}, {0, 0, 3.3, 0, 0, 0},
		{0, 0, 0, 4.4, 0, 0}, {0, 0, 0, 0, 5.0, 0}, {0, 0, 0, 0, 0, 6.0}
	};
	
	

	public Regression(String[] args) {
		super(args);
	}
	

	@Override
	public void run() {
		this.logger.debug("===================== Regression START =====================");
		this.calculateRegression(DATA_REG);
		this.logger.debug("===================== Regression END   =====================\n");

		this.logger.debug("===================== OLS Regression START =====================");
		this.calculateOlsRegression(DATA_LSREG_X, DATA_LSREG_Y);
		this.logger.debug("===================== OLS Regression END   =====================\n");

		this.logger.debug("===================== GLS Regression START =====================");
		this.calculateGlsRegression(DATA_LSREG_X, DATA_LSREG_Y, OMEGA);
		this.logger.debug("===================== GLS Regression END   =====================\n");
	}
	
	
	
	public void calculateRegression(double[][] data) {
		SimpleRegression regression = new SimpleRegression();
		regression.addData(data);
		
		this.logger.info("Intercept = " + regression.getIntercept());
		this.logger.info("Slope     = " + regression.getSlope());
		this.logger.info("Slope Err = " + regression.getSlopeStdErr());
	}
	
	
	public void calculateOlsRegression(double[][] x, double[] y) {
		OLSMultipleLinearRegression regression = new OLSMultipleLinearRegression();
		regression.newSampleData(y, x);
		
		double[]   beta               = regression.estimateRegressionParameters();
		double[]   residuals          = regression.estimateResiduals();
		double[][] parametersVariance = regression.estimateRegressionParametersVariance();
		double     regressandVariance = regression.estimateRegressandVariance();
		double     rSqured            = regression.calculateRSquared();
		double     sigma              = regression.estimateRegressionStandardError();
		
		this.logger.info("Beta            = " + Arrays.toString(beta));
		this.logger.info("Residuals       = " + Arrays.toString(residuals));
		this.logger.info("Param. Var.     = [");
		
		Stream.of(parametersVariance).forEach(element -> {
			this.logger.info("\t" + Arrays.toString(element));
		});
		
		this.logger.info("]");
		
		this.logger.info("Regressand Var. = " + regressandVariance);
		this.logger.info("RSqured         = " + rSqured);
		this.logger.info("Sigma           = " + sigma);
	}
	
	
	public void calculateGlsRegression(double[][] x, double[] y, double[][] omega) {
		GLSMultipleLinearRegression regression = new GLSMultipleLinearRegression();
		regression.newSampleData(y, x, omega);
		
		double[]   beta               = regression.estimateRegressionParameters();
		double[]   residuals          = regression.estimateResiduals();
		double[][] parametersVariance = regression.estimateRegressionParametersVariance();
		double     regressandVariance = regression.estimateRegressandVariance();
		double     sigma              = regression.estimateRegressionStandardError();
		
		this.logger.info("Beta            = " + Arrays.toString(beta));
		this.logger.info("Residuals       = " + Arrays.toString(residuals));
		this.logger.info("Param. Var.     = [");
		
		Stream.of(parametersVariance).forEach(element -> {
			this.logger.info("\t" + Arrays.toString(element));
		});
		
		this.logger.info("]");
		
		this.logger.info("Regressand Var. = " + regressandVariance);
		this.logger.info("Sigma           = " + sigma);
	}

}
