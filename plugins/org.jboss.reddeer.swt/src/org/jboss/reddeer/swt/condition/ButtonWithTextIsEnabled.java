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
import org.jboss.reddeer.swt.api.Button;

/**
 * Condition is met when button with specified text is enabled.
 * 
 * @author Vlado Pakan
 * @author Len DiMaggio
 * @deprecated since 0.8.0. Use {@link WidgetIsEnabled} instead.
 */
public class ButtonWithTextIsEnabled extends AbstractWaitCondition {

	private Button button;

	/**
	 * Constructs ButtonWithTextIsEnabled wait condition.
	 * Condition is met when specified button is active.
	 * 
	 * @param button button to check
	 */
	public ButtonWithTextIsEnabled(Button button) {
		this.button = button;
	}

	@Override
	public boolean test() {
		return button.isEnabled();
	}

	@Override
	public String description() {
		return "button with text '" + button.getText() + " is enabled";
	}

}
