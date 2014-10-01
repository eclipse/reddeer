package org.jboss.reddeer.uiforms.impl.section;

import org.eclipse.swt.widgets.Control;
import org.hamcrest.Matcher;
import org.jboss.reddeer.swt.handler.WidgetHandler;
import org.jboss.reddeer.swt.reference.ReferencedComposite;
import org.jboss.reddeer.swt.widgets.AbstractWidget;
import org.jboss.reddeer.uiforms.api.Section;
import org.jboss.reddeer.uiforms.handler.SectionHandler;

/**
 * Common ancestor for all {@link Section} implementations. 
 * 
 * @author Lucia Jelinkova
 *
 */
public abstract class AbstractSection extends AbstractWidget<org.eclipse.ui.forms.widgets.Section> implements Section {

	protected AbstractSection(ReferencedComposite refComposite, int index, Matcher<?>... matchers){
		super(org.eclipse.ui.forms.widgets.Section.class, refComposite, index, matchers);
		setFocus();
	}
	
	public Control getControl() {
		return swtWidget.getClient();
	}

	public String getText() {
		return WidgetHandler.getInstance().getText(swtWidget);
	}
	
	protected void setFocus() {
		WidgetHandler.getInstance().setFocus(swtWidget);
	}
	
	public void setExpanded(final boolean expanded) {
		SectionHandler.getInstance().setExpanded(swtWidget, expanded);
	}
}
