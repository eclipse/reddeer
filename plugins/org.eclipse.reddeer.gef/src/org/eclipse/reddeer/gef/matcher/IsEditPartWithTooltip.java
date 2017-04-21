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
package org.eclipse.reddeer.gef.matcher;

import org.eclipse.draw2d.IFigure;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.GraphicalEditPart;
import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.core.Is;
import org.eclipse.reddeer.gef.handler.FigureHandler;

/**
 * Matcher which returns true if an edit part has a given tooltip.
 * 
 * @author Andrej Podhradsky (andrej.podhradsky@gmail.com)
 * 
 */
public class IsEditPartWithTooltip extends BaseMatcher<EditPart> {

	private Matcher<String> matcher;

	public IsEditPartWithTooltip(String text) {
		this(Is.<String> is(text));
	}

	public IsEditPartWithTooltip(Matcher<String> matcher) {
		this.matcher = matcher;
	}

	@Override
	public boolean matches(Object obj) {
		if (obj instanceof GraphicalEditPart) {
			IFigure figure = ((GraphicalEditPart) obj).getFigure();
			IFigure tooltip = figure.getToolTip();
			return matcher.matches(FigureHandler.getInstance().getText(tooltip));
		}
		return false;
	}

	@Override
	public void describeTo(Description description) {
		description.appendText("with tooltip '" + matcher.toString() + "'");
	}

}