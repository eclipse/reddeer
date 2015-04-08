package org.jboss.reddeer.swt.impl.toolbar;

import org.jboss.reddeer.swt.impl.shell.DefaultShell;
import org.jboss.reddeer.core.lookup.ToolBarLookup;
import org.jboss.reddeer.core.reference.ReferencedComposite;

/**
 * Default ToolBar implementation.
 * 
 * @author Jiri Peterka
 * @author rhopp
 *
 */
public class DefaultToolBar extends AbstractToolBar {

	public DefaultToolBar() {
		this(0);
	}

	/**
	 * Constructor for nth toolbar in currently active shell.
	 * 
	 * @param index
	 *            index of desired shell.
	 */

	public DefaultToolBar(int index) {
		this(new DefaultShell(), index);
	}

	/**
	 * Constructor for nth toolbar within given ReferencedComposite.
	 * 
	 * @param rc
	 *            {@link ReferencedComposite} in which the lookup will be
	 *            performed.
	 *            <p>
	 *            If null, search will be performed in currently active part.
	 * @param index
	 *            index of desired toolbar within given
	 *            {@link ReferencedComposite}.
	 */

	public DefaultToolBar(ReferencedComposite rc, int index) {
		toolBar = ToolBarLookup.getInstance().getToolBar(rc, index);
	}

}
