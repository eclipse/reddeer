package org.jboss.reddeer.uiforms.impl.section;

import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Widget;
import org.jboss.reddeer.swt.handler.WidgetHandler;
import org.jboss.reddeer.uiforms.api.Section;
import org.jboss.reddeer.uiforms.handler.SectionHandler;

/**
 * Common ancestor for all {@link Section} implementations. 
 * 
 * @author Lucia Jelinkova
 *
 */
public abstract class AbstractSection implements Section {

	protected org.eclipse.ui.forms.widgets.Section section;
	
	public Control getControl() {
		return section.getClient();
	}

	public Widget getSWTWidget() {
		return section;
	}

	public boolean isEnabled() {
		return WidgetHandler.getInstance().isEnabled(section);
	}
	
	public String getText() {
		return WidgetHandler.getInstance().getText(section);
	}
	
	protected void setFocus() {
		WidgetHandler.getInstance().setFocus(section);
	}
	
	public void setExpanded(final boolean expanded) {
		SectionHandler.getInstance().setExpanded(section, expanded);
	}
}
