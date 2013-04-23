package org.jboss.reddeer.swt.impl.table;

import org.apache.log4j.Logger;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotTable;
import org.eclipse.swtbot.swt.finder.widgets.TimeoutException;
import org.jboss.reddeer.swt.api.Table;
import org.jboss.reddeer.swt.condition.TableHasRows;
import org.jboss.reddeer.swt.exception.SWTLayerException;
import org.jboss.reddeer.swt.wait.WaitUntil;

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
		waitUntilTableHasRows(this);
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
		waitUntilTableHasRows(this);
		table.select(indexes);
		
	}
	
	@Override
	public void select(String... items) {
		new WaitUntil(new TableHasRows(this));
		table.select(items);
	}
	
	@Override
	public void select(String item, int columnIndex){
		select(table.indexOf(item, columnIndex));
	}
	
	@Override
	public void check(String item){
		table.getTableItem(item).check();
	}
	
	@Override
	public void check(int itemIndex){
		table.getTableItem(itemIndex).check();
	}
	
	private void waitUntilTableHasRows(Table table) {
		try {
			new WaitUntil(new TableHasRows(table));
		}catch (TimeoutException te) {
			throw new SWTLayerException(te.getLocalizedMessage());
		}
	}
		
}
