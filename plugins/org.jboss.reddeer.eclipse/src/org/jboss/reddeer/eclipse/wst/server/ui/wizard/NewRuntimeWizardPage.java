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
package org.jboss.reddeer.eclipse.wst.server.ui.wizard;

import org.jboss.reddeer.jface.wizard.WizardPage;
import org.jboss.reddeer.swt.api.TreeItem;
import org.jboss.reddeer.swt.impl.tree.DefaultTreeItem;

/**
 * Represents the first page displayed when invoked {@link NewRuntimeWizardDialog}
 * 
 * @author Lucia Jelinkova
 *
 */
public class NewRuntimeWizardPage extends WizardPage {
  
	/**
	 * Instantiates a new new runtime wizard page.
	 */
	public NewRuntimeWizardPage(){
		super();
	}
	
	/**
	 * Select type.
	 *
	 * @param type the type
	 */
	public void selectType(String... type){
		TreeItem t = new DefaultTreeItem(type);
		t.select();
	}
}
