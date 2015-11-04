package org.jboss.reddeer.swt.impl.expandbar;

import org.eclipse.swt.widgets.Control;
import org.jboss.reddeer.common.logging.Logger;
import org.jboss.reddeer.swt.api.ExpandBar;
import org.jboss.reddeer.swt.api.ExpandBarItem;
import org.jboss.reddeer.core.handler.ExpandBarItemHandler;
import org.jboss.reddeer.core.handler.WidgetHandler;
import org.jboss.reddeer.swt.impl.expandbar.internal.BasicExpandBar;
import org.jboss.reddeer.swt.widgets.AbstractWidget;
import org.jboss.reddeer.common.wait.AbstractWait;
import org.jboss.reddeer.common.wait.TimePeriod;

/**
 * Basic ExpandBarItem class is abstract class for all Expand Bar Item implementations
 * 
 * @author Vlado Pakan
 * 
 */
public abstract class AbstractExpandBarItem extends AbstractWidget<org.eclipse.swt.widgets.ExpandItem> implements ExpandBarItem {

	private static final Logger logger = Logger.getLogger(AbstractExpandBarItem.class);

	protected org.eclipse.swt.widgets.ExpandBar swtParent;

	protected AbstractExpandBarItem(final org.eclipse.swt.widgets.ExpandItem swtExpandItem) {
		super(swtExpandItem);
		this.swtParent = ExpandBarItemHandler.getInstance().getParent(swtExpandItem);
	}
	/**
	 * See {@link ExpandBarItem}
	 */
	@Override
	public String getText() {
		return WidgetHandler.getInstance().getText(swtWidget);
	}

	/**
	 * See {@link ExpandBarItem}
	 */
	@Override
	public String getToolTipText() {
		return WidgetHandler.getInstance().getToolTipText(swtWidget);
	}

	/**
	 * See {@link ExpandBarItem}
	 */
	@Override
	public void expand() {
		expand(TimePeriod.SHORT);
	}
	/**
	 * See {@link ExpandBarItem}
	 */
	@Override
	public void expand(TimePeriod timePeriod) {
		logger.debug("Expand Expand Bar Item " + getText());
		if (!isExpanded()) {
			ExpandBarItemHandler.getInstance().expand(getSWTWidget(), getSWTParent());
			AbstractWait.sleep(timePeriod);
			logger.info("Expand Bar Item " + getText()
					+ " has been expanded");
		} else {
			logger.debug("Expand Bar Item " + getText()
					+ " is already expanded. No action performed");
		}
	}
	/**
	 * See {@link ExpandBarItem}
	 */
	@Override
	public void collapse() {
		logger.debug("Collapse Expand Bar Item " + getText());
		if (isExpanded()) {
			ExpandBarItemHandler.getInstance().collapse(getSWTWidget(), getSWTParent());
			logger.info("Expand Bar Item " + getText()
					+ " has been collapsed");
		} else {
			logger.debug("Expand Bar Item " + getText()
					+ " is already collapsed. No action performed");
		}
	}
	
	/**
	 * Return control of Expand Bar Item
	 */
	@Override
	public Control getControl() {
		return swtWidget.getControl();
	}

	public org.eclipse.swt.widgets.ExpandBar getSWTParent() {
		return swtParent;
	}
	/**
	 * See {@link ExpandBarItem}
	 */
	@Override
	public ExpandBar getParent() {
		return new BasicExpandBar(swtParent);
	}
	/**
	 * See {@link ExpandBarItem}
	 */
	@Override
	public boolean isExpanded() {
		return ExpandBarItemHandler.getInstance().isExpanded(getSWTWidget());
	}
}