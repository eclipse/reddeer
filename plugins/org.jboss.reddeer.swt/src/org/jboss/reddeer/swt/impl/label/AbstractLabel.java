package org.jboss.reddeer.swt.impl.label;

import org.jboss.reddeer.swt.api.Label;
import org.jboss.reddeer.swt.handler.WidgetHandler;
import org.jboss.reddeer.swt.lookup.WidgetLookup;

public abstract class AbstractLabel implements Label {

	protected org.eclipse.swt.widgets.Label w;		
	
	@Override
	/*
	 * (non-Javadoc)
	 * @see org.jboss.reddeer.swt.api.Label#getText()
	 */
	public String getText() {
		String text = WidgetHandler.getInstance().getText(w);
		return text;
	}
	
	@Override
	/*
	 * (non-Javadoc)
	 * @see org.jboss.reddeer.swt.api.Label#isVisible()
	 */
	public boolean isVisible() {
		boolean ret = WidgetLookup.getInstance().isVisible(w);
		return ret;
	}
	
	public org.eclipse.swt.widgets.Label getSWTWidget(){
		return w;
	}
	
	@Override
	public boolean isEnabled() {
		return WidgetLookup.getInstance().isEnabled(w);
	}
}
