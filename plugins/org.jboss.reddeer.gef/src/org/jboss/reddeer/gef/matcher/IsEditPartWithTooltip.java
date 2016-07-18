package org.jboss.reddeer.gef.matcher;

import org.eclipse.draw2d.IFigure;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.GraphicalEditPart;
import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.core.Is;
import org.jboss.reddeer.gef.handler.FigureHandler;

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