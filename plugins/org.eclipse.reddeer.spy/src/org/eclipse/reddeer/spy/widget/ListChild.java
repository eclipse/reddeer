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
package org.eclipse.reddeer.spy.widget;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Widget;

/**
 * List child is a child from {@link org.eclipse.swt.widgets.List} widget. This is workaround for 
 * getting children of a list, because list returns such items as strings not as widgets.
 * 
 * @author mlabuda@redhat.com
 * @since 0.8.0
 */
public class ListChild extends Widget {

	private String text;
	
	/**
	 * Creates a new ListChild with specified text.
	 * 
	 * @param parent parent of list child
	 * @param text text of child
	 */
	public ListChild(Widget parent, String text) {
		super(parent, SWT.NONE);
		this.text = text;
	}
	
	@Override
	public void checkSubclass() {
		// DO NOTHING, WORKAROUND FOR "Subclassing not allowed"
	}
	
	/**
	 * Gets text of list child.
	 * @return text of list child
	 */
	public String getText() {
		return text;
	}
}
