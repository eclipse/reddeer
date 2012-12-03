package org.jboss.reddeer.swt.impl.table;

import org.jboss.reddeer.swt.api.Table;
import org.jboss.reddeer.swt.util.Bot;

/**
 * Default Table implementation
 * @author Jiri Peterka
 *
 */
public class DefaultTable extends AbstractTable implements Table {

	public DefaultTable() {
		table = Bot.get().table();
	}
	/**
	 * Table with given index
	 * @param index of table
	 */
	public DefaultTable(int index) {
		table = Bot.get().table(index);
	}
	
	/**
	 * Table with given index in given Group
	 * @param index of table
	 * @param inGroup in group
	 */
	public DefaultTable(String inGroup, int index){
		table = Bot.get().tableInGroup(inGroup, index);
	}
	
}
