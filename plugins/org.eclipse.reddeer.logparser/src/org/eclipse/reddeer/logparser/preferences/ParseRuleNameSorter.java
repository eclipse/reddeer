/*******************************************************************************
 * Copyright (c) 2017 Red Hat, Inc and others.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     Red Hat, Inc - initial API and implementation
 *******************************************************************************/
package org.eclipse.reddeer.logparser.preferences;

import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerSorter;
import org.eclipse.reddeer.logparser.model.ParseRule;

public class ParseRuleNameSorter extends ViewerSorter {
	public int compare(Viewer viewer, Object e1, Object e2) {
		return ((ParseRule) e1).getName().compareToIgnoreCase(((ParseRule) e2).getName());
	}
}
