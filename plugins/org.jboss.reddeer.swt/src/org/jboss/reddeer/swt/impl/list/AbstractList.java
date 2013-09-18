package org.jboss.reddeer.swt.impl.list;

import org.apache.log4j.Logger;
import org.jboss.reddeer.swt.api.List;
import org.jboss.reddeer.swt.handler.WidgetHandler;

/**
 * Abstract class for all List implementations
 * 
 * @author Rastislav Wagner
 * 
 */
public abstract class AbstractList implements List {

	protected org.eclipse.swt.widgets.List list;

	protected final Logger logger = Logger.getLogger(this.getClass());

	public void select(String listItem) {
		WidgetHandler.getInstance().select(list,listItem);
	}

	public void select(int listItemIndex) {
		WidgetHandler.getInstance().select(list,listItemIndex);
	}

	public String[] getListItems() {
		return WidgetHandler.getInstance().getItems(list);
	}

	public void deselect() {
		WidgetHandler.getInstance().deselect(list);
	}
	
	public void selectAll() {
		WidgetHandler.getInstance().selectAll(list);
	}

	public void select(String... listItems) {
		WidgetHandler.getInstance().select(list,listItems);
	}

	public void select(int... indices) {
		WidgetHandler.getInstance().select(list,indices);
	}

}
