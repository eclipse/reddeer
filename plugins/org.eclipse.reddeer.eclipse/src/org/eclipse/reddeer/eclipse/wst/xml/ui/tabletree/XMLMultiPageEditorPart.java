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
package org.eclipse.reddeer.eclipse.wst.xml.ui.tabletree;

import org.hamcrest.Matcher;
import org.eclipse.reddeer.core.matcher.WithTextMatcher;

/**
 * Represents an XML editor with Design and Source pages.
 * 
 * @author Lucia Jelinkova
 *
 */
public class XMLMultiPageEditorPart extends AbstractXMLEditor {

	/**
	 * Find XMLMultiPageEditorPart with the given title.
	 *
	 * @param title the title
	 */
	public XMLMultiPageEditorPart(String title) {
		this(new WithTextMatcher(title));
	}
	
	/**
	 * Find XMLMultiPageEditorPart with the given title matcher.
	 *
	 * @param titleMatcher the title matcher
	 */
	@SuppressWarnings("unchecked")
	public XMLMultiPageEditorPart(Matcher<String> titleMatcher) {
		super(titleMatcher, "org.eclipse.wst.xml.ui.internal.tabletree.XMLMultiPageEditorPart");
	}
}
