package org.jboss.reddeer.uiforms.impl.expandablecomposite;

import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.forms.widgets.ExpandableComposite;
import org.jboss.reddeer.swt.handler.WidgetHandler;
import org.jboss.reddeer.uiforms.handler.ExpandableCompositeHandler;

/**
 * Common ancestor for all
 * {@link org.jboss.reddeer.uiforms.api.ExpandableComposite} implementations.
 *
 * @author Radoslav Rabara
 * @since 0.6
 *
 */
public abstract class AbstractExpandableComposite implements org.jboss.reddeer.uiforms.api.ExpandableComposite {

	protected ExpandableComposite composite;

	@Override
	public String getText() {
		return WidgetHandler.getInstance().getText(composite);
	}

	@Override
	public Control getControl() {
		return composite;
	}

	@Override
	public void setExpanded(boolean expanded) {
		ExpandableCompositeHandler.getInstance()
				.setExpanded(composite, expanded);
	}

	@Override
	public boolean isExpanded() {
		return ExpandableCompositeHandler.getInstance().isExpanded(composite);
	}

	@Override
	public ExpandableComposite getSWTWidget() {
		return composite;
	}

	@Override
	public boolean isEnabled() {
		return WidgetHandler.getInstance().isEnabled(composite);
	}
}
