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
package org.jboss.reddeer.eclipse.ui.wizards;

/**
 * Interface to define basic wizard state methods: to set error message and
 * wizard completion.
 * 
 * @author sbunciak
 * @since 0.2
 */
public interface WizardPageSettings {

	/**
	 * Sets new error message.
	 * @param newMessage new Error message to be set 
	 */
	void setErrorMessage(String newMessage);

	/**
	 * Sets page completions.
	 * @param complete set page completion
	 */
	void setPageComplete(boolean complete);

}