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
