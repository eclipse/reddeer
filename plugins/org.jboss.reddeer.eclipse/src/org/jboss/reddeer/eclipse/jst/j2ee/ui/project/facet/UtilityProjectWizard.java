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

import org.jboss.reddeer.eclipse.topmenu.NewMenuWizard;

/**
 * Represents utility project wizard
 * @author rawagner
 *
 */
public class UtilityProjectWizard extends NewMenuWizard{
	
	public static final String CATEGORY="Java EE";
	public static final String NAME="Utility Project";
	
	/**
	 * Constructs the wizard with {@value #NAME}.
	 */
	public UtilityProjectWizard(){
		super("New Java Utility Module",CATEGORY,NAME);
	}

}
