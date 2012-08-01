package org.jboss.reddeer.swt.impl.table;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotTable;
import org.jboss.reddeer.swt.api.Table;
import org.jboss.reddeer.swt.util.Bot;

/**
 * Basic abstract class implementation for a table
 * @author Jiri Peterka
 *
 */
public abstract class BasicTable implements Table {
	protected final Log logger = LogFactory.getLog(BasicTable.class);
	SWTBotTable table;
	
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
		if (logger.isDebugEnabled()){
		      StringBuffer sbIndexes = new StringBuffer();
		      for (int index : indexes){
		        if (sbIndexes.length() > 0) {
		          sbIndexes.append(",");
		        }
		        sbIndexes.append(index);
		      }
		  logger.debug("Select table row(s): " + sbIndexes.toString());
		}
		Bot.get().table().select(indexes);
		
	}
		
}
