package org.jboss.reddeer.logparser.preferences;

import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerSorter;
import org.jboss.reddeer.logparser.model.ParseRule;

public class ParseRuleNameSorter extends ViewerSorter {
	public int compare(Viewer viewer, Object e1, Object e2) {
		return ((ParseRule) e1).getName().compareToIgnoreCase(((ParseRule) e2).getName());
	}
}
