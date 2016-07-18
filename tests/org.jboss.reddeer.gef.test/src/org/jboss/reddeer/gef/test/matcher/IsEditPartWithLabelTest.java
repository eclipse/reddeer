package org.jboss.reddeer.gef.test.matcher;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.Label;
import org.eclipse.draw2d.text.TextFlow;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.editparts.AbstractGraphicalEditPart;
import org.hamcrest.Matcher;
import org.jboss.reddeer.common.matcher.RegexMatcher;
import org.jboss.reddeer.gef.matcher.IsEditPartWithLabel;
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
