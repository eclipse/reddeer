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
package org.eclipse.reddeer.swt.api;

import java.util.List;

/**
 * API for link manipulation.
 * 
 * @author Jiri Peterka, rhopp
 *
 */
public interface Link extends Control<org.eclipse.swt.widgets.Link> {

	/**
	 * Returns text of link stripped of &lt;a&gt; and &lt;/a&gt; tags.
	 * <br>
	 * For example "This is a &lt;a&gt;link&lt;/a&gt;" will result in
	 * "This is a link".
	 * 
	 * @return clean text of the link
	 */
	String getText();

	/**
	 * Returns array of anchor texts (text between &lt;a&gt; and &lt;/a&gt;).
	 * <br>
	 * For example "This is a &lt;a&gt;link&lt;/a&gt;" will result in "link".
	 * 
	 * @return list of texts between &lt;a&gt; and &lt;/a&gt;
	 */
	List<String> getAnchorTexts();

	/**
	 * Clicks on anchor with specified text within the link.
	 * 
	 * Link widget can have multiple anchors in itself. Example:
	 * "Link &lt;a&gt;link1&lt;/a&gt; and &lt;A href="test"&gt;link2&lt;A\&gt;"
	 * <br>
	 * 
	 * link.click(link1) invokes SWT.Selection event with "link1" as event's
	 * text.
	 * <br>
	 * link.click(link2) invokes SWT.Selection event with "test" as event's
	 * text.
	 * 
	 * @param text text of anchor to click on
	 */
	void click(String text);

	/**
	 * Clicks on first anchor of the link.
	 */
	void click();

	/**
	 * Clicks on the anchor with the specified index in the link.
	 * 
	 * @param index index of anchor
	 */
	void click(int index);
}
