package org.jboss.reddeer.gef.matcher;

import org.eclipse.gef.EditPart;
import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;

/**
 * Returns true if an edit part is a child of a given parent.
 * 
 * @author Andrej Podhradsky (apodhrad@redhat.com)
 *
 */
public class IsEditPartWithParent extends BaseMatcher<EditPart> {

	private EditPart parent;

	/**
	 * Constructs the matcher with a given parent.
	 * 
	 * @param parent
	 *            Parent
	 */
	public IsEditPartWithParent(org.jboss.reddeer.gef.api.EditPart parent) {
		this(parent.getGEFEditPart());
	}

	/**
	 * Constructs the matcher with a given parent.
	 * 
	 * @param parent
	 *            Parent
	 */
	public IsEditPartWithParent(EditPart parent) {
		this.parent = parent;
	}

	@Override
	public boolean matches(Object item) {
		if (item instanceof EditPart) {
			EditPart editPart = (EditPart) item;
			EditPart parent = editPart.getParent();
			while (parent != null) {
				if (parent.equals(this.parent)) {
					return true;
				}
				parent = parent.getParent();
			}
		}
		return false;
	}

	@Override
	public void describeTo(Description description) {
		description.appendText("activity with parent '" + parent + "'");
	}

}
