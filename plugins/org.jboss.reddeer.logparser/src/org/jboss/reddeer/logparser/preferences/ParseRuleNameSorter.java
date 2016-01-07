/******************************************************************************* 
 * Copyright (c) 2016 Red Hat, Inc. 
 * Distributed under license by Red Hat, Inc. All rights reserved. 
 * This program is made available under the terms of the 
 * Eclipse Public License v1.0 which accompanies this distribution, 
 * and is available at http://www.eclipse.org/legal/epl-v10.html 
 * 
 * Contributors: 
 * Red Hat, Inc. - initial API and implementation 
 ******************************************************************************/ 
package org.jboss.reddeer.logparser.preferences;

import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerSorter;
import org.jboss.reddeer.logparser.model.ParseRule;

public class ParseRuleNameSorter extends ViewerSorter {
	public int compare(Viewer viewer, Object e1, Object e2) {
		return ((ParseRule) e1).getName().compareToIgnoreCase(((ParseRule) e2).getName());
	}
}
