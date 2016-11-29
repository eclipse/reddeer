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
package org.jboss.reddeer.core.condition;

import org.eclipse.swt.widgets.Widget;
import org.jboss.reddeer.common.condition.AbstractWaitCondition;

/**
 * Wait for widget disposal
 * @author rawagner
 *
 */
public class WidgetIsDisposed extends AbstractWaitCondition{
	
	private Widget widget;
	
	public WidgetIsDisposed(Widget widget) {
		this.widget = widget;
	}

	@Override
	public boolean test() {
		return widget.isDisposed();
	}
	
	@Override
	public String description() {
		return "widget is disposed";
	}

}
