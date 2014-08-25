package org.jboss.reddeer.uiforms.impl.expandablecomposite;

import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.forms.widgets.ExpandableComposite;
import org.hamcrest.Matcher;
import org.jboss.reddeer.swt.handler.WidgetHandler;
import org.jboss.reddeer.swt.reference.ReferencedComposite;
import org.jboss.reddeer.swt.widgets.AbstractWidget;
import org.jboss.reddeer.uiforms.handler.ExpandableCompositeHandler;

/**
 * Common ancestor for all
 * {@link org.jboss.reddeer.uiforms.api.ExpandableComposite} implementations.
 *
 * @author Radoslav Rabara
 * @since 0.6
 *
 */
public abstract class AbstractExpandableComposite extends AbstractWidget<ExpandableComposite> implements org.jboss.reddeer.uiforms.api.ExpandableComposite {

	protected AbstractExpandableComposite(ReferencedComposite refComposite, int index, Matcher<?>... matchers){
		super(ExpandableComposite.class, refComposite, index, matchers);
	}

	@Override
	public String getText() {
		return WidgetHandler.getInstance().getText(getSWTWidget());
	}

	@Override
	public Control getControl() {
		return getSWTWidget();
	}

	@Override
	public void setExpanded(boolean expanded) {
		ExpandableCompositeHandler.getInstance()
				.setExpanded(getSWTWidget(), expanded);
	}

	@Override
	public boolean isExpanded() {
		return ExpandableCompositeHandler.getInstance().isExpanded(getSWTWidget());
	}
}
