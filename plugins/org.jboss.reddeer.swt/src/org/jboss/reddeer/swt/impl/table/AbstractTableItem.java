package org.jboss.reddeer.swt.impl.table;

import org.eclipse.swt.graphics.Image;
import org.jboss.reddeer.junit.logging.Logger;
import org.jboss.reddeer.swt.api.Table;
import org.jboss.reddeer.swt.api.TableItem;
import org.jboss.reddeer.swt.api.TreeItem;
import org.jboss.reddeer.swt.exception.SWTLayerException;
import org.jboss.reddeer.swt.handler.TableHandler;
import org.jboss.reddeer.swt.handler.WidgetHandler;

public class AbstractTableItem implements TableItem {
	protected final Logger log = Logger.getLogger(this.getClass());
	protected org.eclipse.swt.widgets.TableItem tableItem;
	
	protected AbstractTableItem(org.eclipse.swt.widgets.TableItem swtTableItem){
		  if (swtTableItem != null){
		    this.tableItem = swtTableItem;  
		  }
		  else {
		     throw new SWTLayerException("SWT Table item passed to constructor is null");
		  }	  
	}
	
	/**
	 * See {@link TreeItem}
	 */
	@Override
	public void setChecked(final boolean check) {
		log.info((check ? "Check" : "Uncheck") + "Table Item " + getText()
				+ ":");
		WidgetHandler.getInstance().setChecked(tableItem, check);
	}

	@Override
	public boolean isChecked() {
		return WidgetHandler.getInstance().isChecked(tableItem);
	}
	
	@Override
	public String getText() {
		return WidgetHandler.getInstance().getText(tableItem);
	}
	
	@Override
	public String getText(int cellIndex) {
		return WidgetHandler.getInstance().getText(tableItem, cellIndex);
	}

	@Override
	public boolean isSelected() {
		return WidgetHandler.getInstance().isSelected(tableItem);
	}

	@Override
	public void select() {
		WidgetHandler.getInstance().select(tableItem);
	}
	
	@Override
	public boolean isGrayed() {
		return TableHandler.getInstance().isGrayed(tableItem);
	}
	
	@Override
	public Image getImage(int imageIndex) {
		return TableHandler.getInstance().getItemImage(tableItem, imageIndex);
	}

	@Override
	public Table getParent() {
		return (Table)WidgetHandler.getInstance().getParent(tableItem);
	}
	
	@Override
	public void doubleClick(){
		TableHandler.getInstance().doubleClick(tableItem, 0);
	}
	
	@Override
	public void doubleClick(int column){
		TableHandler.getInstance().doubleClick(tableItem, column);
	}

	@Override
	public org.eclipse.swt.widgets.TableItem getSWTWidget() {
		return tableItem;
	}
	
	@Override
	public boolean isEnabled() {
		return WidgetHandler.getInstance().isEnabled(tableItem);
	}

}
