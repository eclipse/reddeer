package org.jboss.reddeer.eclipse.ui.views.markers;

import java.util.ArrayList;
import java.util.List;

import org.jboss.reddeer.swt.api.TableItem;
import org.jboss.reddeer.swt.impl.table.DefaultTable;

/**
 * Represents QuickFixPage in QuickFixWizard
 * @author rawagner
 *
 */
public class QuickFixPage {
	
	/**
	 * Get proposed fixes
	 * @return proposed fixes
	 */
	public List<String> getAvailableFixes(){
		List<String> toReturn = new ArrayList<String>();
		List<TableItem> items = new DefaultTable().getItems();
		for(TableItem i: items){
			toReturn.add(i.getText());
		}
		return toReturn;
	}
	
	/**
	 * Select fix
	 * @param fix to select
	 */
	public void selectFix(String fix){
		List<TableItem> items = new DefaultTable().getItems();
		for(TableItem i: items){
			if(i.getText().equals(fix)){
				i.select();
				return;
			}
		}
	}

}
