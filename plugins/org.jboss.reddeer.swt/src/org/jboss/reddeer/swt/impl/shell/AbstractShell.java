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
package org.jboss.reddeer.swt.impl.shell;

import org.eclipse.swt.widgets.Control;
import org.jboss.reddeer.common.logging.Logger;
import org.jboss.reddeer.common.wait.WaitUntil;
import org.jboss.reddeer.common.wait.WaitWhile;
import org.jboss.reddeer.core.handler.ShellHandler;
import org.jboss.reddeer.core.util.DiagnosticTool;
import org.jboss.reddeer.swt.api.Shell;
import org.jboss.reddeer.swt.condition.ShellIsActive;
import org.jboss.reddeer.swt.condition.ShellIsAvailable;
import org.jboss.reddeer.swt.widgets.AbstractControl;

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
		setFocus();
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
	 * @see org.jboss.reddeer.swt.api.Shell#getText()
	 */
	@Override
	public String getText() {
		String text = ShellHandler.getInstance().getText(swtWidget);
		return text;
	}

	/* (non-Javadoc)
	 * @see org.jboss.reddeer.swt.api.Shell#setFocus()
	 */
	@Override
	public void setFocus() {
		log.debug("Set focus to Shell " + getText());
		ShellHandler.getInstance().setFocus(swtWidget);
		new WaitUntil(new ShellIsActive(this));
	}

	/* (non-Javadoc)
	 * @see org.jboss.reddeer.swt.api.Shell#close()
	 */
	@Override
	public void close() {
		String text = getText();
		log.info("Close shell " + text);
		ShellHandler.getInstance().closeShell(swtWidget);
		new WaitWhile(new ShellIsAvailable(this));
	}
	
	/* (non-Javadoc)
	 * @see @see org.jboss.reddeer.swt.api.Shell#isMaximized()
	 */
	@Override
	public boolean isMaximized() {
		return ShellHandler.getInstance().isMaximized(swtWidget);
	}
	
	/* (non-Javadoc)
	 * @see @see org.jboss.reddeer.swt.api.Shell#isMinimized()
	 */
	@Override
	public boolean isMinimized() {
		return ShellHandler.getInstance().isMinimized(swtWidget);
	}
	
	/* (non-Javadoc)
	 * @see @see org.jboss.reddeer.swt.api.Shell#maximize()
	 */
	@Override
	public void maximize() {
		ShellHandler.getInstance().maximize(swtWidget);
	}
	
	/* (non-Javadoc)
	 * @see org.jboss.reddeer.swt.api.Shell#minimize()
	 */
	@Override
	public void minimize() {
		ShellHandler.getInstance().minimize(swtWidget);
	}
	
	/* (non-Javadoc)
	 * @see org.jboss.reddeer.swt.api.Shell#restore()
	 */
	@Override
	public void restore() {
		ShellHandler.getInstance().restore(swtWidget);
	}

}
