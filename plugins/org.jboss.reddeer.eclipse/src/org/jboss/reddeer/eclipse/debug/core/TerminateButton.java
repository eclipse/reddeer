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
package org.jboss.reddeer.eclipse.debug.core;

import org.jboss.reddeer.common.matcher.RegexMatcher;
import org.jboss.reddeer.core.matcher.WithTooltipTextMatcher;
import org.jboss.reddeer.eclipse.exception.EclipseLayerException;
import org.jboss.reddeer.swt.impl.toolbar.DefaultToolItem;
import org.jboss.reddeer.workbench.impl.shell.WorkbenchShell;

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
	 * @see org.jboss.reddeer.swt.impl.toolbar.AbstractToolItem#click()
	 */
	@Override
	public void click() {
		if (!isEnabled()) {
			throw new EclipseLayerException("Cannot click on 'Terminate' button, it is NOT enabled!");
		}
		super.click();
	}
}
