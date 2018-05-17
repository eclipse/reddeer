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
package org.eclipse.reddeer.jface.wizard;

import org.eclipse.reddeer.common.logging.Logger;
import org.eclipse.reddeer.core.reference.ReferencedComposite;
import org.eclipse.swt.widgets.Control;

/**
 * Superclass of wizard page represent single page in wizard dialog.
 * 
 * @author apodhrad
 * @since 0.6
 * 
 */
public abstract class WizardPage implements ReferencedComposite{

	protected final Logger log = Logger.getLogger(this.getClass());
	protected ReferencedComposite referencedComposite;

	/**
	 * Instantiates a new wizard page.
	 */
	public WizardPage(ReferencedComposite referencedComposite) {
		if(referencedComposite == null) {
			throw new IllegalArgumentException("referencedComposite can't be null");
		}
		if(referencedComposite.getControl() != null && referencedComposite.getControl().isDisposed()) {
			throw new IllegalArgumentException("referencedComposite is disposed");
		}
		this.referencedComposite = referencedComposite;
	}

	
	@Override
	public Control getControl() {
		return referencedComposite.getControl();
	}
}
