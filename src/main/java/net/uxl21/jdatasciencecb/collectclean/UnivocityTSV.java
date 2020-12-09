package net.uxl21.jdatasciencecb.collectclean;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import com.univocity.parsers.common.processor.RowListProcessor;
import com.univocity.parsers.tsv.TsvParser;
import com.univocity.parsers.tsv.TsvParserSettings;

public class UnivocityTSV extends CollectCleanRunnable {

	public UnivocityTSV(String[] args) {
		super(args);
	}

	@Override
	public void run() {
		String fileName = this.configSet.getString("fileDir") + File.separator;
		
		if( this.params.length > 0 ) {
			fileName += this.params[0];
		} else {
			fileName += this.configSet.getString("tsvFileToRead");
		}
		
		TsvParserSettings settings = new TsvParserSettings();
		settings.getFormat().setLineSeparator("\n");
		settings.setHeaderExtractionEnabled(true);
		
		RowListProcessor rowProcessor = new RowListProcessor();
		settings.setProcessor(rowProcessor);
		
		TsvParser parser = new TsvParser(settings);
		List<String[]> allRows = parser.parseAll(new File(fileName));
		
		String[] headers = rowProcessor.getHeaders();
		this.logger.info("HEADER: " + Arrays.asList(headers).toString());
		
		for(int i=0; i<allRows.size(); i++ ) {
			this.logger.info(i + ": " + Arrays.asList(allRows.get(i)));
		}
	}

}
