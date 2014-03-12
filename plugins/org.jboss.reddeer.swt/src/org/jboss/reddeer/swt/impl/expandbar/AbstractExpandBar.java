package org.jboss.reddeer.swt.impl.expandbar;

import java.util.List;

import org.jboss.reddeer.junit.logging.Logger;
import org.jboss.reddeer.swt.api.ExpandBar;
import org.jboss.reddeer.swt.api.ExpandBarItem;
import org.jboss.reddeer.swt.exception.SWTLayerException;
import org.jboss.reddeer.swt.handler.ExpandBarHandler;
import org.jboss.reddeer.swt.handler.WidgetHandler;
/**
 * Abstract class for all Expand Bar implementations
 * 
 * @author Vlado Pakan
 * 
 */
public abstract class AbstractExpandBar implements ExpandBar {

	protected final Logger logger = Logger.getLogger(this.getClass());

	protected org.eclipse.swt.widgets.ExpandBar swtExpandBar;

	protected AbstractExpandBar(final org.eclipse.swt.widgets.ExpandBar swtExpandBar) {
		if (swtExpandBar != null) {
			this.swtExpandBar = swtExpandBar;
		} else {
			throw new SWTLayerException(
					"SWT Expand Bar passed to constructor is null");
		}
	}
	/**
	 * See {@link ExpandBar}
	 */
	@Override
	public int getItemsCount() {
		return ExpandBarHandler.getInstance().getItems(this.getSWTWidget()).size();
	}
	/**
	 * See {@link ExpandBar}
	 */
	@Override
	public List<ExpandBarItem> getItems() {
		return ExpandBarHandler.getInstance().getItems(this.getSWTWidget());
	}
	/**
	 * See {@link ExpandBar}
	 */
	@Override
	public void setFocus() {
		WidgetHandler.getInstance().setFocus(swtExpandBar);

	}
	/**
	 * See {@link ExpandBar}
	 */
	@Override
	public void expandAll() {
		for (ExpandBarItem expandBarItem : getItems()){
			expandBarItem.expand();
		}
	}
	/**
	 * See {@link ExpandBar}
	 */
	@Override
	public void collapseAll() {
		for (ExpandBarItem expandBarItem : getItems()){
			expandBarItem.collapse();
		}
	}
	/**
	 * See {@link ExpandBar}
	 */
	@Override
	public org.eclipse.swt.widgets.ExpandBar getSWTWidget() {
		return swtExpandBar;
	}
	
	@Override
	public boolean isEnabled() {
		return WidgetHandler.getInstance().isEnabled(swtExpandBar);
	}
}
