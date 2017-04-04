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
	
	/**
	 * Finds control of specified class, within referenced composite with defined index and matching all matchers
	 * Control is focused afterwards
	 * @param controlClass class of control
	 * @param refComposite composite where control should be found
	 * @param index of control
	 * @param matchers matchers matching control
	 */
	protected AbstractControl(Class<T> controlClass, ReferencedComposite refComposite, int index, Matcher<?>... matchers) {
		super(controlClass,refComposite,index,matchers);
		setFocus();
	}

	/**
	 * Constructs reddeer control with specified swt control. Control is focused afterwards
	 * @param swtControl instance of swt control
	 */
	protected AbstractControl(T swtControl) {
		super(swtControl);
		setFocus();
	}
	
	/**
	 * Constructs reddeer control with specified swt control. Control is focused afterwards.
	 * If control is null diagnostics runnable is run (to provide additional debug output etc)
	 * @param swtControl instance of swt control
	 * @param diagnostics diagnostics runnable to run if control is null
	 */
	protected AbstractControl(T swtControl, Runnable diagnostics) {
		super(swtControl, diagnostics);
		setFocus();
	}
	
	@Override
	public boolean isEnabled(){
		return ControlHandler.getInstance().isEnabled(swtWidget);
	}
	
	@Override
	public boolean isVisible(){
		return ControlHandler.getInstance().isVisible(swtWidget);
	}
	
	@Override
	public void setFocus(){
		ControlHandler.getInstance().setFocus(swtWidget);
	}
	
	@Override
	public boolean isFocusControl(){
		return ControlHandler.getInstance().isFocusControl(swtWidget);
	}
	
	@Override
	public String getToolTipText(){
		return ControlHandler.getInstance().getToolTipText(swtWidget);
	}

}
