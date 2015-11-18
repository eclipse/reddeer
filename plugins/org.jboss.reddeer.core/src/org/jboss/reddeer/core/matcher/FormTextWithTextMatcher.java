package org.jboss.reddeer.core.matcher;

import org.eclipse.swt.widgets.Widget;
import org.eclipse.ui.forms.widgets.FormText;
import org.jboss.reddeer.core.matcher.WithTextMatcher;
import org.jboss.reddeer.core.handler.FormTextHandler;

/**
 * Matcher matching text of {@link org.eclipse.ui.forms.widgets.FormText}. 
 * 
 * @author rhopp
 *
 */
public class FormTextWithTextMatcher extends WithTextMatcher {

	/**
	 * Creates new FormTextWithTextMatcher matching text of FormText widget to specified text.
	 * 
	 * @param text text to match text of form text widget
	 */
	public FormTextWithTextMatcher(String text) {
		super(text);
	}

	/* (non-Javadoc)
	 * @see org.jboss.reddeer.core.matcher.AbstractWidgetWithTextMatcher#extractWidgetText(org.eclipse.swt.widgets.Widget)
	 */
	@Override
	protected String extractWidgetText(Widget widget) {
		if (widget instanceof FormText) {
			return FormTextHandler.getInstance().getText((FormText) widget);
		}
		return null;
	}

}
