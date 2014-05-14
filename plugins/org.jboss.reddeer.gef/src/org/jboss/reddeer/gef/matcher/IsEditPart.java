package org.jboss.reddeer.gef.matcher;

import org.eclipse.gef.EditPart;
import org.eclipse.gef.GraphicalEditPart;
import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;

/**
 * Returns true if an object is an instance of selectable {@link org.eclipse.gef.EditPart}.
 * 
 * @author Andrej Podhradsky (andrej.podhradsky@gmail.com)
 *
 */
public class IsEditPart extends BaseMatcher<EditPart> {

	@Override
	public boolean matches(Object obj) {
		return obj instanceof GraphicalEditPart && ((GraphicalEditPart) obj).isSelectable();
	}

	@Override
	public void describeTo(Description desc) {
		desc.appendText("is EditPart");
	}

}
