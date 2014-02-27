package org.jboss.reddeer.uiforms.impl.hyperlink;

import org.eclipse.swt.widgets.Widget;
import org.jboss.reddeer.swt.handler.WidgetHandler;
import org.jboss.reddeer.uiforms.api.Hyperlink;

/**
 * Common ancestor for all {@link Hyperlink} implementations
 * 
 * @author Lucia Jelinkova
 *
 */
public abstract class AbstractHyperlink implements Hyperlink {

	protected org.eclipse.ui.forms.widgets.Hyperlink hyperLink;
	
	public Widget getSWTWidget() {
		return hyperLink;
	}

	public boolean isEnabled() {
		return WidgetHandler.getInstance().isEnabled(hyperLink);
	}

	public String getText() {
		return WidgetHandler.getInstance().getText(hyperLink);
	}
	
	public void activate() {
		WidgetHandler.getInstance().activate(hyperLink);
	}
	
	protected void setFocus() {
		WidgetHandler.getInstance().setFocus(hyperLink);
	}
}
