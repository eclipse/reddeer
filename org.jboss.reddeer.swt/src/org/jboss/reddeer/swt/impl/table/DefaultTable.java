package org.jboss.reddeer.swt.impl.table;

import org.jboss.reddeer.swt.api.Table;
import org.jboss.reddeer.swt.lookup.impl.WidgetLookup;

/**
 * Default Table implementation
 * @author Jiri Peterka
 *
 */
public class DefaultTable extends AbstractTable implements Table {
	
	/**
	 * Default Table constructor
	 */
	public DefaultTable() {
		this(0);
	}
	/**
	 * Table with given index
	 * @param index of table
	 */
	public DefaultTable(int index) {
		super(WidgetLookup.getInstance().activeWidget(org.eclipse.swt.widgets.Table.class, index));
	}
	
}
