package org.jboss.reddeer.swt.impl.table;

import org.apache.log4j.Logger;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotTable;
import org.jboss.reddeer.swt.api.Table;

/**
 * Basic abstract class implementation for a table
 * @author Jiri Peterka
 * @author Rastislav Wagner
 *
 */
public abstract class AbstractTable implements Table {
	protected final Logger log = Logger.getLogger(this.getClass());
	protected SWTBotTable table;
	
	@Override
	public String cell(int row, int column) {
		String ret = table.cell(row, column);
		return ret;		
	}
	
	@Override
	public int rowCount() {
		int count = table.rowCount();
		return count;
	}

	@Override
	public void select(int... indexes) {
		if (log.isDebugEnabled()){
		      StringBuffer sbIndexes = new StringBuffer();
		      for (int index : indexes){
		        if (sbIndexes.length() > 0) {
		          sbIndexes.append(",");
		        }
		        sbIndexes.append(index);
		      }
		  log.debug("Select table row(s): " + sbIndexes.toString());
		}
		table.select(indexes);
		
	}
	
	@Override
	public void select(String... items) {
		table.select(items);
	}
	
	@Override
	public void select(String item, int columnIndex){
		select(table.indexOf(item, columnIndex));
	}
		
}
