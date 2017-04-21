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
package org.eclipse.reddeer.gef.impl.connection;

import org.eclipse.draw2d.geometry.Point;
import org.hamcrest.Matcher;
import org.eclipse.reddeer.gef.api.Connection;
import org.eclipse.reddeer.gef.handler.ViewerHandler;
import org.eclipse.reddeer.gef.lookup.FigureLookup;
import org.eclipse.reddeer.gef.lookup.ViewerLookup;

/**
 * Abstract class for Connection implementation.
 * 
 * @author Andrej Podhradsky (andrej.podhradsky@gmail.com)
 *
 */
public class AbstractConnection implements Connection {

	protected org.eclipse.draw2d.Connection connection;

	/**
	 * Finds a connection according to a given matcher at the specified index.
	 * 
	 * @param matcher
	 *            Matcher
	 * @param index
	 *            Index
	 */
	public AbstractConnection(Matcher<?> matcher, int index) {
		connection = (org.eclipse.draw2d.Connection) FigureLookup.getInstance().findFigure(matcher, index);
	}

	@Override
	public void select() {
		Point sp = connection.getSourceAnchor().getReferencePoint();
		Point tp = connection.getTargetAnchor().getReferencePoint();
		int x = (sp.x + tp.x) / 2;
		int y = (sp.y + tp.y) / 2;
		ViewerHandler.getInstance().click(ViewerLookup.getInstance().findGraphicalViewer(), x, y);
	}

}
