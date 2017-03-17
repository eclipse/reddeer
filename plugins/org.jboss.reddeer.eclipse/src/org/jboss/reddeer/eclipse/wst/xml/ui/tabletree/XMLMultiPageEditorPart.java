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
package org.jboss.reddeer.eclipse.wst.xml.ui.tabletree;

import org.hamcrest.Matcher;
import org.jboss.reddeer.core.matcher.WithTextMatcher;

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
