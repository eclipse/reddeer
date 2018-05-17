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
package org.eclipse.reddeer.graphiti.impl.contextbutton.internal;

import org.eclipse.graphiti.tb.IContextButtonEntry;
import org.eclipse.reddeer.graphiti.impl.contextbutton.AbstractContextButton;

/**
 * Internal implementation of ContextButton.
 * 
 * @author Andrej Podhradsky (andrej.podhradsky@gmail.com)
 *
 */
public class BasicContextButton extends AbstractContextButton {

	/**
	 * Instantiates a new basic context button.
	 *
	 * @param contextButtonEntry the context button entry
	 */
	public BasicContextButton(IContextButtonEntry contextButtonEntry) {
		super(contextButtonEntry);
	}

}
