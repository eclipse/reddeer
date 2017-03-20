/******************************************************************************* 
 * Copyright (c) 2017 Red Hat, Inc. 
 * Distributed under license by Red Hat, Inc. All rights reserved. 
 * This program is made available under the terms of the 
 * Eclipse Public License v1.0 which accompanies this distribution, 
 * and is available at http://www.eclipse.org/legal/epl-v10.html 
 * 
 * Contributors: 
 * Red Hat, Inc. - initial API and implementation 
 ******************************************************************************/ 
package org.jboss.reddeer.eclipse.topmenu;

import org.hamcrest.Matcher;
import org.jboss.reddeer.core.matcher.WithTextMatcher;
import org.jboss.reddeer.eclipse.ui.dialogs.ImportExportWizard;
import org.jboss.reddeer.jface.wizard.WizardDialog;
import org.jboss.reddeer.swt.api.Shell;
import org.jboss.reddeer.swt.impl.tree.DefaultTreeItem;
import org.jboss.reddeer.workbench.api.TopMenuOpenable;

/**
 * Represents wizard which can be found in import wizard dialog
 * @author rawagner
 *
 */
public abstract class ImportMenuWizard extends WizardDialog implements TopMenuOpenable{
	
	protected Matcher<String> matcher;
	private String[] path;
	
	/**
	 * Constructor set path to the specific item in new wizard dialog.
	 * @param text dialog text
	 * @param path to the specific item in new wizard dialog
	 */
	public ImportMenuWizard(String text, String... path) {
		super((Shell)null);
		this.matcher = new WithTextMatcher(text);
		this.path = path;
	}
	
	@Override
	public void open(){
		if(!isOpen()){
			ImportExportWizard ieWizard = new ImportExportWizard(true);
			ieWizard.open();
			new DefaultTreeItem(path).select();
			ieWizard.next();
			setShell(new WizardDialog(matcher).getShell());
		}
	}
	
	@Override
	public Matcher<String> getShellMatcher() {
		return matcher;
	}
	
	@Override
	public String[] getMenuPath(){
		return path;
	}

}
