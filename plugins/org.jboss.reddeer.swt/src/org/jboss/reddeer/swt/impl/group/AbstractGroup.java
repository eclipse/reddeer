/******************************************************************************* 
 * Copyright (c) 2017 Red Hat, Inc. 
 * Distributed under license by Red Hat, Inc. All rights reserved. 
 * This program is made available under the terms of the 
 * Eclipse Public License v1.0 which accompanies this distribution, 
 * and is available at http://www.eclipse.org/legal/epl-v10.html 
 * 
 * Contributors: 
 * Red Hat, Inc. - initial API and implementation 
 ******************************************************************************/ 
package org.jboss.reddeer.swt.impl.group;

import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Group;
import org.hamcrest.Matcher;
import org.jboss.reddeer.core.handler.WidgetHandler;
import org.jboss.reddeer.core.reference.ReferencedComposite;
import org.jboss.reddeer.swt.widgets.AbstractWidget;

public abstract class AbstractGroup extends AbstractWidget<Group> implements org.jboss.reddeer.swt.api.Group{
	
	/**
	 * Group with given index inside given composite that matches given matchers.
	 *
	 * @param referencedComposite the referenced composite
	 * @param index group index
	 * @param matchers the matchers
	 */
	protected AbstractGroup(ReferencedComposite referencedComposite, int index, Matcher<?>... matchers){
		super(Group.class, referencedComposite, index, matchers);
	}
	
	protected AbstractGroup(Group widget){
		super(widget);
	}
	
	/* (non-Javadoc)
	 * @see org.jboss.reddeer.swt.api.Group#getText()
	 */
	@Override
	public String getText() {
		return WidgetHandler.getInstance().getText(swtWidget);
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
