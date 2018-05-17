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
package org.eclipse.reddeer.swt.widgets;

import org.hamcrest.Matcher;
import org.eclipse.reddeer.core.handler.WidgetHandler;
import org.eclipse.reddeer.core.lookup.WidgetLookup;
import org.eclipse.reddeer.core.reference.ReferencedComposite;
import org.eclipse.reddeer.swt.api.Widget;
import org.eclipse.reddeer.swt.exception.SWTLayerException;

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
	 * Instantiates a new RedDeer widget. If widget is null diagnostics runnable is run
	 * @param swtWidget swt widget to encapsulate
	 * @param diagnostics to run if swt widget is null
	 */
	protected AbstractWidget(T swtWidget, Runnable diagnostics){
		if (swtWidget == null){
			if(diagnostics != null){
				diagnostics.run();
			}
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
		if(refComposite != null && WidgetHandler.getInstance().isDisposed(refComposite.getControl())){
			throw new SWTLayerException("Given referenced composite is disposed");
		}
		swtWidget = WidgetLookup.getInstance().activeWidget(refComposite, widgetClass, index, matchers);
	}
	
	@Override
	public T getSWTWidget() {
		return swtWidget;
	}
	
	@Override
	public boolean isDisposed() {
		return WidgetHandler.getInstance().isDisposed(swtWidget);
	}
}
