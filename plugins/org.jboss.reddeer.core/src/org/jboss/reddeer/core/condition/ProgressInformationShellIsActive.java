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
package org.jboss.reddeer.core.condition;

import org.jboss.reddeer.core.condition.ShellWithTextIsActive;

/**
 * Condition is met when progress information shell is active.
 * 
 * @author Lucia Jelinkova
 *
 */
public class ProgressInformationShellIsActive extends ShellWithTextIsActive {

	/**
	 * Constructs ProgressInformationShellIsActive wait condition. Condition is met
	 * when shell with name Progress Information is active.
	 */
	public ProgressInformationShellIsActive() {
		super("Progress Information");
	}
}