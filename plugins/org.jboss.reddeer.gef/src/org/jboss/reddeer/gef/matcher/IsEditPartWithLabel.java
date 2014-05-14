package org.jboss.reddeer.gef.matcher;

import java.util.List;

import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.Label;
import org.eclipse.draw2d.text.TextFlow;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.GraphicalEditPart;
import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.hamcrest.core.IsInstanceOf;
import org.jboss.reddeer.gef.finder.FigureFinder;

/**
 * Matches edit part which contains {@link org.eclipse.draw2d.Label} or {@link org.eclipse.draw2d.text.TextFlow} with a
 * given text.
 * 
 * @author Andrej Podhradsky (andrej.podhradsky@gmail.com)
 *
 */
public class IsEditPartWithLabel extends BaseMatcher<EditPart> {

	private String label;

	/**
	 * Constructs a matcher with a given label.
	 * 
	 * @param label Label
	 */
	public IsEditPartWithLabel(String label) {
		this.label = label;
	}

	@Override
	public boolean matches(Object obj) {
		if (obj instanceof GraphicalEditPart) {
			GraphicalEditPart gep = (GraphicalEditPart) obj;
			if (gep.isSelectable()) {
				List<IFigure> labels = new FigureFinder().find(gep.getFigure(), new IsInstanceOf(Label.class));
				for (IFigure figure : labels) {
					String label = ((Label) figure).getText();
					if (this.label.trim().equals(label.trim())) {
						return true;
					}
				}
				List<IFigure> textFlows = new FigureFinder().find(gep.getFigure(), new IsInstanceOf(TextFlow.class));
				for (IFigure figure : textFlows) {
					String text = ((TextFlow) figure).getText();
					if (this.label.trim().equals(text.trim())) {
						return true;
					}
				}
			}
		}
		return false;
	}

	@Override
	public void describeTo(Description description) {
		description.appendText("is EditPart with label '" + label + "'");
	}
}
