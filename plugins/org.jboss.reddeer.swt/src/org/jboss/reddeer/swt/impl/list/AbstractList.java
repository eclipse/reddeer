package org.jboss.reddeer.swt.impl.list;

import org.hamcrest.Matcher;
import org.jboss.reddeer.common.logging.Logger;
import org.jboss.reddeer.common.logging.LoggingUtils;
import org.jboss.reddeer.swt.api.List;
import org.jboss.reddeer.core.handler.ListHandler;
import org.jboss.reddeer.core.reference.ReferencedComposite;
import org.jboss.reddeer.swt.widgets.AbstractWidget;

/**
 * Abstract class for all List implementations
 * 
 * @author Rastislav Wagner
 * 
 */
public abstract class AbstractList extends AbstractWidget<org.eclipse.swt.widgets.List> implements List {

	private static final Logger logger = Logger.getLogger(AbstractList.class);

	protected AbstractList(ReferencedComposite refComposite, int index, Matcher<?>... matchers) {
		super(org.eclipse.swt.widgets.List.class, refComposite, index, matchers);
	}

	public void select(String listItem) {
		ListHandler.getInstance().select(swtWidget,listItem);
	}

	public void select(int listItemIndex) {
		ListHandler.getInstance().select(swtWidget,listItemIndex);
	}

	public String[] getListItems() {
		return ListHandler.getInstance().getItems(swtWidget);
	}

	public void deselectAll() {
		ListHandler.getInstance().deselectAll(swtWidget);
	}
	
	public void selectAll() {
		ListHandler.getInstance().selectAll(swtWidget);
	}

	public void select(String... listItems) {
		logger.info("Select list items (" + LoggingUtils.format(listItems) + ")");
		ListHandler.getInstance().select(swtWidget,listItems);
	}

	public void select(int... indices) {
		logger.info("Select list items with indices (" + LoggingUtils.format(indices) + ")");
		ListHandler.getInstance().select(swtWidget,indices);
	}
}
