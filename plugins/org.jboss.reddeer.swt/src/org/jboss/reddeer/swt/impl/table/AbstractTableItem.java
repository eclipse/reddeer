package org.jboss.reddeer.swt.impl.table;

import org.eclipse.swt.graphics.Image;
import org.jboss.reddeer.common.logging.Logger;
import org.jboss.reddeer.swt.api.Table;
import org.jboss.reddeer.swt.api.TableItem;
import org.jboss.reddeer.swt.api.TreeItem;
import org.jboss.reddeer.swt.exception.SWTLayerException;
import org.jboss.reddeer.swt.handler.TableHandler;
import org.jboss.reddeer.swt.handler.TableItemHandler;
import org.jboss.reddeer.swt.handler.WidgetHandler;

public class AbstractTableItem implements TableItem {
	private static final Logger log = Logger.getLogger(AbstractTableItem.class);
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
		log.info((check ? "Check" : "Uncheck") + " table item " + getText()
				+ ":");
		TableItemHandler.getInstance().setChecked(tableItem, check);
	}

	@Override
	public boolean isChecked() {
		return TableItemHandler.getInstance().isChecked(tableItem);
	}
	
	@Override
	public String getText() {
		return WidgetHandler.getInstance().getText(tableItem);
	}
	
	@Override
	public String getText(int cellIndex) {
		return TableItemHandler.getInstance().getText(tableItem, cellIndex);
	}

	@Override
	public boolean isSelected() {
		return TableItemHandler.getInstance().isSelected(tableItem);
	}

	@Override
	public void select() {
		TableItemHandler.getInstance().select(tableItem);
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
		return new DefaultTable(TableItemHandler.getInstance().getParent(tableItem));
	}
	
	@Override
	public void doubleClick(){
		log.info("Double click table item " + getText());
		TableHandler.getInstance().doubleClick(tableItem, 0);
	}
	
	@Override
	public void doubleClick(int column){
		log.info("Double click column " + column + " of table item " + getText());
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
