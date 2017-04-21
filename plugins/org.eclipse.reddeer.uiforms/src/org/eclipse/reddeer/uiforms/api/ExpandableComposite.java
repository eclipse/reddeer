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
package org.eclipse.reddeer.uiforms.api;

import org.eclipse.reddeer.core.reference.ReferencedComposite;
import org.eclipse.reddeer.swt.api.Control;

/**
 * Represents {@link org.eclipse.ui.forms.widgets.ExpandableComposite} object. 
 * 
 * @author Radoslav Rabara
 *
 */
public interface ExpandableComposite extends ReferencedComposite, Control<org.eclipse.ui.forms.widgets.ExpandableComposite> {

	/**
	 * Returns the title string.
	 * @return the title string
	 */
	String getText();

	/**
	 * Sets the expansion state of the composite.
	 * 
	 * @param expanded the new expanded state
	 */
	void setExpanded(boolean expanded);

	/**
	 * Returns the expansion state of the composite.
	 * @return <code>true</code> if expanded, <code>false</code> if collapsed.
	 */
	boolean isExpanded();
}
