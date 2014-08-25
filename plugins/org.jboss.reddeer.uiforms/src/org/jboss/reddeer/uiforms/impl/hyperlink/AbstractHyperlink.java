package org.jboss.reddeer.uiforms.impl.hyperlink;

import org.hamcrest.Matcher;
import org.jboss.reddeer.swt.handler.WidgetHandler;
import org.jboss.reddeer.swt.reference.ReferencedComposite;
import org.jboss.reddeer.swt.widgets.AbstractWidget;
import org.jboss.reddeer.uiforms.api.Hyperlink;
import org.jboss.reddeer.uiforms.handler.HyperLinkHandler;

/**
 * Common ancestor for all {@link Hyperlink} implementations
 * 
 * @author Lucia Jelinkova
 *
 */
public abstract class AbstractHyperlink extends AbstractWidget<org.eclipse.ui.forms.widgets.Hyperlink> implements Hyperlink {

	protected AbstractHyperlink(ReferencedComposite refComposite, int index, Matcher<?>... matchers){
		super(org.eclipse.ui.forms.widgets.Hyperlink.class, refComposite, index, matchers);
		setFocus();
	}
	
	public String getText() {
		return WidgetHandler.getInstance().getText(swtWidget);
	}
	
	public void activate() {
		HyperLinkHandler.getInstance().activate(swtWidget);
	}
	
	protected void setFocus() {
		WidgetHandler.getInstance().setFocus(swtWidget);
	}
}
