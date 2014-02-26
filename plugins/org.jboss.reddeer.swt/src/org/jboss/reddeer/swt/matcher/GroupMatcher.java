package org.jboss.reddeer.swt.matcher;

import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Widget;
import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.jboss.reddeer.swt.lookup.WidgetResolver;
import org.jboss.reddeer.swt.reference.ReferencedComposite;

/**
 * Group matcher for matching widgets in group
 * @author jpeterka
 * @deprecated in 0.4, use {@link ReferencedComposite}
 *
 */
public class GroupMatcher extends BaseMatcher<String> {

	private String text;
	
	public GroupMatcher(String text ) {
		this.text = text;
	}

	/**
	 * Matches if widget's parent is a group with text
	 */
	@Override
	public boolean matches(Object item) {
		
		if (item instanceof Widget) {
			Widget w  = WidgetResolver.getInstance().getParent((Widget)item);
			if (w instanceof Group) {
				if (((Group)w).getText().equals(text)) return true;
			}
		}
		
		return false;
	}
		
	@Override
	public void describeTo(Description description) {
		
	}

}
