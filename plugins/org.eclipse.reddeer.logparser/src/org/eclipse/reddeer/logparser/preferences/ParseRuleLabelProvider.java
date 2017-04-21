/*******************************************************************************
 * Copyright (c) 2017 Red Hat, Inc and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Red Hat Inc. - initial API and implementation
 *******************************************************************************/
package org.eclipse.reddeer.logparser.preferences;

import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;
import org.eclipse.reddeer.logparser.model.ParseRule;

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