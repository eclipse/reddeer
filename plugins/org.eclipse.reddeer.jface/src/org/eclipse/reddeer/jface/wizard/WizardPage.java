/*******************************************************************************
 * Copyright (c) 2017 Red Hat, Inc and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Red Hat Inc. - initial API and implementation
 *******************************************************************************/
package org.eclipse.reddeer.jface.wizard;

import org.eclipse.reddeer.common.logging.Logger;
import org.eclipse.reddeer.core.reference.ReferencedComposite;

/**
 * Superclass of wizard page represent single page in wizard dialog.
 * 
 * @author apodhrad
 * @since 0.6
 * 
 */
public abstract class WizardPage {

	protected final Logger log = Logger.getLogger(this.getClass());
	protected ReferencedComposite referencedComposite;

	/**
	 * Instantiates a new wizard page.
	 */
	public WizardPage(ReferencedComposite referencedComposite) {
		this.referencedComposite = referencedComposite;
	}
}
