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
package org.eclipse.reddeer.uiforms.impl.expandablecomposite;

import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.forms.widgets.ExpandableComposite;
import org.hamcrest.Matcher;
import org.eclipse.reddeer.core.reference.ReferencedComposite;
import org.eclipse.reddeer.swt.widgets.AbstractControl;
import org.eclipse.reddeer.uiforms.handler.ExpandableCompositeHandler;

/**
 * Common ancestor for all
 * {@link org.eclipse.reddeer.uiforms.api.ExpandableComposite} implementations.
 *
 * @author Radoslav Rabara
 * @since 0.6
 *
 */
public abstract class AbstractExpandableComposite extends AbstractControl<ExpandableComposite> implements org.eclipse.reddeer.uiforms.api.ExpandableComposite {

	protected AbstractExpandableComposite(ReferencedComposite refComposite, int index, Matcher<?>... matchers){
		super(ExpandableComposite.class, refComposite, index, matchers);
	}
	
	protected AbstractExpandableComposite(ExpandableComposite widget) {
		super(widget);
	}

	@Override
	public String getText() {
		return ExpandableCompositeHandler.getInstance().getText(getSWTWidget());
	}

	@Override
	public Control getControl() {
		return getSWTWidget();
	}

	@Override
	public void setExpanded(boolean expanded) {
		ExpandableCompositeHandler.getInstance()
				.setExpanded(getSWTWidget(), expanded);
	}

	@Override
	public boolean isExpanded() {
		return ExpandableCompositeHandler.getInstance().isExpanded(getSWTWidget());
	}
}
