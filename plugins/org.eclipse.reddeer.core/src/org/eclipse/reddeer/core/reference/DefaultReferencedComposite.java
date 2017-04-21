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
