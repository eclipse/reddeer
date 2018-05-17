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

import static org.junit.Assert.assertTrue;

import org.eclipse.draw2d.IFigure;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.editparts.AbstractGraphicalEditPart;
import org.hamcrest.Matcher;
import org.eclipse.reddeer.gef.matcher.IsEditPartWithParent;
import org.junit.Test;

/**
 * Test for IsEditPartWithParent
 * 
 * @author Andrej Podhradsky (apodhrad@redhat.com)
 *
 */
public class IsEditPartWithParentTest {

	@Test
	public void testMacthingEditPartWithParent() {
		EditPart child = emptyEditPart();
		EditPart parent = emptyEditPart();
		child.setParent(parent);

		Matcher<EditPart> matcher = new IsEditPartWithParent(parent);
		assertTrue(matcher.matches(child));
	}

	@Test
	public void testMacthingEditPartWithHigherParent() {
		EditPart child1 = emptyEditPart();
		EditPart child2 = emptyEditPart();
		EditPart parent = emptyEditPart();
		child2.setParent(parent);
		child1.setParent(child2);

		Matcher<EditPart> matcher = new IsEditPartWithParent(parent);
		assertTrue(matcher.matches(child1));
	}

	private static EditPart emptyEditPart() {
		return new AbstractGraphicalEditPart() {
			@Override
			protected IFigure createFigure() {
				return null;
			}

			@Override
			protected void createEditPolicies() {
			}
		};
	}
}
