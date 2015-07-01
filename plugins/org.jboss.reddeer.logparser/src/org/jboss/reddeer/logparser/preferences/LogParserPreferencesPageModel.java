package org.jboss.reddeer.logparser.preferences;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.ui.IMemento;
import org.eclipse.ui.WorkbenchException;
import org.eclipse.ui.XMLMemento;
import org.jboss.reddeer.logparser.LogParserActivator;
import org.jboss.reddeer.logparser.LogParserLog;
import org.jboss.reddeer.logparser.model.ParseRule;

public class LogParserPreferencesPageModel {
	private static final String TAG_PARSE_RULES = "parse_rules";
	private static final String TAG_PARSE_RULE = "parse_rule";
	private static final String TAG_NAME = "name";
	private static final String TAG_DESCRIPTION = "description";
	private static final String TAG_INCLUDE_REGEX = "include_regex";
	private static final String TAX_EXCLUDE_REGEX = "exclude_regex";
	private static final String TAG_INDENT = "indent";
	private static final String TAG_PERFIX = "prefix";
	private static final String TAG_DISPLAY_LINES_BEFORE = "display_lines_before";
	private static final String TAG_DISPLAY_LINES_AFTER = "display_lines_after";

	public static ArrayList<ParseRule> getParseRules() {
		return loadParseRules();
	}

	public static void saveParseRules(List<ParseRule> parseRules) {
		XMLMemento memento = XMLMemento.createWriteRoot(TAG_PARSE_RULES);
		FileWriter fileWriter = null;
		try {
			for (ParseRule parseRule : parseRules) {
				IMemento parseRuleNode = memento.createChild(TAG_PARSE_RULE);
				parseRuleNode.putString(TAG_NAME, parseRule.getName());
				parseRuleNode.createChild(TAG_DESCRIPTION).putTextData(parseRule.getDescription());
				parseRuleNode.createChild(TAG_INCLUDE_REGEX).putTextData(parseRule.getIncludeRegex());
				parseRuleNode.createChild(TAX_EXCLUDE_REGEX).putTextData(parseRule.getExcludeRegex());
				parseRuleNode.createChild(TAG_INDENT).putTextData(String.valueOf(parseRule.getIndent()));
				parseRuleNode.createChild(TAG_PERFIX).putTextData(parseRule.getPrefix());
				parseRuleNode.createChild(TAG_DISPLAY_LINES_BEFORE)
						.putTextData(String.valueOf(parseRule.getDisplayLinesBefore()));
				parseRuleNode.createChild(TAG_DISPLAY_LINES_AFTER)
						.putTextData(String.valueOf(parseRule.getDisplaylinesAfter()));
			}
			fileWriter = new FileWriter(getParseRulesFile());
			memento.save(fileWriter);
		} catch (IOException ioe) {
			LogParserLog.logError(ioe);
		} finally {
			try {
				if (fileWriter != null) {
					fileWriter.close();
				}
			} catch (IOException ioe) {
				LogParserLog.logError(ioe);
			}
		}
	}

	private static ArrayList<ParseRule> loadParseRules() {
		ArrayList<ParseRule> parseRules = new ArrayList<ParseRule>();
		
		FileReader fileReader = null;
		File parseRuleFile = getParseRulesFile();
		if (parseRuleFile.exists()){
			try {
				fileReader = new FileReader(parseRuleFile);
				IMemento[] parseRuleNodes = XMLMemento.createReadRoot(fileReader).getChildren(TAG_PARSE_RULE);
				for (IMemento parseRuleNode : parseRuleNodes) {
					ParseRule parseRule = new ParseRule();
					parseRule.setName(parseRuleNode.getString(TAG_NAME));
					parseRule.setDescription(getMementoNonNullTextData(parseRuleNode.getChild(TAG_DESCRIPTION)));
					parseRule.setIncludeRegex(getMementoNonNullTextData(parseRuleNode.getChild(TAG_INCLUDE_REGEX)));
					parseRule.setExcludeRegex(getMementoNonNullTextData(parseRuleNode.getChild(TAX_EXCLUDE_REGEX)));
					parseRule.setIndent(Integer.parseInt(parseRuleNode.getChild(TAG_INDENT).getTextData()));
					parseRule.setPrefix(getMementoNonNullTextData(parseRuleNode.getChild(TAG_PERFIX)));
					parseRule.setDisplayLinesBefore(Integer.parseInt(parseRuleNode.getChild(TAG_DISPLAY_LINES_BEFORE).getTextData()));
					parseRule.setDisplaylinesAfter(Integer.parseInt(parseRuleNode.getChild(TAG_DISPLAY_LINES_AFTER).getTextData()));
					parseRules.add(parseRule);
				}
			} catch (WorkbenchException|IOException ioe ) {
				LogParserLog.logError(ioe);
			} finally {
				try {
					if (fileReader != null) {
						fileReader.close();
					}
				} catch (IOException ioe) {
					LogParserLog.logError(ioe);
				}
			}
		}
		
		return parseRules;
	}

	private static String getMementoNonNullTextData (IMemento node){
		String nodeTextData = node.getTextData();
		return nodeTextData == null ? "" : nodeTextData;
	}
	
	private static File getParseRulesFile() {
		return LogParserActivator.getDefault().getStateLocation().append("parserules.xml").toFile();
	}
}
