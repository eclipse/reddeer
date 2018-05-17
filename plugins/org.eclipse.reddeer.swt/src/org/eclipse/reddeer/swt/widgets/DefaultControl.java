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
 * Default class for all control 
 * @author lvalach
 *
 * @param <T> extends swt control
 */
public class DefaultControl<T extends org.eclipse.swt.widgets.Control> extends AbstractControl<T> {
	
	/**
	 * Finds control of specified class, within referenced composite with defined index and matching all matchers
	 * @param controlClass class of control
	 * @param refComposite composite where control should be found
	 * @param index of control
	 * @param matchers matchers matching control
	 */
	public DefaultControl(Class<T> controlClass, ReferencedComposite refComposite, int index, Matcher<?>... matchers) {
		super(controlClass,refComposite,index,matchers);
	}

	/**
	 * Constructs reddeer control with specified swt control
	 * @param swtControl instance of swt control
	 */
	public DefaultControl(T swtControl) {
		super(swtControl);
	}
	
	/**
	 * Constructs reddeer control with specified swt control, if control is null diagnostics runnable is run 
	 * (to provide additional debug output etc)
	 * @param swtControl instance of swt control
	 * @param diagnostics diagnostics runnable to run if control is null
	 */
	public DefaultControl(T swtControl, Runnable diagnostics) {
		super(swtControl, diagnostics);
	}
}
