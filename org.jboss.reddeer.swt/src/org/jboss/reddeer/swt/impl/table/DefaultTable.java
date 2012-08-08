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
	
}
