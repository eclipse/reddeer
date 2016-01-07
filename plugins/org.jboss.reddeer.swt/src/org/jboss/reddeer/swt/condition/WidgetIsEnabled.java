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
import org.jboss.reddeer.swt.widgets.Widget;

/**
 * Condition is met when specified widget is enabled.
 * 
 * @author mlabuda@redhat.com
 *
 */
public class WidgetIsEnabled extends AbstractWaitCondition {

	private Widget widget;

	/**
	 * Constructs WidgetIsEnabled wait condition.
	 * Condition is met when specified widget is enabled.
	 * 
	 * @param widget widget which should be enabled to let the condition pass
	 */
	public WidgetIsEnabled(Widget widget) {
		this.widget = widget;
	}

	@Override
	public boolean test() {
		return widget.isEnabled();
	}

	@Override
	public String description() {
		return "widget is enabled";
	}

}
