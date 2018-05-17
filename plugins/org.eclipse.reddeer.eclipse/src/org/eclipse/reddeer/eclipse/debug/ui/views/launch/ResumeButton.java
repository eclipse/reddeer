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
package org.eclipse.reddeer.eclipse.debug.ui.views.launch;

import org.eclipse.reddeer.common.matcher.RegexMatcher;
import org.eclipse.reddeer.core.matcher.WithTooltipTextMatcher;
import org.eclipse.reddeer.eclipse.exception.EclipseLayerException;
import org.eclipse.reddeer.swt.impl.toolbar.DefaultToolItem;
import org.eclipse.reddeer.workbench.impl.shell.WorkbenchShell;

/**
 * Represents the debugger 'Resume' button implemented as a tool item in the
 * workbench.
 * 
 * @author Andrej Podhradsky
 *
 */
public class ResumeButton extends DefaultToolItem {

	/**
	 * Constructs the Resume button as a tool item in the workbench.
	 */
	public ResumeButton() {
		super(new WorkbenchShell(), new WithTooltipTextMatcher(new RegexMatcher("Resume.*")));
	}

	/* (non-Javadoc)
	 * @see org.eclipse.reddeer.swt.impl.toolbar.AbstractToolItem#click()
	 */
	@Override
	public void click() {
		if (!isEnabled()) {
			throw new EclipseLayerException("Cannot click on 'Resume' button, it is NOT enabled!");
		}
		super.click();
	}

}
