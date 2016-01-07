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

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Control;
import org.jboss.reddeer.common.logging.Logger;
import org.jboss.reddeer.common.wait.WaitUntil;
import org.jboss.reddeer.common.wait.WaitWhile;
import org.jboss.reddeer.core.condition.ShellWithTextIsAvailable;
import org.jboss.reddeer.core.handler.ShellHandler;
import org.jboss.reddeer.core.handler.WidgetHandler;
import org.jboss.reddeer.core.util.DiagnosticTool;
import org.jboss.reddeer.swt.api.Shell;
import org.jboss.reddeer.swt.condition.ShellIsActive;
import org.jboss.reddeer.swt.exception.SWTLayerException;
import org.jboss.reddeer.swt.impl.button.CancelButton;

/**
 * Abstract class for all Shells
 * 
 * @author Jiri Peterka
 * 
 */
public abstract class AbstractShell implements Shell {

	private static final Logger log = Logger.getLogger(AbstractShell.class);

	protected org.eclipse.swt.widgets.Shell swtShell;

	protected AbstractShell(org.eclipse.swt.widgets.Shell swtShell) {
		if (swtShell != null) {
			this.swtShell = swtShell;
		} else {
			String exceptionText = new DiagnosticTool().getShellsDiagnosticInformation();
			throw new SWTLayerException("SWT Shell passed to constructor is null. " + exceptionText);
		}
	}

	/**
	 * Gets the control.
	 *
	 * @return the control
	 */
	@Override
	public Control getControl() {
		return swtShell;
	}

	/* (non-Javadoc)
	 * @see org.jboss.reddeer.swt.api.Shell#getText()
	 */
	@Override
	public String getText() {
		String text = WidgetHandler.getInstance().getText(swtShell);
		return text;
	}

	/* (non-Javadoc)
	 * @see org.jboss.reddeer.swt.api.Shell#setFocus()
	 */
	@Override
	public void setFocus() {
		log.debug("Set focus to Shell " + getText());
		WidgetHandler.getInstance().setFocus(swtShell);
		new WaitUntil(new ShellIsActive(this));
	}
	
	/* (non-Javadoc)
	 * @see org.jboss.reddeer.swt.api.Shell#isVisible()
	 */
	@Override
	public boolean isVisible(){
		return ShellHandler.getInstance().isVisible(swtShell);
	}
	
	/* (non-Javadoc)
	 * @see org.jboss.reddeer.swt.api.Shell#isFocused()
	 */
	@Override
	public boolean isFocused() {
		return ShellHandler.getInstance().isFocused(swtShell);
	}

	/* (non-Javadoc)
	 * @see org.jboss.reddeer.swt.api.Shell#close()
	 */
	@Override
	public void close() {
		String text = getText();
		log.info("Close shell " + text);
		try {
			new CancelButton().click();
		} catch (Exception e) {
			WidgetHandler.getInstance().notify(SWT.Close, swtShell);
			ShellHandler.getInstance().closeShell(swtShell);
		}
		new WaitWhile(new ShellWithTextIsAvailable(text));
	}

	/* (non-Javadoc)
	 * @see org.jboss.reddeer.swt.api.Shell#getSWTWidget()
	 */
	public org.eclipse.swt.widgets.Shell getSWTWidget() {
		return swtShell;
	}

	/* (non-Javadoc)
	 * @see org.jboss.reddeer.swt.widgets.Widget#isEnabled()
	 */
	@Override
	public boolean isEnabled() {
		return WidgetHandler.getInstance().isEnabled(swtShell);
	}
	
	/* (non-Javadoc)
	 * @see org.jboss.reddeer.swt.widgets.Widget#isDisposed()
	 */
	@Override
	public boolean isDisposed() {
		return WidgetHandler.getInstance().isDisposed(swtShell);
	}

}
