package org.jboss.reddeer.swt.impl.toolbar;

import org.hamcrest.Matcher;
import org.jboss.reddeer.common.logging.Logger;
import org.jboss.reddeer.swt.api.ToolItem;
import org.jboss.reddeer.swt.widgets.AbstractWidget;
import org.jboss.reddeer.core.exception.Thrower;
import org.jboss.reddeer.core.handler.ToolItemHandler;
import org.jboss.reddeer.core.handler.WidgetHandler;
import org.jboss.reddeer.core.lookup.ToolItemLookup;
import org.jboss.reddeer.core.reference.ReferencedComposite;

/**
 * Abstract class for all Toolbar implementations
 * @author Jiri Peterka
 *
 */
public abstract class AbstractToolItem extends AbstractWidget<org.eclipse.swt.widgets.ToolItem> implements ToolItem {
	
	protected AbstractToolItem(ReferencedComposite refComposite, int index, Matcher<?>... matchers) {
		super(org.eclipse.swt.widgets.ToolItem.class, refComposite == null ? ToolItemLookup.getInstance().findReferencedComposite() : refComposite, index, matchers);
	}

	private static final Logger log = Logger.getLogger(AbstractToolItem.class);

	/**
	 * See {@link ToolItem}}
	 */
	@Override
	public void click() {
		Thrower.objectIsNull(getSWTWidget(), "ToolItem is null" );
		log.info("Click tool item " + getToolTipText());
		ToolItemHandler.getInstance().click(getSWTWidget());
	}
	
	/**
	 * See {@link ToolItem}}
	 */
	@Override
	public String getToolTipText() {	
		String tooltipText;
		tooltipText = WidgetHandler.getInstance().getToolTipText(swtWidget);
		return tooltipText;
	}
	/**
	 * See {@link ToolItem}}
	 */
	@Override
	public boolean isSelected() {
		return ToolItemHandler.getInstance().isSelected(swtWidget);
	}
	/**
	 * See {@link ToolItem}}
	 */
	@Override
	public void toggle(boolean toggle) {
		log.info((toggle ? "Click" : "Unclick") + " tool item " + getToolTipText());
		if (isSelected() != toggle){
			click();
		}		
	}
	
	@Override
	public boolean isEnabled() {
		return WidgetHandler.getInstance().isEnabled(swtWidget);
	}
}
