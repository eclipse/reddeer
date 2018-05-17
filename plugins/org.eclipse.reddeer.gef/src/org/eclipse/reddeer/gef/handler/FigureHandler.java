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
package org.eclipse.reddeer.gef.handler;

import org.eclipse.draw2d.IFigure;
import org.eclipse.reddeer.common.logging.Logger;
import org.eclipse.reddeer.common.util.ObjectUtil;

/**
 * Handler for {@link org.eclipse.draw2d.IFigure}.
 * 
 * @author Andrej Podhradsky (andrej.podhradsky@gmail.com)
 *
 */
public class FigureHandler {

	protected final Logger log = Logger.getLogger(this.getClass());
	private static FigureHandler instance;
	
	/**
	 * Gets instance of FigureHandler.
	 * 
	 * @return instance of FigureHandler
	 */
	public static FigureHandler getInstance(){
		if(instance == null){
			instance = new FigureHandler();
		}
		return instance;
	}

	/**
	 * Gets text of the specified figure.
	 *
	 * @param <T>
	 *            the generic type
	 * @param figure
	 *            figure to handle
	 * @return text of the specified figure or null if method 'getText()' is missing
	 */
	public <T extends IFigure> String getText(final T figure) {
		if (figure == null) {
			return null;
		}

		Object o = ObjectUtil.invokeMethod(figure, "getText");
		if (o == null) {
			return null;
		}
		if (o instanceof String) {
			return (String) o;
		}

		return null;
	}
}
