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
package org.jboss.reddeer.eclipse.wst.html.ui.wizard;

import org.jboss.reddeer.eclipse.topmenu.NewMenuWizard;

/**
 * New HTML Wizard.
 * @author rawagner
 *
 */
public class NewHTMLWizard extends NewMenuWizard{
	
	/**
	 * Default constructor.
	 */
	public NewHTMLWizard(){
		super("New HTML File","Web","HTML File");
	}

}
