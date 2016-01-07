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
package org.jboss.reddeer.gef.impl.connection;

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
