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
 * Cancel button implementation
 * @author Jiri Peterka
 *
 */
public class CancelButton extends PredefinedButton {
	
	/**
	 * CancelButton default constructor.
	 */
	public CancelButton() {		
		super(null, 0, "Cancel", SWT.PUSH);
		
	}

}
