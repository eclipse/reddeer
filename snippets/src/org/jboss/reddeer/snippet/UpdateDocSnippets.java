package org.jboss.reddeer.snippet;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 * Class to snippets in RedDeer User guide. Directory defined by ${workspace} property 
 * in Jenkins environment has to contain RedDeer repo and RedDeer wiki repo cloned in itself 
 * for successful pass of main method. In case of running main method outside of Jenkins, 
 * environment, user is obliged to define WORKSPACE_DIRECTORY environment variable to point 
 * to a directory containing both reddeer repo and reddeer.wiki repo.
 * 
 * @author mlabuda@redhat.com
 * @since 0.8.0
 *
 */
public class UpdateDocSnippets {

	public static final String WORKSPACE_DIRECTORY = "WORKSPACE_DIRECTORY";
	
	private static final String MAPPING_FILE = "SnippetsMapping.xml";
	
	private static final String DOCUMENT_NODE = "doc";
	private static final String SNIPPET_NODE = "snippet";
	private static final String WIKI_PAGE_ATTR = "page";
	private static final String INDEX_ATTR = "index";
	private static final String PACKAGE_ATTR = "package";
	private static final String FILE_ATTR = "file";

	/**
	 * Updates snippets in RedDeer wiki User guide.
	 *  
	 * @param args arguments
	 * @throws ParserConfigurationException 
	 * @throws IOException 
	 * @throws SAXException 
	 */
	public static void main(String[] args) throws ParserConfigurationException, SAXException, IOException {
		String workspaceDir = System.getProperty(WORKSPACE_DIRECTORY);
		String redDeerDir = workspaceDir + File.separator + "reddeer";
		String redDeerWikiDir = workspaceDir + File.separator + "reddeer.wiki";
		
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
		Document document = dBuilder.parse(UpdateDocSnippets.class.getResourceAsStream(MAPPING_FILE));
		
		NodeList pages = document.getElementsByTagName(DOCUMENT_NODE);
		for (int i= 0; i < pages.getLength(); i++) {
			Element pageElement = (Element) pages.item(i);
			String wikiPageLocation = redDeerWikiDir + File.separator + pageElement.getAttribute(WIKI_PAGE_ATTR);
			NodeList snippets = pageElement.getElementsByTagName(SNIPPET_NODE);
			BufferedReader wikiReader = new BufferedReader(new FileReader(new File(wikiPageLocation)));
			StringBuilder builder = new StringBuilder();
			String line;
			
			int index = 0;
			while ((line = wikiReader.readLine()) != null) {
				builder.append(line + System.lineSeparator());
				if (line.contains("```java") || line.contains("```xml")) {
					Element snippet = getElementWithIndex(snippets, index);
					if (snippet != null) {
						String path = redDeerDir + File.separator;
						String partialPath;
						String plugin;
						// Append new code of specific snippet
						if (!(plugin = snippet.getAttribute("plugin")).equals("")) {
							partialPath = "plugins" + File.separator + plugin +
									File.separator + "src" + File.separator;
						} else {
							partialPath = "snippets" + File.separator  + "src" + File.separator;
						}
						partialPath += snippet.getAttribute(PACKAGE_ATTR).replace(".", File.separator) + File.separator +
								snippet.getAttribute(FILE_ATTR);
						path += partialPath;
						
						BufferedReader snippetReader = new BufferedReader(new FileReader(new File(path)));
						String snippetLine;
						while ((snippetLine = snippetReader.readLine()) != null) {
							builder.append(snippetLine + System.lineSeparator());
						}
						snippetReader.close();
						
						// Read other lines but ignore them and append end of code block
						String codeEndLine;
						while (!("```").equals(codeEndLine = wikiReader.readLine())) { }
						builder.append(codeEndLine + System.lineSeparator());
						index++;
						
						line = wikiReader.readLine();
						builder.append("[source code](" + "https://github.com/jboss-reddeer/reddeer/blob/master/" + 
								partialPath + ")" + System.lineSeparator() + System.lineSeparator());
						if (line != null && !line.contains("[source code]")) {
								builder.append(line + System.lineSeparator());
						} 
					}	
				}
			}
			wikiReader.close();
			
			BufferedWriter wikiWriter = new BufferedWriter(new FileWriter(new File(wikiPageLocation)));
			wikiWriter.write(builder.toString());
			wikiWriter.flush();
			wikiWriter.close();
		}
	}
	
	private static Element getElementWithIndex(NodeList nodeList, int index) {
		for (int i=0; i < nodeList.getLength(); i++) {
			Element snippet = (Element) nodeList.item(i);
			String indexAttribute = snippet.getAttribute(INDEX_ATTR);
			if (indexAttribute != "") {
				if (index == Integer.valueOf(indexAttribute).intValue()) {
					return snippet;
				}
			}
		}
		return null;
	}
}
