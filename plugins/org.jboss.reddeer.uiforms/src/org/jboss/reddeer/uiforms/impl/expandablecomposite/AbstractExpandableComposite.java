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
package org.jboss.reddeer.uiforms.impl.expandablecomposite;

import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.forms.widgets.ExpandableComposite;
import org.hamcrest.Matcher;
import org.jboss.reddeer.core.handler.WidgetHandler;
import org.jboss.reddeer.core.reference.ReferencedComposite;
import org.jboss.reddeer.swt.widgets.AbstractWidget;
import org.jboss.reddeer.uiforms.handler.ExpandableCompositeHandler;

/**
 * Common ancestor for all
 * {@link org.jboss.reddeer.uiforms.api.ExpandableComposite} implementations.
 *
 * @author Radoslav Rabara
 * @since 0.6
 *
 */
public abstract class AbstractExpandableComposite extends AbstractWidget<ExpandableComposite> implements org.jboss.reddeer.uiforms.api.ExpandableComposite {

	protected AbstractExpandableComposite(ReferencedComposite refComposite, int index, Matcher<?>... matchers){
		super(ExpandableComposite.class, refComposite, index, matchers);
	}
	
	protected AbstractExpandableComposite(ExpandableComposite widget) {
		super(widget);
	}

	@Override
	public String getText() {
		return WidgetHandler.getInstance().getText(getSWTWidget());
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
