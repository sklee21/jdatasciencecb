package net.uxl21.jdatasciencecb.collectclean;

import java.io.IOException;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import net.uxl21.jdatasciencecb.JDSRunnable;

public class JSoupParser extends JDSRunnable {

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
			this.logger.info("Title = " + title);
			
			
			/*
			Elements links = doc.select("a[href]");

			for(Element link : links) {
				this.logger.info(
					//link.attr("href") + "\t" + link.text() + "\t" + link.outerHtml() + "\t" + link.html()
					link.attr("href") + "\t=>\t" + link.text()
				);
			}
			*/
			
			Elements elements = doc.select("table.tbl_data");
			
			this.parseLotWinList(elements);
		}
	}
	
	
	protected void parseLotWinList(Elements elements) {
		for(Element element : elements) {
			Elements tbody = element.getElementsByTag("tbody");
			
			if( tbody.size() > 0 ) {
				Elements trs = tbody.get(0).children();
				
				for(Element tr : trs) {
					this.logger.info(tr.text());
				}
			}
		}
	}

}
