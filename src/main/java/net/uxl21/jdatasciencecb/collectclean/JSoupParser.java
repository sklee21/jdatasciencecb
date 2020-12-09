package net.uxl21.jdatasciencecb.collectclean;

import java.io.IOException;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class JSoupParser extends CollectCleanRunnable {

	public JSoupParser(String[] args) {
		super(args);
	}

	@Override
	public void run() {
		String href;
		
		if( this.params.length > 0 ) {
			href = this.params[0];
		} else {
			href = "https://www.google.com";
		}
		
		Connection connection = Jsoup.connect(href);
		Document doc = null;
		
		try {
			doc = connection.timeout(10 * 1000).userAgent("Mozilla").ignoreHttpErrors(true).get();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
		if( doc != null ) {
			String title = doc.title();
			//String bodyText = doc.body().text();
			Elements links = doc.select("a[href]");
			
			this.logger.info("Title = " + title);
			
			for(Element link : links) {
				this.logger.info(
					//link.attr("href") + "\t" + link.text() + "\t" + link.outerHtml() + "\t" + link.html()
					link.attr("href") + "\t=>\t" + link.text()
				);
			}
		}
	}

}
