package org.jboss.reddeer.swt.matcher;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;
import org.jboss.reddeer.swt.api.TableItem;

/**
 * Checks that the table item is checked.
 *  
 * @author Lucia Jelinkova
 *
 */
public class CheckedTableItemMatcher extends TypeSafeMatcher<TableItem> {

	@Override
	protected boolean matchesSafely(TableItem item) {
		return item.isChecked();
	}

	@Override
	public void describeTo(Description description) {
		description.appendText("table item is not checked");
	}
}
