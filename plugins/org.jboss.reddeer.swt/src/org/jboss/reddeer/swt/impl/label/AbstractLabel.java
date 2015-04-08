package org.jboss.reddeer.swt.impl.label;

import org.hamcrest.Matcher;
import org.jboss.reddeer.swt.api.Label;
import org.jboss.reddeer.core.handler.WidgetHandler;
import org.jboss.reddeer.core.reference.ReferencedComposite;
import org.jboss.reddeer.swt.widgets.AbstractWidget;

public abstract class AbstractLabel extends AbstractWidget<org.eclipse.swt.widgets.Label> implements Label {

	protected AbstractLabel(ReferencedComposite refComposite, int index, Matcher<?>... matchers){
		super(org.eclipse.swt.widgets.Label.class, refComposite, index, matchers);
	}
	
	@Override
	public String getText() {
		String text = WidgetHandler.getInstance().getText(swtWidget);
		return text;
	}
	
	@Override
	public boolean isVisible() {
		boolean ret = WidgetHandler.getInstance().isVisible(swtWidget);
		return ret;
	}
}
