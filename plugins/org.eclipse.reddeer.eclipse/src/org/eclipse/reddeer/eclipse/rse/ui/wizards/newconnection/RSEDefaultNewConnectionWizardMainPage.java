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
package org.eclipse.reddeer.eclipse.rse.ui.wizards.newconnection;

import java.util.LinkedList;
import java.util.List;

import org.eclipse.reddeer.core.reference.ReferencedComposite;
import org.eclipse.reddeer.jface.wizard.WizardPage;
import org.eclipse.reddeer.swt.api.Combo;
import org.eclipse.reddeer.swt.impl.combo.DefaultCombo;
import org.eclipse.reddeer.swt.impl.text.DefaultText;


/**
 * This class represents the main wizard page of the New Connection wizard dialog where
 * the host name and connection name of the remote system are set.
 * @author Pavol Srna
 *
 */
public class RSEDefaultNewConnectionWizardMainPage extends WizardPage {
	
	public RSEDefaultNewConnectionWizardMainPage(ReferencedComposite referencedComposite) {
		super(referencedComposite);
	}

	/**
	 * Sets Host name.
	 *
	 * @param hostname the new host name
	 */
	public void setHostName(String hostname){
		getHostNameCombo().setText(hostname);
	}

	/**
	 * Sets Connection name.
	 *
	 * @param name the new connection name
	 */
	public void setConnectionName(String name){
		new DefaultText(referencedComposite, 0).setText(name);
	}
	
	/**
	 * Gets list of all defined host names.
	 *
	 * @return list of host names
	 */
	public List<String> getHostNames() {
		List<String> items = new LinkedList<String>(getHostNameCombo().getItems());
		return items;
	}
	
	private Combo getHostNameCombo(){
		return new DefaultCombo(referencedComposite, 1);
	}	
}