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
package org.jboss.reddeer.swt.widgets;

import org.hamcrest.Matcher;
import org.jboss.reddeer.core.handler.WidgetHandler;
import org.jboss.reddeer.core.lookup.WidgetLookup;
import org.jboss.reddeer.core.reference.ReferencedComposite;
import org.jboss.reddeer.swt.exception.SWTLayerException;

/**
 * Abstract widget implementation that looks up and stores swt widget. 
 *  
 * @author Lucia Jelinkova
 * 
 */
public abstract class AbstractWidget<T extends org.eclipse.swt.widgets.Widget> implements Widget<T> {

	protected T swtWidget;
	
	/**
	 * Instantiates a new RedDeer widget.
	 * 
	 * @param swtWidget swt widget to encapsulate
	 */
	protected AbstractWidget(T swtWidget) {
		if (swtWidget == null){
			throw new SWTLayerException("SWT widget provided is null");
		}
		if (swtWidget.isDisposed()){
			throw new SWTLayerException("SWT widget provided is disposed");
		}
		this.swtWidget = swtWidget;
	}
	
	/**
	 * Instantiate a new RedDeer widget.
	 * 
	 * @param widgetClass eclipse SWT widget class
	 * @param refComposite referenced composite
	 * @param index index of widget
	 * @param matchers matchers to match widget
	 */
	protected AbstractWidget(Class<T> widgetClass, ReferencedComposite refComposite, int index, Matcher<?>... matchers) {
		swtWidget = WidgetLookup.getInstance().activeWidget(refComposite, widgetClass, index, matchers);
	}
	
	@Override
	public T getSWTWidget() {
		return swtWidget;
	}

	@Override
	public boolean isEnabled() {
		return WidgetHandler.getInstance().isEnabled(swtWidget);
	}
	
	@Override
	public boolean isDisposed() {
		return WidgetHandler.getInstance().isDisposed(swtWidget);
	}
}
