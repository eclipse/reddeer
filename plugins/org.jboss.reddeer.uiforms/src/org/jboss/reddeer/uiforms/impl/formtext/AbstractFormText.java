package org.jboss.reddeer.uiforms.impl.formtext;

import org.eclipse.swt.widgets.Widget;
import org.jboss.reddeer.swt.handler.WidgetHandler;
import org.jboss.reddeer.uiforms.api.FormText;
import org.jboss.reddeer.uiforms.handler.FormTextHandler;

public abstract class AbstractFormText implements FormText {

	protected org.eclipse.ui.forms.widgets.FormText widget;

	public Widget getSWTWidget() {
		return widget;
	}

	public String getSelectionText() {
		return null;
	}

	public void click() {
		FormTextHandler.getInstance().click(widget);

	}

	public String getText() {
		return FormTextHandler.getInstance().getText(widget);
	}

	public void setFocus() {
		WidgetHandler.getInstance().setFocus(widget);
	}

	public boolean hasFocus() {
		return FormTextHandler.getInstance().hasFocus(widget);
	}

	public String getTooltipText() {
		return FormTextHandler.getInstance().getTooltipText(widget);
	}
	
	public boolean isEnabled() {
		return false;
	}
}
