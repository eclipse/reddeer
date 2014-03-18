package org.jboss.reddeer.swt.impl.table;

import org.jboss.reddeer.swt.api.Table;
import org.jboss.reddeer.swt.lookup.WidgetLookup;
import org.jboss.reddeer.swt.reference.ReferencedComposite;

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
	 * Default Table constructor inside given composite
	 * @param referencedComposite
	 */
	public DefaultTable(ReferencedComposite referencedComposite) {
		this(referencedComposite, 0);
	}
	
	/**
	 * Table with given index 
	 * @param index of table
	 */
	public DefaultTable(int index) {
		this(null,index);
	}
	
	/**
	 * Table with given index inside given composite
	 * @param referencedComposite
	 * @param index of table
	 */
	public DefaultTable(ReferencedComposite referencedComposite, int index) {
		super(WidgetLookup.getInstance().activeWidget(referencedComposite, org.eclipse.swt.widgets.Table.class, index));
	}
	
	protected DefaultTable(org.eclipse.swt.widgets.Table table){
		super(table);
	}
}
