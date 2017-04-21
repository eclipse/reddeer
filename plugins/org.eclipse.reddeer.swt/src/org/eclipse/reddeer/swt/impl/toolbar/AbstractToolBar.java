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
package org.eclipse.reddeer.swt.impl.toolbar;

import org.eclipse.swt.widgets.Control;
import org.hamcrest.Matcher;
import org.eclipse.reddeer.swt.api.ToolBar;
import org.eclipse.reddeer.swt.widgets.AbstractControl;
import org.eclipse.reddeer.core.reference.ReferencedComposite;

/**
 * Abstract class for all Toolbar implementations
 * @author Jiri Peterka
 *
 */
public abstract class AbstractToolBar extends AbstractControl<org.eclipse.swt.widgets.ToolBar> implements ToolBar{

	protected AbstractToolBar(org.eclipse.swt.widgets.ToolBar widget) {
		super(widget);
	}
	
		
	protected AbstractToolBar(ReferencedComposite refComposite, int index, Matcher<?>... matchers) {
		super(org.eclipse.swt.widgets.ToolBar.class, refComposite, index, matchers);
	}
	
	/**
	 * Gets the control.
	 *
	 * @return the control
	 */
	@Override
	public Control getControl() {
		return swtWidget;
	}
}
