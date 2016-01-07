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
package org.jboss.reddeer.eclipse.jst.j2ee.ui.project.facet;

import org.jboss.reddeer.jface.wizard.NewWizardDialog;

/**
 * Wizard dialog for creating EAR project.
 */
public class EarProjectWizard extends NewWizardDialog{
	
	public static final String CATEGORY="Java EE";
	public static final String NAME="Enterprise Application Project";
	
	/**
	 * Constructs the wizard with {@value #CATEGORY}.
	 */
	public EarProjectWizard(){
		super(CATEGORY,NAME);
	}

}
