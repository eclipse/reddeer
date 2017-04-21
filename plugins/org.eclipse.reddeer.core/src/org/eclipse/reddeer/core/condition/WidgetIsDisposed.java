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
package org.eclipse.reddeer.core.condition;

import org.eclipse.swt.widgets.Widget;
import org.eclipse.reddeer.common.condition.AbstractWaitCondition;

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
