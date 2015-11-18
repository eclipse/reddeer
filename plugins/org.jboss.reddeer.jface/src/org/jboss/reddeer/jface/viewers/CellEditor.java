package org.jboss.reddeer.jface.viewers;

import java.lang.reflect.Field;

import org.eclipse.jface.viewers.ColumnViewerEditor;
import org.eclipse.jface.viewers.ViewerColumn;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.TableColumn;
import org.jboss.reddeer.core.handler.WidgetHandler;
import org.jboss.reddeer.core.reference.ReferencedComposite;
import org.jboss.reddeer.core.util.Display;
import org.jboss.reddeer.core.util.ResultRunnable;
import org.jboss.reddeer.jface.exception.JFaceLayerException;
import org.jboss.reddeer.swt.api.TableItem;

/**
 * This class helps to activate, deactivate and find an activated edit control using a referenced composite.
 * 
 * @author apodhrad
 *
 */
public class CellEditor implements ReferencedComposite {

	protected static final String COLUMN_VIEWER_ID = "org.eclipse.jface.columnViewer";

	protected TableItem tableItem;
	protected int index;

	/**
	 * Constructor sets a given table item with default cell index 0.
	 * 
	 * @param tableItem
	 *            Table item
	 */
	public CellEditor(TableItem tableItem) {
		this(tableItem, 0);
	}

	/**
	 * Constructor sets a given table item with the specified cell index.
	 * 
	 * @param tableItem
	 *            Table item
	 * @param index
	 *            Cell index
	 */
	public CellEditor(TableItem tableItem, int index) {
		this.tableItem = tableItem;
		this.index = index;
	}

	/**
	 * Activate a cell editor using a simple click.
	 */
	public void activate() {
		tableItem.click(index);
	}

	/**
	 * Activate a cell editor using double click.
	 */
	public void activateByDoubleClick() {
		tableItem.doubleClick(index);
	}

	/**
	 * Return whether a cell editor is activated.
	 * 
	 * @return true if a cell editor is activated, false otherwise
	 */
	public boolean isActivated() {
		return getControl() != null;
	}

	/**
	 * Deactivate a cell editor using focus out event.
	 */
	public void deactivate() {
		if (isActivated()) {
			WidgetHandler.getInstance().notify(SWT.FocusOut, getControl());
		}
	}

	/**
	 * Return a table item of this cell editor.
	 * 
	 * @return Table item
	 */
	public TableItem getTableItem() {
		return tableItem;
	}

	/* (non-Javadoc)
	 * @see org.jboss.reddeer.core.reference.ReferencedComposite#getControl()
	 */
	@Override
	public Control getControl() {
		org.eclipse.jface.viewers.CellEditor jFaceCellEditor = getJFaceCellEditor();
		if (jFaceCellEditor == null) {
			return null;
		}
		return jFaceCellEditor.getControl();
	}

	/**
	 * Return org.eclipse.jface.viewers.CellEditor.
	 * 
	 * @return JFace cell editor
	 */
	protected org.eclipse.jface.viewers.CellEditor getJFaceCellEditor() {
		return Display.syncExec(new ResultRunnable<org.eclipse.jface.viewers.CellEditor>() {

			@Override
			public org.eclipse.jface.viewers.CellEditor run() {
				org.eclipse.jface.viewers.CellEditor cellEditor = null;

				TableColumn col = tableItem.getParent().getSWTWidget().getColumn(index);
				Object colData = col.getData(COLUMN_VIEWER_ID);
				ViewerColumn viewCol = (ViewerColumn) colData;
				ColumnViewerEditor colViewerEditor = viewCol.getViewer().getColumnViewerEditor();

				try {
					Field f = ColumnViewerEditor.class.getDeclaredField("cellEditor");
					if (f == null) {
						throw new JFaceLayerException("Cannot find a cell editor at index '" + index
								+ "'. Probably you didn't activate it.");
					}
					f.setAccessible(true);
					Object obj = f.get(colViewerEditor);
					if (obj instanceof org.eclipse.jface.viewers.CellEditor) {
						cellEditor = (org.eclipse.jface.viewers.CellEditor) obj;
					}
				} catch (Exception e) {
					throw new JFaceLayerException("Cannot find a cell editor.", e);
				}

				return cellEditor;
			}

		});
	}
}
