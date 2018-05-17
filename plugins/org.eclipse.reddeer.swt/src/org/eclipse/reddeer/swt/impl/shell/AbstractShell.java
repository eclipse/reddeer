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
package org.eclipse.reddeer.swt.impl.shell;

import org.eclipse.swt.widgets.Control;
import org.eclipse.reddeer.common.logging.Logger;
import org.eclipse.reddeer.common.wait.WaitUntil;
import org.eclipse.reddeer.common.wait.WaitWhile;
import org.eclipse.reddeer.core.handler.ShellHandler;
import org.eclipse.reddeer.core.util.DiagnosticTool;
import org.eclipse.reddeer.swt.api.Shell;
import org.eclipse.reddeer.swt.condition.ShellIsActive;
import org.eclipse.reddeer.swt.condition.ShellIsAvailable;
import org.eclipse.reddeer.swt.widgets.AbstractControl;

/**
 * Abstract class for all Shells
 * 
 * @author Jiri Peterka
 * 
 */
public abstract class AbstractShell extends AbstractControl<org.eclipse.swt.widgets.Shell> implements Shell {

	private static final Logger log = Logger.getLogger(AbstractShell.class);

	protected AbstractShell(org.eclipse.swt.widgets.Shell swtShell) {
		super(swtShell, () -> new DiagnosticTool().getShellsDiagnosticInformation());
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

	/* (non-Javadoc)
	 * @see org.eclipse.reddeer.swt.api.Shell#getText()
	 */
	@Override
	public String getText() {
		String text = ShellHandler.getInstance().getText(swtWidget);
		return text;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.reddeer.swt.api.Shell#setFocus()
	 */
	@Override
	public void setFocus() {
		log.debug("Set focus to Shell " + getText());
		ShellHandler.getInstance().setFocus(swtWidget);
		new WaitUntil(new ShellIsActive(this));
	}

	/* (non-Javadoc)
	 * @see org.eclipse.reddeer.swt.api.Shell#close()
	 */
	@Override
	public void close() {
		String text = getText();
		log.info("Close shell " + text);
		ShellHandler.getInstance().closeShell(swtWidget);
		new WaitWhile(new ShellIsAvailable(this));
	}
	
	/* (non-Javadoc)
	 * @see @see org.eclipse.reddeer.swt.api.Shell#isMaximized()
	 */
	@Override
	public boolean isMaximized() {
		return ShellHandler.getInstance().isMaximized(swtWidget);
	}
	
	/* (non-Javadoc)
	 * @see @see org.eclipse.reddeer.swt.api.Shell#isMinimized()
	 */
	@Override
	public boolean isMinimized() {
		return ShellHandler.getInstance().isMinimized(swtWidget);
	}
	
	/* (non-Javadoc)
	 * @see @see org.eclipse.reddeer.swt.api.Shell#maximize()
	 */
	@Override
	public void maximize() {
		ShellHandler.getInstance().maximize(swtWidget);
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.reddeer.swt.api.Shell#minimize()
	 */
	@Override
	public void minimize() {
		ShellHandler.getInstance().minimize(swtWidget);
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.reddeer.swt.api.Shell#restore()
	 */
	@Override
	public void restore() {
		ShellHandler.getInstance().restore(swtWidget);
	}

}
