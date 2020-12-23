package net.uxl21.jdatasciencecb.statistics;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.commons.math3.stat.Frequency;

import net.uxl21.jdatasciencecb.JDSRunnable;

public class FrequencyStats extends JDSRunnable {

	public FrequencyStats(String[] args) {
		super(args);
	}
	

	@Override
	public void run() {
		//
		//
		double[] doubleValues = new double[] { 32d, 39d, 14d, 98d, 45d, 44d, 45d, 34d, 89d, 67d, 0d, 15d, 0d, 56d, 88d };
		
		Frequency freqNum = new Frequency();
		Arrays.stream(doubleValues).forEach(freqNum::addValue);
		
		for(double value : doubleValues) {
			this.logger.info(String.format("value %s => %s", value, freqNum.getCount(value)));
		}
		this.logger.info("\n\n");
		
		
		//
		//
		String str = "Horatio says 'tis but our fantasy, "
				   + "And will not let belief take hold of him "
				   + "Touching this dreaded sight, twice seen of us. "
				   + "Therefore I have entreated him along, 35 "
				   + "With us to watch the minutes of this night, "
				   + "That, if again this apparition come, "
				   + "He may approve our eyes and speak to it.";
		String[] allWords = str.toLowerCase().split("\\W+");
		
		Frequency freqStr = new Frequency();
		Arrays.stream(allWords).forEach(word -> {
			freqStr.addValue(word.trim());
		});
		
		for(String word : allWords) {
			this.logger.info(String.format("word '%s' => %s", word, freqStr.getCount(word)));
		}
		this.logger.info("\n\n");
		
		
		//
		//
		Stream<String> stream = Stream.of(str.toLowerCase().split("\\W+")).parallel();
		Map<String, Long> map = stream.collect(Collectors.groupingBy(String::toString, Collectors.counting()));
		map.forEach((key, value) -> {
			this.logger.info(String.format("word '%s' => %s", key, value));
		});
	}

}
