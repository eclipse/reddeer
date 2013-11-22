package org.jboss.reddeer.swt.impl.toolbar;

import org.hamcrest.Matcher;

/**
 * ViewToolItem implementation. It expect view where toolbar should be found has
 * @deprecated - use DefaultToolItem implementation for view tool item and also for editor tool item
 * @author Jiri Peterka
 *
 */
public class ViewToolItem extends DefaultToolItem {

	public ViewToolItem(Matcher<String> matcher) {
		super(matcher);
	}
}
