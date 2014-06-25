package org.jboss.reddeer.swt.impl.expandbar;

import org.eclipse.swt.widgets.Control;
import org.jboss.reddeer.common.logging.Logger;
import org.jboss.reddeer.swt.api.ExpandBar;
import org.jboss.reddeer.swt.api.ExpandBarItem;
import org.jboss.reddeer.swt.exception.SWTLayerException;
import org.jboss.reddeer.swt.handler.ExpandBarItemHandler;
import org.jboss.reddeer.swt.handler.WidgetHandler;
import org.jboss.reddeer.swt.impl.expandbar.internal.BasicExpandBar;
import org.jboss.reddeer.swt.wait.AbstractWait;
import org.jboss.reddeer.swt.wait.TimePeriod;

/**
 * Basic ExpandBarItem class is abstract class for all Expand Bar Item implementations
 * 
 * @author Vlado Pakan
 * 
 */
public abstract class AbstractExpandBarItem implements ExpandBarItem {

	protected final Logger logger = Logger.getLogger(this.getClass());

	protected org.eclipse.swt.widgets.ExpandItem swtExpandItem;
	protected org.eclipse.swt.widgets.ExpandBar swtParent;

	protected AbstractExpandBarItem(final org.eclipse.swt.widgets.ExpandItem swtExpandItem) {
		if (swtExpandItem != null) {
			this.swtExpandItem = swtExpandItem;
			this.swtParent = ExpandBarItemHandler.getInstance().getParent(swtExpandItem);
		} else {
			throw new SWTLayerException(
					"SWT Expand Item passed to constructor is null");
		}

	}
	/**
	 * See {@link ExpandBarItem}
	 */
	@Override
	public String getText() {
		return WidgetHandler.getInstance().getText(this.swtExpandItem);
	}

	/**
	 * See {@link ExpandBarItem}
	 */
	@Override
	public String getToolTipText() {
		return WidgetHandler.getInstance().getToolTipText(this.swtExpandItem);
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
		logger.debug("Expanding Expand Bar Item " + getText());
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
		logger.debug("Collapsing Expand Bar Item " + getText());
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
	 * Return swt widget of Expand Bar Item
	 */
	@Override
	public org.eclipse.swt.widgets.ExpandItem getSWTWidget() {
		return swtExpandItem;
	}
	
	/**
	 * Return control of Expand Bar Item
	 */
	@Override
	public Control getControl() {
		return swtExpandItem.getControl();
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
		return ExpandBarItemHandler.getInstance().isExpanded(this.swtExpandItem);
	}
	
	@Override
	public boolean isEnabled() {
		return WidgetHandler.getInstance().isEnabled(swtParent);
	}
}