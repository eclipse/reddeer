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
