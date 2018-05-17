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
package org.eclipse.reddeer.swt.impl.group;

import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Group;
import org.hamcrest.Matcher;
import org.eclipse.reddeer.core.handler.GroupHandler;
import org.eclipse.reddeer.core.reference.ReferencedComposite;
import org.eclipse.reddeer.swt.widgets.AbstractControl;

public abstract class AbstractGroup extends AbstractControl<Group> implements org.eclipse.reddeer.swt.api.Group{
	
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
	 * @see org.eclipse.reddeer.swt.api.Group#getText()
	 */
	@Override
	public String getText() {
		return GroupHandler.getInstance().getText(swtWidget);
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
