package org.jboss.reddeer.swt.api;


/**
 * API for Table manipulation
 * @author Jiri Peterka
 *
 */
public interface Table {

	/**
	 * Retursn cell string on given position
	 * @param row table row
	 * @param column table column
	 * @return text on given position
	 */
	String cell(int row, int column);

	/**
	 * Returns tabler row count
	 * @return row count
	 */
	int rowCount();
	
	/**
	 * Select rows on given indexes 
	 * @param index given row indexes
	 */
	void select(int... indexes);
	
	
}
