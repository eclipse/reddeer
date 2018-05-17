/*******************************************************************************
 * Copyright (c) 2017 Red Hat, Inc and others.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     Red Hat, Inc - initial API and implementation
 *******************************************************************************/
package org.eclipse.reddeer.core.condition;

import org.eclipse.swt.widgets.Widget;
import org.eclipse.reddeer.common.condition.AbstractWaitCondition;

/**
 * Wait for widget disposal
 * @author rawagner
 *
 */
public class WidgetIsDisposed extends AbstractWaitCondition {
	
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
