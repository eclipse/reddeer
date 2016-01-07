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
 * NoButton is simple button implementation for "No" button
 * @author Jiri Peterka
 *
 */
public class NoButton extends PredefinedButton {

	
	/**
	 * NoButton default constructor.
	 */
	public NoButton() {		
		super(null, 0, "No", SWT.PUSH);
		
	}

}
