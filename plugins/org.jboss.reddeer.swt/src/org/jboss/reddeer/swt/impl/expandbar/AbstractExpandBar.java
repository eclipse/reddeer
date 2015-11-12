package org.jboss.reddeer.swt.impl.expandbar;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.widgets.ExpandItem;
import org.hamcrest.Matcher;
import org.jboss.reddeer.common.logging.Logger;
import org.jboss.reddeer.swt.api.ExpandBar;
import org.jboss.reddeer.swt.api.ExpandBarItem;
import org.jboss.reddeer.core.handler.ExpandBarHandler;
import org.jboss.reddeer.core.handler.WidgetHandler;
import org.jboss.reddeer.swt.impl.expandbar.internal.BasicExpandBarItem;
import org.jboss.reddeer.core.reference.ReferencedComposite;
import org.jboss.reddeer.swt.widgets.AbstractWidget;

/**
 * Abstract class for all Expand Bar implementations
 * 
 * @author Vlado Pakan
 * 
 */
public abstract class AbstractExpandBar extends AbstractWidget<org.eclipse.swt.widgets.ExpandBar> implements ExpandBar {

	private static final Logger logger = Logger.getLogger(AbstractExpandBar.class);

	protected AbstractExpandBar(org.eclipse.swt.widgets.ExpandBar swtExpandBar){
		super(swtExpandBar);
	}
	
	protected AbstractExpandBar(ReferencedComposite referencedComposite, int index, Matcher<?>... matchers) {
		super(org.eclipse.swt.widgets.ExpandBar.class, referencedComposite, index, matchers);
	}
	
	/**
	 * See {@link ExpandBar}.
	 *
	 * @return the items count
	 */
	@Override
	public int getItemsCount() {
		return getItems().size();
	}
	
	/**
	 * See {@link ExpandBar}.
	 *
	 * @return the items
	 */
	@Override
	public List<ExpandBarItem> getItems() {
		List<org.eclipse.swt.widgets.ExpandItem> items = ExpandBarHandler.getInstance().getSWTItems(this.getSWTWidget());
		List<ExpandBarItem> result = new ArrayList<ExpandBarItem>();
		for (ExpandItem item: items) {
			result.add(new BasicExpandBarItem(item));
		}
		return result;
	}
	
	/**
	 * See {@link ExpandBar}.
	 */
	@Override
	public void setFocus() {
		WidgetHandler.getInstance().setFocus(this.getSWTWidget());

	}
	
	/**
	 * See {@link ExpandBar}.
	 */
	@Override
	public void expandAll() {
		logger.info("Expand all expand bar items");
		for (ExpandBarItem expandBarItem : getItems()){
			expandBarItem.expand();
		}
	}
	
	/**
	 * See {@link ExpandBar}.
	 */
	@Override
	public void collapseAll() {
		logger.info("Collapse all expand bar items");
		for (ExpandBarItem expandBarItem : getItems()){
			expandBarItem.collapse();
		}
	}
}
