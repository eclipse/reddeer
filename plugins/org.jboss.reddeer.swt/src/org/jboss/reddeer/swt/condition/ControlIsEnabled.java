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
package org.jboss.reddeer.swt.condition;

import org.jboss.reddeer.common.condition.AbstractWaitCondition;
import org.jboss.reddeer.swt.api.Control;

/**
 * Condition is met when specified control is enabled.
 * 
 * @author mlabuda@redhat.com
 *
 */
public class ControlIsEnabled extends AbstractWaitCondition {

	private Control<?> control;

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
		return control.isEnabled();
	}

	@Override
	public String description() {
		return "control is enabled";
	}

}
