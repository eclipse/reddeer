package org.jboss.reddeer.logparser.preferences;

import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;
import org.jboss.reddeer.logparser.model.ParseRule;

class ParseRuleLabelProvider extends LabelProvider implements ITableLabelProvider {
	public String getColumnText(Object obj, int index) {
		ParseRule parseRule = (ParseRule) obj;
		switch (index) {
		case 0:
			return parseRule.getName();
		case 1:
			return parseRule.getDescription();
		case 2:
			return parseRule.getIncludeRegex();
		case 3:
			return parseRule.getExcludeRegex();
		default:
			return "Unknown index: " + index;
		}

	}

	public Image getColumnImage(Object obj, int index) {
		return null;
	}
}