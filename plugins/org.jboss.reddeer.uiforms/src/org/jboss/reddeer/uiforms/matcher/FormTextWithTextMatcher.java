package org.jboss.reddeer.uiforms.matcher;

import org.eclipse.swt.widgets.Widget;
import org.eclipse.ui.forms.widgets.FormText;
import org.jboss.reddeer.core.matcher.WithTextMatcher;
import org.jboss.reddeer.uiforms.handler.FormTextHandler;

/**
 * Matches text to FormText widgets text
 * 
 * @author rhopp
 *
 */

public class FormTextWithTextMatcher extends WithTextMatcher {

	public FormTextWithTextMatcher(String text) {
		super(text);
	}

	@Override
	protected String extractWidgetText(Widget widget) {
		if (widget instanceof FormText) {
			return FormTextHandler.getInstance().getText((FormText) widget);
		}
		return null;
	}

}
