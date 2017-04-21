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
package org.eclipse.reddeer.uiforms.impl.section;

import org.eclipse.swt.widgets.Control;
import org.hamcrest.Matcher;
import org.eclipse.reddeer.core.reference.ReferencedComposite;
import org.eclipse.reddeer.swt.widgets.AbstractControl;
import org.eclipse.reddeer.uiforms.api.Section;
import org.eclipse.reddeer.uiforms.handler.SectionHandler;

/**
 * Common ancestor for all {@link Section} implementations. 
 * 
 * @author Lucia Jelinkova
 *
 */
public abstract class AbstractSection extends AbstractControl<org.eclipse.ui.forms.widgets.Section> implements Section {

	/**
	 * Instantiates a new abstract section.
	 *
	 * @param refComposite the ref composite
	 * @param index the index
	 * @param matchers the matchers
	 */
	protected AbstractSection(ReferencedComposite refComposite, int index, Matcher<?>... matchers){
		super(org.eclipse.ui.forms.widgets.Section.class, refComposite, index, matchers);
		setFocus();
	}
	
	protected AbstractSection(org.eclipse.ui.forms.widgets.Section widget){
		super(widget);
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.reddeer.core.reference.ReferencedComposite#getControl()
	 */
	public Control getControl() {
		return swtWidget;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.reddeer.uiforms.api.Section#getText()
	 */
	public String getText() {
		return SectionHandler.getInstance().getText(swtWidget);
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.reddeer.uiforms.api.Section#setExpanded(boolean)
	 */
	public void setExpanded(final boolean expanded) {
		SectionHandler.getInstance().setExpanded(swtWidget, expanded);
	}
}
