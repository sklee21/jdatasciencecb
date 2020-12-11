package net.uxl21.jdatasciencecb.indexsearch;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.Objects;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.FSDirectory;

import net.uxl21.jdatasciencecb.JDSRunnable;

public class SearchFiles extends JDSRunnable {
	
	public static final String FIELD_CONTENTS = "contents";
	
	

	public SearchFiles(String[] args) {
		super(args);
	}

	@Override
	public void run() {
		String indexPath = this.configSet.getString("defaultIndexPath");
		String searchString = "bezel";
		
		for( int i=0; i<this.params.length; i++ ) {
			if( Objects.equals(this.params[i], "-index") ) {
				indexPath = this.params[i + 1];
			}
			
			if( Objects.equals(this.params[i], "-search") ) {
				searchString = this.params[i + 1];
			}
		}
		
		try {
			IndexReader reader = DirectoryReader.open(FSDirectory.open(Paths.get(indexPath)));
			IndexSearcher indexSearcher = new IndexSearcher(reader);
			
			Analyzer analyzer = new StandardAnalyzer();
			QueryParser queryParser = new QueryParser(FIELD_CONTENTS, analyzer);
			Query query = queryParser.parse(searchString);
			
			TopDocs results = indexSearcher.search(query, 5);
			ScoreDoc[] hits = results.scoreDocs;
			long numTotalHits = results.totalHits.value;
			
			this.logger.info(numTotalHits + " total matching documents.");
			
			for( int i=0; i<hits.length; i++ ) {
				this.logger.info((i + 1) + ": " + indexSearcher.doc(hits[i].doc).get("path") + ", score=" + hits[i].score);
			}
			
		} catch (IOException e) {
			e.printStackTrace();
			
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}

}
