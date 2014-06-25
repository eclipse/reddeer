package org.jboss.reddeer.swt.impl.list;

import org.jboss.reddeer.common.logging.Logger;
import org.jboss.reddeer.swt.api.List;
import org.jboss.reddeer.swt.handler.ListHandler;
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
		ListHandler.getInstance().select(list,listItem);
	}

	public void select(int listItemIndex) {
		ListHandler.getInstance().select(list,listItemIndex);
	}

	public String[] getListItems() {
		return ListHandler.getInstance().getItems(list);
	}

	public void deselectAll() {
		ListHandler.getInstance().deselectAll(list);
	}
	
	public void selectAll() {
		ListHandler.getInstance().selectAll(list);
	}

	public void select(String... listItems) {
		ListHandler.getInstance().select(list,listItems);
	}

	public void select(int... indices) {
		ListHandler.getInstance().select(list,indices);
	}
	
	public org.eclipse.swt.widgets.List getSWTWidget(){
		return list;
	}
	
	@Override
	public boolean isEnabled() {
		return WidgetHandler.getInstance().isEnabled(list);
	}

}
