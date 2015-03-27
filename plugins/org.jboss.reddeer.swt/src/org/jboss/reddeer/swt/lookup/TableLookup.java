package org.jboss.reddeer.swt.lookup;

import org.eclipse.swt.widgets.Table;
import org.jboss.reddeer.core.reference.ReferencedComposite;


/**
 * Table lookup containing lookup routines for Tale widget type
 * @author Rastislav Wagner
 *
 */
public class TableLookup {

	private static TableLookup instance = null;

	private TableLookup() {
	}

	/**
	 * Creates and returns instance of Table Lookup
	 * 
	 * @return ListLookup instance
	 */
	public static TableLookup getInstance() {
		if (instance == null)
			instance = new TableLookup();
		return instance;
	}

	/**
	 * Return Table instance
	 * 
	 * @param index table index
	 * @return Table Widget matching criteria
	 */
	public Table getTable(ReferencedComposite refComposite, int index) {
		return (Table)WidgetLookup.getInstance().activeWidget(refComposite, Table.class, index);
	}

}