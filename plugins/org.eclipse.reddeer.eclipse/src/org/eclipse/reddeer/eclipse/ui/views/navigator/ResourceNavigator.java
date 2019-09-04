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
package org.eclipse.reddeer.eclipse.ui.views.navigator;

import java.util.regex.Pattern;

import org.eclipse.reddeer.common.matcher.RegexMatcher;
import org.eclipse.reddeer.core.matcher.WithTextMatcher;
import org.eclipse.reddeer.eclipse.ui.navigator.resources.AbstractExplorer;
import org.eclipse.reddeer.workbench.exception.WorkbenchLayerException;
import org.eclipse.reddeer.workbench.lookup.ViewLookup;

/**
 * Navigator RedDeer implementation
 * "The Navigator view provides a hierarchical view of the resources
 * in the Workbench including hidden files."
 * 
 * @author Radoslav Rabara, odockal
 *
 */
public class ResourceNavigator extends AbstractExplorer {
	
	private static final String NAVIGATOR_NAME;
	
	// resolve renaming of Navigator view in 4.13 to "Navigator (Deprecated)"
	// see https://bugs.eclipse.org/bugs/show_bug.cgi?id=549953
	static {
		String navigatorName = "Navigator";
		WithTextMatcher matcher = new WithTextMatcher(new RegexMatcher("\\*?" + Pattern.quote(navigatorName)));
		try {
			ViewLookup.getInstance().findRegisteredViewPath(matcher);
		} catch (WorkbenchLayerException exc) {
			navigatorName = "Navigator (Deprecated)";
		}
		NAVIGATOR_NAME = navigatorName;
	}
	
	/**
	 * Construct the explorer with "Navigator (Deprecated)". 
	 */
	public ResourceNavigator() {
		super(NAVIGATOR_NAME);
	}
}
