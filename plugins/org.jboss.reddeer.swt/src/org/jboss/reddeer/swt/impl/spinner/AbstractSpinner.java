package org.jboss.reddeer.swt.impl.spinner;

import org.jboss.reddeer.swt.api.Spinner;
import org.jboss.reddeer.swt.lookup.WidgetLookup;
import org.jboss.reddeer.swt.util.Display;
import org.jboss.reddeer.swt.util.ResultRunnable;

/**
 * Abstract class for all Spinner implementations
 * 
 * @author Andrej Podhradsky
 * 
 */
public abstract class AbstractSpinner implements Spinner {

	protected org.eclipse.swt.widgets.Spinner swtSpinner;

	@Override
	public int getValue() {
		return Display.syncExec(new ResultRunnable<Integer>() {

			@Override
			public Integer run() {
				return swtSpinner.getSelection();
			}
		});
	}

	@Override
	public void setValue(final int value) {
		Display.syncExec(new Runnable() {

			@Override
			public void run() {
				swtSpinner.setSelection(value);
			}
		});
	}
	
	public org.eclipse.swt.widgets.Spinner getSWTWidget(){
		return swtSpinner;
	}
	
	@Override
	public boolean isEnabled() {
		return WidgetLookup.getInstance().isEnabled(swtSpinner);
	}

}
