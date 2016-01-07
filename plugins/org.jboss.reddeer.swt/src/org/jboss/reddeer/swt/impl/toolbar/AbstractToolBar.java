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
package org.jboss.reddeer.swt.impl.toolbar;

import org.eclipse.swt.widgets.Control;
import org.hamcrest.Matcher;
import org.jboss.reddeer.swt.api.ToolBar;
import org.jboss.reddeer.swt.widgets.AbstractWidget;
import org.jboss.reddeer.core.reference.ReferencedComposite;

/**
 * Abstract class for all Toolbar implementations
 * @author Jiri Peterka
 *
 */
public abstract class AbstractToolBar extends AbstractWidget<org.eclipse.swt.widgets.ToolBar> implements ToolBar{

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
