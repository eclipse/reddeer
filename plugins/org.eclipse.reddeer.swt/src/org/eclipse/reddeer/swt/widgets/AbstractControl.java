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
package org.eclipse.reddeer.swt.widgets;

import org.hamcrest.Matcher;
import org.eclipse.reddeer.core.handler.ControlHandler;
import org.eclipse.reddeer.core.reference.ReferencedComposite;
import org.eclipse.reddeer.swt.api.Control;

/**
 * Abstract class for all control 
 * @author rawagner
 *
 * @param <T> extends swt control
 */
public abstract class AbstractControl<T extends org.eclipse.swt.widgets.Control> extends AbstractWidget<T> implements Control<T>{
	
	/**
	 * Finds control of specified class, within referenced composite with defined index and matching all matchers
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
	 * Constructs reddeer control with specified swt control
	 * @param swtControl instance of swt control
	 */
	protected AbstractControl(T swtControl) {
		super(swtControl);
		setFocus();
	}
	
	/**
	 * Constructs reddeer control with specified swt control, if control is null diagnostics runnable is run 
	 * (to provide additional debug output etc)
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
