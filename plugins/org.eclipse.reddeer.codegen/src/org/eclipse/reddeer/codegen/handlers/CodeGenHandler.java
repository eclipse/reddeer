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
package org.eclipse.reddeer.codegen.handlers;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.jface.dialogs.IPageChangedListener;
import org.eclipse.jface.dialogs.IPageChangingListener;
import org.eclipse.jface.dialogs.PageChangedEvent;
import org.eclipse.jface.dialogs.PageChangingEvent;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.INewWizard;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.handlers.HandlerUtil;
import org.eclipse.reddeer.codegen.CodeGenerator;
import org.eclipse.reddeer.codegen.wizards.CodeGenWizard;
import org.eclipse.reddeer.codegen.wizards.FirstPage;
import org.eclipse.reddeer.codegen.wizards.MethodsPage;
import org.eclipse.reddeer.codegen.wizards.PreviewPage;
import org.eclipse.reddeer.common.logging.Logger;
import org.eclipse.reddeer.core.lookup.ShellLookup;

/**
 * Handler for RedDeer Code Generator wizard pages
 * 
 * @author djelinek
 */
public class CodeGenHandler extends AbstractHandler {

	private final Logger log = Logger.getLogger(CodeGenHandler.class);

	private Shell lastActiveShell;

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		lastActiveShell = ShellLookup.getInstance().getCurrentActiveShell();
		IWorkbenchWindow window = HandlerUtil.getActiveWorkbenchWindowChecked(event);
		openCodeGen(window);
		window.getShell().getDisplay().addFilter(SWT.KeyDown, new Listener() {
			public void handleEvent(Event e) {
				if ((e.stateMask == SWT.ALT) && (e.keyCode == 'g')) {
					log.info("Shortcut 'ALT + G' was captured.");
					openCodeGen(window);
				}
			}
		});
		return null;
	}

	/**
	 * Method for open RedDeer CodeGen wizard and runs all necessary listeners
	 * 
	 * @param window
	 */
	private void openCodeGen(IWorkbenchWindow window) {

		log.info("Trying to open CodeGen wizard.");
		Shell[] shell = ShellLookup.getInstance().getShells();
		for (Shell sh : shell) {
			if (sh.getText() == CodeGenWizard.WIZZARD_NAME)
				return;
		}
		INewWizard wizard = new CodeGenWizard();
		WizardDialog dialog = new WizardDialog(window.getShell(), wizard);
		dialog.setMinimumPageSize(150, 350);

		dialog.addPageChangedListener(new IPageChangedListener() {

			@Override
			public void pageChanged(PageChangedEvent event) {
				log.info("Page changed listener was started.");
				Object selected = event.getSelectedPage();
				if (selected instanceof PreviewPage) {
					PreviewPage prev = ((PreviewPage) selected);
					log.debug("Active page -> 'PreviewPage'.");
					dialog.updateButtons();
				} else if (selected instanceof MethodsPage) {
					log.debug("Active page -> 'MethodsPage'.");
				} else if (selected instanceof FirstPage) {
					log.debug("Active page -> 'FirstPage'.");
					((FirstPage) selected).dialogChanged();
					dialog.updateButtons();
				}
			}
		});

		dialog.addPageChangingListener(new IPageChangingListener() {

			@Override
			public void handlePageChanging(PageChangingEvent event) {
				log.info("Page changing listener was started.");
				Object current = event.getCurrentPage();
				Object target = event.getTargetPage();
				if (current instanceof MethodsPage && target instanceof PreviewPage) {
					log.debug("Switching between 'MethodsPage' -> 'PreviewPage'.");
					MethodsPage meth = ((MethodsPage) current);
					PreviewPage prev = ((PreviewPage) target);
					log.info("Trying to generate code.");
					CodeGenerator g = new CodeGenerator(meth.getClassBuilder().getClassName(),
							meth.getClassBuilder().getPackageName(), meth.getSelectedOptional());
					log.info("Trying to update text area in 'PreviewPage'.");
					g.setLastActiveShell(lastActiveShell);
					prev.updateAreaContent(g.generateCode());
				}

			}
		});

		log.info("Opening WizardDialog -> " + wizard.getWindowTitle() + "...");
		dialog.open();
	}
}
