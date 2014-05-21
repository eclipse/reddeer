package org.jboss.reddeer.gef.matcher;

import org.eclipse.gef.EditPart;
import org.eclipse.gef.GraphicalEditPart;
import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;

/**
 * Returns true if an object is an instance of a given instance.
 * 
 * @author Andrej Podhradsky (andrej.podhradsky@gmail.com)
 *
 */
public class IsEditPartOfInstance extends BaseMatcher<EditPart> {

	private String instance;

	/**
	 * Constructs the matcher with given instance name. You can specify only a simple name or the whole canonical name.
	 * 
	 * @param instance
	 *            Instance name
	 */
	public IsEditPartOfInstance(String instance) {
		this.instance = instance;
	}

	@Override
	public boolean matches(Object obj) {
		if (obj instanceof GraphicalEditPart && ((GraphicalEditPart) obj).isSelectable()) {
			String name = obj.getClass().getSimpleName();
			String canonicalName = obj.getClass().getCanonicalName();
			return name.equals(instance) || canonicalName.equals(instance);
		}
		return false;
	}

	@Override
	public void describeTo(Description desc) {
		desc.appendText("is EditPart of instance '" + instance + "'");
	}

}
