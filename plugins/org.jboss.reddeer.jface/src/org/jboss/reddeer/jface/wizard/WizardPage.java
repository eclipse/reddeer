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
package org.jboss.reddeer.jface.wizard;

import org.jboss.reddeer.common.logging.Logger;

/**
 * Superclass of wizard page represent single page in wizard dialog.
 * 
 * @author apodhrad
 * @since 0.6
 * 
 */
public abstract class WizardPage {

	protected final Logger log = Logger.getLogger(this.getClass());

	/**
	 * Instantiates a new wizard page.
	 */
	protected WizardPage() {

	}
}
