/*******************************************************************************
 * Copyright (c) 2017, 2018 Red Hat, Inc and others.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     Red Hat, Inc - initial API and implementation
 *******************************************************************************/
package org.eclipse.reddeer.swt.widgets;

import org.eclipse.reddeer.core.reference.ReferencedComposite;
import org.hamcrest.Matcher;

/**
 * Default widget implementation that looks up and stores swt widget. 
 *  
 * @author lvalach
 * 
 */
public class DefaultWidget<T extends org.eclipse.swt.widgets.Widget> extends AbstractWidget<T>{

	/**
	 * Instantiates a new RedDeer widget.
	 * 
	 * @param swtWidget swt widget to encapsulate
	 */
	public DefaultWidget(T swtWidget) {
		super(swtWidget);
	}	
	
	/**
	 * Instantiates a new RedDeer widget. If widget is null diagnostics runnable is run
	 * @param swtWidget swt widget to encapsulate
	 * @param diagnostics to run if swt widget is null
	 */
	public DefaultWidget(T swtWidget, Runnable diagnostics){
		super(swtWidget, diagnostics);
	}
	
	/**
	 * Instantiate a new RedDeer widget.
	 * 
	 * @param widgetClass eclipse SWT widget class
	 * @param refComposite referenced composite
	 * @param index index of widget
	 * @param matchers matchers to match widget
	 */
	public DefaultWidget(Class<T> widgetClass, ReferencedComposite refComposite, int index, Matcher<?>... matchers) {
		super(widgetClass, refComposite, index, matchers);
	}

}
