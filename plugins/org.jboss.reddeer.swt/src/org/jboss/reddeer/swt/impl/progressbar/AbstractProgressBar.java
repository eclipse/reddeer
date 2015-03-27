package org.jboss.reddeer.swt.impl.progressbar;

import org.hamcrest.Matcher;
import org.jboss.reddeer.swt.api.ProgressBar;
import org.jboss.reddeer.core.reference.ReferencedComposite;
import org.jboss.reddeer.swt.widgets.AbstractWidget;

/**
 * Abstract class for ProgressBar
 * 
 * @author rhopp
 *
 */

public abstract class AbstractProgressBar extends AbstractWidget<org.eclipse.swt.widgets.ProgressBar> implements ProgressBar {
	
	protected AbstractProgressBar(ReferencedComposite refComposite, int index, Matcher<?>... matchers){
		super(org.eclipse.swt.widgets.ProgressBar.class, null, index, matchers);
	}
	
	/**
	 * Returns state of this progressbar. One of SWT.NORMAL, SWT.ERROR, SWT.PAUSED. 
	 * Note: This operation is a hint and is not supported on platforms that do not have this concept.
	 */
	
	@Override
	public int getState() {
		return ProgressBarHandler.getInstance().getState(swtWidget);
	}
}
