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
 * OkButton is simple button implementation for OK button
 * @author Jiri Peterka
 *
 */
public class OkButton extends PredefinedButton {

	
	/**
	 * OkButton default constructor.
	 */
	public OkButton() {		
		super(null, 0, "OK", SWT.PUSH);
		
	}

}
