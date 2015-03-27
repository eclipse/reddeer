package org.jboss.reddeer.uiforms.handler;

import org.eclipse.ui.forms.widgets.ExpandableComposite;
import org.jboss.reddeer.core.util.Display;
import org.jboss.reddeer.core.util.ResultRunnable;

/**
 * Contains methods for handling UI operations on {@link ExpandableComposite} widgets.
 *
 * @author Radoslav Rabara
 *
 */
public class ExpandableCompositeHandler {

	private static ExpandableCompositeHandler instance;

	private ExpandableCompositeHandler() {

	}

	/**
	 * Returns instance of this class.
	 *
	 * @return instance of ExpandableCompositeHandler
	 */
	public static ExpandableCompositeHandler getInstance() {
		if (instance == null) {
			instance = new ExpandableCompositeHandler();
		}
		return instance;
	}

	/**
	 * Sets the expansion state to the specified {@link ExpandableComposite}.
	 *
	 * @param composite {@link ExpandableComposite} to handle
	 * @param expanded the new expanded state
	 */
	public void setExpanded(final ExpandableComposite composite, final boolean expanded) {
		Display.syncExec(new Runnable() {
			@Override
			public void run() {
				composite.setExpanded(expanded);
			}
		});
	}

	/**
	 * Returns the expansion state of the specified {@link ExpandableComposite}.
	 *
	 * @param composite {@link ExpandableComposite} to handle
	 *
	 * @return <code>true</code> if expanded, <code>false</code> if collapsed.
	 */
	public boolean isExpanded(final ExpandableComposite composite) {
		boolean expansionState = Display
				.syncExec(new ResultRunnable<Boolean>() {
					@Override
					public Boolean run() {
						return composite.isExpanded();
					}
				});
		return expansionState;
	}
}
