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
package org.eclipse.reddeer.eclipse.rse.ui.view;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.reddeer.common.logging.Logger;
import org.eclipse.reddeer.core.exception.CoreLayerException;
import org.eclipse.reddeer.eclipse.exception.EclipseLayerException;
import org.eclipse.reddeer.eclipse.rse.ui.wizards.newconnection.RSEMainNewConnectionWizard;
import org.eclipse.reddeer.swt.api.Tree;
import org.eclipse.reddeer.swt.api.TreeItem;
import org.eclipse.reddeer.swt.impl.menu.ContextMenuItem;
import org.eclipse.reddeer.swt.impl.shell.DefaultShell;
import org.eclipse.reddeer.swt.impl.tree.DefaultTree;
import org.eclipse.reddeer.workbench.impl.view.WorkbenchView;

/**
 * This class represents the Remote Systems view.
 * @author Pavol Srna
 *
 */
public class SystemViewPart extends WorkbenchView {

	public static final String TITLE = "Remote Systems";
	
	private static final Logger log = Logger.getLogger(SystemViewPart.class);
	
	/**
	 * Creates a new System view instance.
	 */
	public SystemViewPart() {
		super(TITLE);
	}
	
	/**
	 * Open and return New Connection wizard dialog.
	 *
	 * @return NewConnectionWizardDialog
	 */
	public RSEMainNewConnectionWizard newConnection(){
		log.info("Creating new connection");
		activate();
		getSystem("Local").select();
		new ContextMenuItem("New","Connection...").select();
		new DefaultShell("New Connection");
		
		return new RSEMainNewConnectionWizard();
	}

	/**
	 * Get list of all Remote Systems in tree.
	 *
	 * @return list of systems
	 */
	public List<System> getSystems(){
		List<System> systems = new ArrayList<System>();

		Tree tree;
		try {
			tree = getSystemTree();
		} catch (CoreLayerException e){
			return new ArrayList<System>();
		}
		for (TreeItem item : tree.getItems()){
			systems.add(createSystem(item));
		}
		return systems;
	}

	/**
	 * Get Remote System specified by it's name.
	 *
	 * @param name of the system to be returned
	 * @return System
	 * @throws EclipseLayerException if no remote system found
	 */
	public System getSystem(String name){
		for (System system : getSystems()){
			if (system.getLabel().equals(name)){
				return system;
			}
		}
		throw new EclipseLayerException("There is no remote system with name " + name);
	}

	/**
	 * Gets the system tree.
	 *
	 * @return the system tree
	 */
	protected Tree getSystemTree(){
		activate();
		return new DefaultTree(cTabItem);
	}

	/**
	 * Creates the system.
	 *
	 * @param item the item
	 * @return the system
	 */
	protected System createSystem(TreeItem item){
		return new System(item);
	}
}
