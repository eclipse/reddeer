package org.jboss.reddeer.eclipse.debug.core;

import org.jboss.reddeer.common.matcher.RegexMatcher;
import org.jboss.reddeer.core.matcher.WithTooltipTextMatcher;
import org.jboss.reddeer.eclipse.exception.EclipseLayerException;
import org.jboss.reddeer.swt.impl.toolbar.DefaultToolItem;
import org.jboss.reddeer.workbench.impl.shell.WorkbenchShell;

/**
 * Represents the debugger 'Resume' button implemented as a tool item in the
 * workbench.
 * 
 * @author Andrej Podhradsky
 *
 */
public class ResumeButton extends DefaultToolItem {

	/**
	 * Constructs the Resume button as a tool item in the workbench.
	 */
	public ResumeButton() {
		super(new WorkbenchShell(), new WithTooltipTextMatcher(new RegexMatcher("Resume.*")));
	}

	/* (non-Javadoc)
	 * @see org.jboss.reddeer.swt.impl.toolbar.AbstractToolItem#click()
	 */
	@Override
	public void click() {
		if (!isEnabled()) {
			throw new EclipseLayerException("Cannot click on 'Resume' button, it is NOT enabled!");
		}
		super.click();
	}

}
