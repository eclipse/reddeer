package org.jboss.reddeer.uiforms.impl.form;

import org.eclipse.swt.widgets.Control;
import org.jboss.reddeer.swt.handler.WidgetHandler;
import org.jboss.reddeer.uiforms.api.Form;

/**
 * Common ancestor for all {@link Form} implementations. 
 * 
 * @author Lucia Jelinkova
 *
 */
public abstract class AbstractForm implements Form {

	protected org.eclipse.ui.forms.widgets.Form form;
	
	@Override
	public Control getControl() {
		return form.getBody();
	}

	@Override
	public org.eclipse.swt.widgets.Widget getSWTWidget() {
		return form;
	}

	@Override
	public boolean isEnabled() {
		return WidgetHandler.getInstance().isEnabled(form);
	}
	
	protected void setFocus() {
		WidgetHandler.getInstance().setFocus(form);
	}
	
	public String getText(){
		return WidgetHandler.getInstance().getText(form);
	}
}
