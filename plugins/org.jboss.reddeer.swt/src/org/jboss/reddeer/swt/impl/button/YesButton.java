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
package org.jboss.reddeer.swt.impl.button;

import org.eclipse.swt.SWT;

/**
 * BackButton is simple button implementation for "Yes" button
 * @author Jiri Peterka
 *
 */
public class YesButton extends PredefinedButton {

	
	/**
	 * YesButton default constructor.
	 */
	public YesButton() {		
		super(null, 0, "Yes", SWT.PUSH);
		
	}

}
