/******************************************************************************* 
 * Copyright (c) 2017 Red Hat, Inc. 
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
import org.jboss.reddeer.core.handler.ControlHandler;
import org.jboss.reddeer.core.reference.ReferencedComposite;
import org.jboss.reddeer.swt.api.Control;

/**
 * Abstract class for all control 
 * @author rawagner
 *
 * @param <T> extends swt control
 */
public abstract class AbstractControl<T extends org.eclipse.swt.widgets.Control> extends AbstractWidget<T> implements Control<T>{
	
	protected ControlHandler controlHandler = ControlHandler.getInstance();
	
	protected AbstractControl(Class<T> widgetClass, ReferencedComposite refComposite, int index, Matcher<?>... matchers) {
		super(widgetClass,refComposite,index,matchers);
	}

	protected AbstractControl(T swtWidget) {
		super(swtWidget);
	}
	
	protected AbstractControl(T swtWidget, Runnable diagnostics) {
		super(swtWidget, diagnostics);
	}
	
	/**
	 * Finds out whether a widget is enabled.
	 * 
	 * @return true if widget is enabled, false otherwise
	 */
	public boolean isEnabled(){
		return controlHandler.isEnabled(swtWidget);
	}
	
	public boolean isVisible(){
		return controlHandler.isVisible(swtWidget);
	}
	
	public void setFocus(){
		controlHandler.setFocus(swtWidget);
	}
	
	public boolean isFocusControl(){
		return controlHandler.isFocusControl(swtWidget);
	}
	
	public String getToolTipText(){
		return controlHandler.getToolTipText(swtWidget);
	}

}
