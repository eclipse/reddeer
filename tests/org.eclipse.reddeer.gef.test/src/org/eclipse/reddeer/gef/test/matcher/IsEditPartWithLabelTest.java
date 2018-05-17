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
package org.eclipse.reddeer.gef.test.matcher;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.Label;
import org.eclipse.draw2d.text.TextFlow;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.editparts.AbstractGraphicalEditPart;
import org.hamcrest.Matcher;
import org.eclipse.reddeer.common.matcher.RegexMatcher;
import org.eclipse.reddeer.gef.matcher.IsEditPartWithLabel;
import org.junit.Test;

/**
 * Test for IsEditPartWithLabelTest
 * 
 * @author Andrej Podhradsky (andrej.podhradsky@gmail.com)
 *
 */
public class IsEditPartWithLabelTest {

	@Test
	public void testMacthingEditPartWithNoLabel() {
		Matcher<EditPart> matcher = new IsEditPartWithLabel("helloworld");
		assertFalse(matcher.matches(new AbstractGraphicalEditPart() {
			@Override
			protected IFigure createFigure() {
				return new Figure();
			}

			@Override
			protected void createEditPolicies() {
			}
		}));
	}

	@Test
	public void testMacthingEditPartWithSimpleLabel() {
		Matcher<EditPart> matcher = new IsEditPartWithLabel("helloworld");
		assertTrue(matcher.matches(new AbstractGraphicalEditPart() {
			@Override
			protected IFigure createFigure() {
				IFigure fig = new Figure();
				fig.add(new Label("helloworld"));
				return fig;
			}

			@Override
			protected void createEditPolicies() {
			}
		}));
	}

	@Test
	public void testMacthingEditPartWithTextFlowLabel() {
		Matcher<EditPart> matcher = new IsEditPartWithLabel("helloworld");
		assertTrue(matcher.matches(new AbstractGraphicalEditPart() {
			@Override
			protected IFigure createFigure() {
				IFigure fig = new Figure();
				fig.add(new TextFlow("helloworld"));
				return fig;
			}

			@Override
			protected void createEditPolicies() {
			}
		}));
	}

	@Test
	public void testMacthingEditPartWithSimpleLabelByRegex() {
		Matcher<EditPart> matcher = new IsEditPartWithLabel(new RegexMatcher("hello.*d"));
		assertTrue(matcher.matches(new AbstractGraphicalEditPart() {
			@Override
			protected IFigure createFigure() {
				IFigure fig = new Figure();
				fig.add(new Label("helloworld"));
				return fig;
			}

			@Override
			protected void createEditPolicies() {
			}
		}));
	}

}
