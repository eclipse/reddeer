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
package org.eclipse.reddeer.swt.condition;

import org.eclipse.reddeer.common.condition.AbstractWaitCondition;
import org.eclipse.reddeer.swt.api.Control;

/**
 * Condition is met when specified control is enabled.
 * 
 * @author mlabuda@redhat.com
 *
 */
public class ControlIsEnabled extends AbstractWaitCondition {

	private Control<?> control;
	private Control<?> resultControl;
	
	/**
	 * Constructs ControltIsEnabled wait condition.
	 * Condition is met when specified control is enabled.
	 * 
	 * @param control control which should be enabled to let the condition pass
	 */
	public ControlIsEnabled(Control<?> control) {
		this.control = control;
	}

	@Override
	public boolean test() {
		if (control.isEnabled()) {
			this.resultControl = control;
			return true;
		}
		return false;
	}

	@Override
	public String description() {
		return "control is enabled";
	}
	
	@SuppressWarnings("unchecked")
	@Override 
	public Control<?> getResult() {
		return this.resultControl;
	}

}
