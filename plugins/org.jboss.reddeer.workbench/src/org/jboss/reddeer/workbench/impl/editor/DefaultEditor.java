package org.jboss.reddeer.workbench.impl.editor;

import org.hamcrest.Matcher;
import org.jboss.reddeer.workbench.api.Editor;

/**
 * Represents general editor with basic operations implemented.
 * 
 * @author rhopp
 * @author rawagner
 */
public class DefaultEditor extends AbstractEditor implements Editor {

	/**
	 * Initialize editor
	 */
	public DefaultEditor() {
		super();
	}

	/**
	 * Initialize editor with given title and activate it.
	 * 
	 * @param title
	 *            Title of editor to initialize and activate
	 */
	public DefaultEditor(final String title) {
		super(title);
	}

	/**
	 * Initialize editor with given title matcher.
	 * 
	 * @param titleMatcher
	 *            Title matcher of editor to initialize and activate
	 */
	public DefaultEditor(final Matcher<String> titleMatcher) {
		super(titleMatcher);
	}

}
