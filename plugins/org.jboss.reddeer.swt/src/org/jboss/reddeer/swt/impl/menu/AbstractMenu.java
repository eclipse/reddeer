package org.jboss.reddeer.swt.impl.menu;

import org.hamcrest.Matcher;
import org.jboss.reddeer.common.logging.Logger;
import org.jboss.reddeer.swt.api.Menu;

/**
 * Abstract class for all Menu implementations
 * 
 * @author Jiri Peterka
 * 
 */
public abstract class AbstractMenu implements Menu {

	protected String[] path;
	protected Matcher<String>[] matchers;

	@Override
	public abstract void select();
	
	@Override
	public abstract boolean isSelected();
	
	@Override
	public String getText() {
		throw new UnsupportedOperationException("not yet implemented");
	}
	
}
