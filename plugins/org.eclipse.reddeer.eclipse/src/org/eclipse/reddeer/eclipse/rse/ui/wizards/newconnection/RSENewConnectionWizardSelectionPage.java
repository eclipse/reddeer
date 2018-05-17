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
package org.eclipse.reddeer.eclipse.rse.ui.wizards.newconnection;

import org.eclipse.reddeer.core.reference.ReferencedComposite;
import org.eclipse.reddeer.jface.wizard.WizardDialog;
import org.eclipse.reddeer.jface.wizard.WizardPage;
import org.eclipse.reddeer.swt.impl.tree.DefaultTree;
import org.eclipse.reddeer.swt.impl.tree.DefaultTreeItem;

/**
 * This class represents first wizard page of the New Connection wizard 
 * and it let user choose type of the remote system.
 * @author Pavol Srna
 *
 */
public class RSENewConnectionWizardSelectionPage extends WizardPage{
	
	public RSENewConnectionWizardSelectionPage(ReferencedComposite referencedComposite) {
		super(referencedComposite);
	}
	
	
	/**
	 * Select remote system type in tree.
	 *
	 * @param type the type
	 */
	public RSENewConnectionWizardSelectionPage selectSystemType(SystemType type){
		new DefaultTreeItem(new DefaultTree(this), "General", type.getLabel()).select(); 
		return this;
	}
	
	/**
	 * Enumeration of supported remote system types 
	 */
	public enum SystemType {
		FTP_ONLY("FTP Only"), LINUX("Linux"), LOCAL("Local"), SSH_ONLY("SSH Only"), 
		TELNET_ONLY("Telnet Only (Experimental)"), UNIX("Unix"), WINDOWS("Windows");
		
		private String label;
		
		/**
		 * Instantiates a new system type.
		 *
		 * @param label the label
		 */
		SystemType(String label) {
			this.label = label;
		}
		
		/**
		 * Gets the label.
		 *
		 * @return the label
		 */
		public String getLabel() {
			return label;
		}
	}

}
