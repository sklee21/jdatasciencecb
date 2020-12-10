package net.uxl21.jdatasciencecb.collectclean;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import com.univocity.parsers.common.processor.RowListProcessor;
import com.univocity.parsers.csv.CsvParser;
import com.univocity.parsers.csv.CsvParserSettings;

import net.uxl21.jdatasciencecb.JDSRunnable;

public class UnivocityCSV extends JDSRunnable {

	public UnivocityCSV(String[] args) {
		super(args);
	}

	@Override
	public void run() {
		String fileName = this.configSet.getString("fileDir") + File.separator;
		
		if( this.params.length > 0 ) {
			fileName += this.params[0];
		} else {
			fileName += this.configSet.getString("csvFileToRead");
		}
		
		CsvParserSettings parserSettings = new CsvParserSettings();
		parserSettings.setLineSeparatorDetectionEnabled(true);
		
		RowListProcessor rowProcessor = new RowListProcessor();
		//parserSettings.setRowProcessor(rowProcessor);
		parserSettings.setProcessor(rowProcessor);
		parserSettings.setHeaderExtractionEnabled(true);
		
		CsvParser parser = new CsvParser(parserSettings);
		parser.parse(new File(fileName));
		
		String[] headers = rowProcessor.getHeaders();
		this.logger.info("HEADER: " + Arrays.asList(headers).toString());
		
		List<String[]> rows = rowProcessor.getRows();
		
		for( int i=0; i<rows.size(); i++ ) {
			this.logger.info(i + ": " + Arrays.asList(rows.get(i)));
		}
	}

}
