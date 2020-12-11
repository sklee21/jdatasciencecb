package net.uxl21.jdatasciencecb.indexsearch;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Date;
import java.util.Objects;

import org.apache.commons.lang3.time.StopWatch;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.LongPoint;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.IndexWriterConfig.OpenMode;
import org.apache.lucene.index.Term;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

import net.uxl21.jdatasciencecb.JDSRunnable;

public class IndexFiles extends JDSRunnable {
	
	public IndexFiles(String[] args) {
		super(args);
	}
	
	

	public static void indexDocs(final IndexWriter writer, Path path) throws IOException {
		if( Files.isDirectory(path) ) {
			Files.walkFileTree(path, new SimpleFileVisitor<Path>() {
				@Override
				public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
					indexDocs(writer, file, attrs.lastModifiedTime().toMillis());
					
					return FileVisitResult.CONTINUE;
				}
			});
			
		} else {
			indexDocs(writer, path, Files.getLastModifiedTime(path).toMillis());
		}
	}
	
	
	public static void indexDocs(final IndexWriter writer, Path file, long lastModified) throws IOException {
		try (InputStream stream = Files.newInputStream(file)) {
			Field pathField = new StringField("path", file.toString(), Field.Store.YES);
			
			Document doc = new Document();
			doc.add(pathField);
			doc.add(new LongPoint("modified", lastModified));
			doc.add(new TextField("contents", new BufferedReader(new InputStreamReader(stream, StandardCharsets.UTF_8))));
			
			if( writer.getConfig().getOpenMode() == OpenMode.CREATE ) {
				writer.addDocument(doc);
			} else {
				writer.updateDocument(new Term("path", file.toString()), doc);
			}
		}
	}


	@Override
	public void run() {
		String indexPath = this.configSet.getString("defaultIndexPath");
		String docsPath = this.configSet.getString("fileDir");
		boolean create = true;
		
		for( int i=0; i<this.params.length; i++ ) {
			if( Objects.equals(this.params[i], "-index") ) {
				indexPath = this.params[i + 1];
			
			} else if( Objects.equals(this.params[i], "-docs") ) {
				docsPath = this.params[i + 1];
			
			} else if( Objects.equals(this.params[i], "-update") ) {
				create = false;
			}
		}
		
		
		final Path docDir = Paths.get(docsPath);
		
		StopWatch stopWatch = new StopWatch();
		stopWatch.start();
		
		try {
			Directory dir = FSDirectory.open(Paths.get(indexPath));
			Analyzer analyzer = new StandardAnalyzer();
			IndexWriterConfig lwc = new IndexWriterConfig(analyzer);
			
			if( create ) {
				lwc.setOpenMode(OpenMode.CREATE);
			} else {
				lwc.setOpenMode(OpenMode.CREATE_OR_APPEND);
			}
			
			IndexWriter writer = new IndexWriter(dir, lwc);
			indexDocs(writer, docDir);
			writer.close();
			
			stopWatch.stop();
			this.logger.debug("Ellapsed time: " + stopWatch.formatTime());
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
}
