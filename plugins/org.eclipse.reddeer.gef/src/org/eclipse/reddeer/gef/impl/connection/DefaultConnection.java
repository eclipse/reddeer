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
package org.eclipse.reddeer.gef.impl.connection;

import org.eclipse.draw2d.Connection;
import org.hamcrest.core.IsInstanceOf;

/**
 * Default Connection implementation.
 * 
 * @author Andrej Podhradsky (andrej.podhradsky@gmail.com)
 *
 */
public class DefaultConnection extends AbstractConnection {

	/**
	 * Constructs a connection at index 0.
	 */
	public DefaultConnection() {
		this(0);
	}

	/**
	 * Constructs a connection at a given index.
	 * 
	 * @param index
	 *            Index
	 */
	public DefaultConnection(int index) {
		super(new IsInstanceOf(Connection.class), index);
	}

}
