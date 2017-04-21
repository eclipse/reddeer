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
package org.eclipse.reddeer.eclipse.debug.ui.views.launch;

import org.eclipse.reddeer.common.matcher.RegexMatcher;
import org.eclipse.reddeer.core.matcher.WithTooltipTextMatcher;
import org.eclipse.reddeer.eclipse.exception.EclipseLayerException;
import org.eclipse.reddeer.swt.impl.toolbar.DefaultToolItem;
import org.eclipse.reddeer.workbench.impl.shell.WorkbenchShell;

/**
 * Represents the debugger 'Terminate' button implemented as a tool item in the
 * workbench.
 * 
 * @author Andrej Podhradsky
 *
 */
public class TerminateButton extends DefaultToolItem {

	/**
	 * Constructs the Terminate button as a tool item in the workbench.
	 */
	public TerminateButton() {
		super(new WorkbenchShell(), new WithTooltipTextMatcher(new RegexMatcher("Terminate.*")));
	}

	/* (non-Javadoc)
	 * @see org.eclipse.reddeer.swt.impl.toolbar.AbstractToolItem#click()
	 */
	@Override
	public void click() {
		if (!isEnabled()) {
			throw new EclipseLayerException("Cannot click on 'Terminate' button, it is NOT enabled!");
		}
		super.click();
	}
}
