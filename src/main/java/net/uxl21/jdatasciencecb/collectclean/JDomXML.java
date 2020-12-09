package net.uxl21.jdatasciencecb.collectclean;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

public class JDomXML extends CollectCleanRunnable {

	public JDomXML(String[] args) {
		super(args);
	}

	@Override
	public void run() {
		String fileName = this.configSet.getString("fileDir") + File.separator;
		
		if( this.params.length > 0 ) {
			fileName += this.params[0];
		} else {
			fileName += this.configSet.getString("xmlFileToRead");
		}
		
		SAXBuilder builder = new SAXBuilder();
		File file = new File(fileName);
		
		try {
			Document document = builder.build(file);
			Element rootNode = document.getRootElement();
			
			List<Element> list = rootNode.getChildren("author");
			Element node;
			
			for( int i=0; i<list.size(); i++ ) {
				node = list.get(i);
				this.logger.info(i + ": " + node.getChildText("firstname") + " " + node.getChildText("lastname"));
			}
			
		} catch (JDOMException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
