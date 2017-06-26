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
package org.eclipse.reddeer.eclipse.wst.server.ui;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.reddeer.common.logging.Logger;
import org.eclipse.reddeer.common.wait.TimePeriod;
import org.eclipse.reddeer.common.wait.WaitUntil;
import org.eclipse.reddeer.common.wait.WaitWhile;
import org.eclipse.reddeer.core.reference.ReferencedComposite;
import org.eclipse.reddeer.eclipse.exception.EclipseLayerException;
import org.eclipse.reddeer.eclipse.wst.server.ui.wizard.NewRuntimeWizardDialog;
import org.eclipse.reddeer.jface.preference.PreferencePage;
import org.eclipse.reddeer.swt.api.Shell;
import org.eclipse.reddeer.swt.api.Table;
import org.eclipse.reddeer.swt.condition.ShellIsAvailable;
import org.eclipse.reddeer.swt.impl.button.OkButton;
import org.eclipse.reddeer.swt.impl.button.PushButton;
import org.eclipse.reddeer.swt.impl.shell.DefaultShell;
import org.eclipse.reddeer.swt.impl.table.DefaultTable;

/**
 * 
 * Preference page for managing server runtime environments.  
 * 
 * @author Lucia Jelinkova
 *
 */
public class RuntimePreferencePage extends PreferencePage {

	public static final String PAGE_NAME = "Runtime Environments";
	
	public static final String EDIT_BUTTON_NAME = "Edit...";

	private static final Logger log = Logger.getLogger(RuntimePreferencePage.class);
	
	/**
	 * Constructs the preference page with "Server" &gt; {@value #PAGE_NAME}.
	 */
	public RuntimePreferencePage(ReferencedComposite referencedComposite) {
		super(referencedComposite, new String[] {"Server", PAGE_NAME});
	}
	
	/**
	 * Return list of server runtimes.
	 * 
	 * @return List of server runtimes
	 */
	public List<Runtime> getServerRuntimes(){
		List<Runtime> runtimes = new ArrayList<Runtime>();
		
		Table table = new DefaultTable(referencedComposite);
		int rows = table.rowCount();
		
		for (int i = 0; i < rows; i++){
			Runtime runtime = new Runtime();
			runtime.setName(table.getItem(i).getText());
			runtime.setType(table.getItem(i).getText(1));
			runtimes.add(runtime);
		}
		return runtimes;
	}
	
	/**
	 * Removes a given runtime.
	 * 
	 * @param runtime Runtime
	 */
	public void removeRuntime(Runtime runtime){
		log.info("Removing runtime '" + runtime + "'");
		selectRuntime(runtime.getName());
		new PushButton(referencedComposite, "Remove").click();
		new WaitUntil(new ShellIsAvailable("Server"),TimePeriod.MEDIUM, false);
		if(new ShellIsAvailable("Server").test()){
			Shell serverShell = new DefaultShell("Server");
			new OkButton(serverShell).click();
			new WaitWhile(new ShellIsAvailable(serverShell));
		} else {
			log.debug("Server shell was not opened");
		}
	}
	
	/**
	 * Removes all runtimes.
	 */
	public void removeAllRuntimes(){
		log.info("Removing all runtimes");
		for (Runtime runtime : getServerRuntimes()){
			removeRuntime(runtime);
		}
	}
	
	private void selectRuntime(String name){
		
		Table table = new DefaultTable(referencedComposite);
		log.debug("Selecting runtime '" + name + "'");
		for (int i = 0; i < table.rowCount(); i++){
			String runtimeName = table.getItem(i).getText();
			log.debug("'" + runtimeName + "' was found");
			if (runtimeName.equals(name)){
				log.debug("'" + runtimeName + "' matched '" + name + "'! Selecting...");
				table.select(i);
				return;
			}
		}
		throw new EclipseLayerException("Unable to find runtime "+name);
	}
	
	/**
	 * Opens a wizard for adding new runtime.
	 * 
	 * @return Wizard for adding new runtime
	 */
	public NewRuntimeWizardDialog addRuntime(){
		log.info("Adding new runtime");
		new PushButton(referencedComposite, "Add...").click();
		new DefaultShell(NewRuntimeWizardDialog.DIALOG_TITLE);
		return new NewRuntimeWizardDialog();
	}
	
	/**
	 * Opens tie runtime's edit dialog. Since the dialog is specific for every runtime type
	 * it is upon user to instantiate the concrete RedDeer dialog. 
	 *
	 * @param name the name
	 */
	public void editRuntime(String name){
		selectRuntime(name);
		new PushButton(referencedComposite, EDIT_BUTTON_NAME).click();
	}
}
