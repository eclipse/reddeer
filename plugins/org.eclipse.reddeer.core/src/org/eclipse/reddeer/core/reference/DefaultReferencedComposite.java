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
package org.eclipse.reddeer.core.reference;

import org.eclipse.swt.widgets.Control;

/**
 * Default referenced composite.
 * 
 * @author mlabuda@redhat.com
 *
 */
public class DefaultReferencedComposite implements ReferencedComposite {

	private Control control;
	
	/**
	 * Creates default ReferencedComposite from control.
	 * 
	 * @param control control
	 */
	public DefaultReferencedComposite(Control control) {
		this.control = control;
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.reddeer.core.reference.ReferencedComposite#getControl()
	 */
	@Override
	public Control getControl() {
		return control;
	}

}
