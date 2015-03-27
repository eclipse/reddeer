package org.jboss.reddeer.swt.impl.table;

import org.eclipse.swt.graphics.Image;
import org.hamcrest.Matcher;
import org.jboss.reddeer.common.logging.Logger;
import org.jboss.reddeer.swt.api.Table;
import org.jboss.reddeer.swt.api.TableItem;
import org.jboss.reddeer.swt.api.TreeItem;
import org.jboss.reddeer.swt.handler.TableHandler;
import org.jboss.reddeer.swt.handler.TableItemHandler;
import org.jboss.reddeer.core.handler.WidgetHandler;
import org.jboss.reddeer.swt.impl.table.internal.BasicTable;
import org.jboss.reddeer.core.reference.ReferencedComposite;
import org.jboss.reddeer.swt.widgets.AbstractWidget;

public class AbstractTableItem extends AbstractWidget<org.eclipse.swt.widgets.TableItem> implements TableItem {
	
	private static final Logger log = Logger.getLogger(AbstractTableItem.class);

	protected AbstractTableItem(ReferencedComposite refComposite, int index, Matcher<?>... matchers){
		super(org.eclipse.swt.widgets.TableItem.class, refComposite, index, matchers);
	}
	
	protected AbstractTableItem(org.eclipse.swt.widgets.TableItem swtTableItem){
		super(swtTableItem);
	}
	
	/**
	 * See {@link TreeItem}
	 */
	@Override
	public void setChecked(final boolean check) {
		log.info((check ? "Check" : "Uncheck") + " table Item " + getText()
				+ ":");
		TableItemHandler.getInstance().setChecked(swtWidget, check);
	}

	@Override
	public boolean isChecked() {
		return TableItemHandler.getInstance().isChecked(swtWidget);
	}
	
	@Override
	public String getText() {
		return WidgetHandler.getInstance().getText(swtWidget);
	}
	
	@Override
	public String getText(int cellIndex) {
		return TableItemHandler.getInstance().getText(swtWidget, cellIndex);
	}

	@Override
	public boolean isSelected() {
		return TableItemHandler.getInstance().isSelected(swtWidget);
	}

	@Override
	public void select() {
		TableItemHandler.getInstance().select(swtWidget);
	}
	
	@Override
	public boolean isGrayed() {
		return TableHandler.getInstance().isGrayed(swtWidget);
	}
	
	@Override
	public Image getImage(int imageIndex) {
		return TableHandler.getInstance().getItemImage(swtWidget, imageIndex);
	}

	@Override
	public Table getParent() {
		return new BasicTable(TableItemHandler.getInstance().getParent(swtWidget));
	}
	
	@Override
	public void doubleClick(){
		log.info("Double click table item " + getText());
		TableHandler.getInstance().doubleClick(swtWidget, 0);
	}
	
	@Override
	public void doubleClick(int column){
		log.info("Double click column " + column + " of table item " + getText());
		TableHandler.getInstance().doubleClick(swtWidget, column);
	}
}
