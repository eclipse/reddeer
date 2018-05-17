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
package org.eclipse.reddeer.gef.matcher;

import java.util.List;

import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.Label;
import org.eclipse.draw2d.text.TextFlow;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.GraphicalEditPart;
import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.core.Is;
import org.hamcrest.core.IsInstanceOf;
import org.eclipse.reddeer.gef.finder.FigureFinder;

/**
 * Matches edit part which contains {@link org.eclipse.draw2d.Label} or {@link org.eclipse.draw2d.text.TextFlow} with a
 * given text.
 * 
 * @author Andrej Podhradsky (andrej.podhradsky@gmail.com)
 *
 */
public class IsEditPartWithLabel extends BaseMatcher<EditPart> {

	private Matcher<String> matcher;

	/**
	 * Constructs a matcher with a given label.
	 * 
	 * @param label
	 *            Label
	 */
	public IsEditPartWithLabel(String label) {
		this(Is.<String> is(label));
	}

	/**
	 * Constructs a matcher with a given matcher.
	 * 
	 * @param matcher
	 *            Matcher
	 */
	public IsEditPartWithLabel(Matcher<String> matcher) {
		this.matcher = matcher;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.hamcrest.Matcher#matches(java.lang.Object)
	 */
	@Override
	public boolean matches(Object obj) {
		if (obj instanceof GraphicalEditPart) {
			GraphicalEditPart gep = (GraphicalEditPart) obj;
			if (gep.isSelectable()) {
				List<IFigure> labels = new FigureFinder().find(gep.getFigure(), new IsInstanceOf(Label.class));
				for (IFigure figure : labels) {
					String label = ((Label) figure).getText();
					if (matcher.matches(label)) {
						return true;
					}
				}
				List<IFigure> textFlows = new FigureFinder().find(gep.getFigure(), new IsInstanceOf(TextFlow.class));
				for (IFigure figure : textFlows) {
					String text = ((TextFlow) figure).getText();
					if (matcher.matches(text)) {
						return true;
					}
				}
			}
		}
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.hamcrest.SelfDescribing#describeTo(org.hamcrest.Description)
	 */
	@Override
	public void describeTo(Description description) {
		description.appendText("is EditPart with label '" + matcher.toString() + "'");
	}
}
