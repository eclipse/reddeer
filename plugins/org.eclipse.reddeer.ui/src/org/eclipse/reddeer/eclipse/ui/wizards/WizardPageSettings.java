/*******************************************************************************
 * Copyright (c) 2017 Red Hat, Inc and others.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     Red Hat, Inc - initial API and implementation
 *******************************************************************************/
package org.eclipse.reddeer.eclipse.ui.wizards;

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