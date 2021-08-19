/*******************************************************************************
 * Copyright (c) 2017 Red Hat, Inc and others.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     Red Hat, Inc - initial API and implementation
 *******************************************************************************/
package org.eclipse.reddeer.swt.impl.table;

import org.eclipse.swt.graphics.Image;
import org.hamcrest.Matcher;
import org.eclipse.reddeer.common.logging.Logger;
import org.eclipse.reddeer.swt.api.Control;
import org.eclipse.reddeer.swt.api.Table;
import org.eclipse.reddeer.swt.api.TableItem;
import org.eclipse.reddeer.swt.api.TreeItem;
import org.eclipse.reddeer.core.handler.TableItemHandler;
import org.eclipse.reddeer.core.reference.ReferencedComposite;
import org.eclipse.reddeer.swt.widgets.AbstractItem;

public abstract class AbstractTableItem extends AbstractItem<org.eclipse.swt.widgets.TableItem> implements TableItem {
	
	private static final Logger log = Logger.getLogger(AbstractTableItem.class);

	protected AbstractTableItem(ReferencedComposite refComposite, int index, Matcher<?>... matchers){
		super(org.eclipse.swt.widgets.TableItem.class, refComposite, index, matchers);
	}
	
	protected AbstractTableItem(org.eclipse.swt.widgets.TableItem swtTableItem){
		super(swtTableItem);
	}
	
	/**
	 * See {@link TreeItem}.
	 *
	 * @param check the new checked
	 */
	@Override
	public void setChecked(final boolean check) {
		log.info((check ? "Check" : "Uncheck") + " table Item " + getText()
				+ ":");
		TableItemHandler.getInstance().setChecked(swtWidget, check);
	}

	/* (non-Javadoc)
	 * @see org.eclipse.reddeer.swt.api.TableItem#isChecked()
	 */
	@Override
	public boolean isChecked() {
		return TableItemHandler.getInstance().isChecked(swtWidget);
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.reddeer.swt.api.TableItem#getText(int)
	 */
	@Override
	public String getText(int cellIndex) {
		return TableItemHandler.getInstance().getText(swtWidget, cellIndex);
	}

	/* (non-Javadoc)
	 * @see org.eclipse.reddeer.swt.api.TableItem#isSelected()
	 */
	@Override
	public boolean isSelected() {
		return TableItemHandler.getInstance().isSelected(swtWidget);
	}

	/* (non-Javadoc)
	 * @see org.eclipse.reddeer.swt.api.TableItem#select()
	 */
	@Override
	public void select() {
		TableItemHandler.getInstance().select(swtWidget);
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.reddeer.swt.api.TableItem#isGrayed()
	 */
	@Override
	public boolean isGrayed() {
		return TableItemHandler.getInstance().isGrayed(swtWidget);
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.reddeer.swt.api.TableItem#getImage(int)
	 */
	@Override
	public Image getImage(int imageIndex) {
		return TableItemHandler.getInstance().getImage(swtWidget, imageIndex);
	}

	/* (non-Javadoc)
	 * @see org.eclipse.reddeer.swt.api.TableItem#getParent()
	 */
	@Override
	public Table getParent() {
		return new DefaultTable(TableItemHandler.getInstance().getParent(swtWidget));
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.reddeer.swt.api.TableItem#doubleClick()
	 */
	@Override
	public void doubleClick(){
		log.info("Double click table item " + getText());
		TableItemHandler.getInstance().doubleClick(swtWidget, 0);
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.reddeer.swt.api.TableItem#click()
	 */
	@Override
	public void click(){
		click(0);
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.reddeer.swt.api.TableItem#click(int)
	 */
	@Override
	public void click(int column){
		log.info("Double click table item " + getText());
		TableItemHandler.getInstance().click(swtWidget, column);
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.reddeer.swt.api.TableItem#doubleClick(int)
	 */
	@Override
	public void doubleClick(int column){
		log.info("Double click column " + column + " of table item " + getText());
		TableItemHandler.getInstance().doubleClick(swtWidget, column);
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.reddeer.swt.api.TableItem#setText(String)
	 */
	@Override
	public void setText (String text) {
		log.info("Set text " + text);
		TableItemHandler.getInstance().setText(swtWidget, text);
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.reddeer.swt.api.TableItem#setText(String, int)
	 */
	@Override
	public void setText (int index, String text){
		log.info("Set text " + text + "in column " + index);
		TableItemHandler.getInstance().setText(swtWidget, index, text);
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.reddeer.swt.api.TableItem#setText(String [])
	 */
	@Override
	public void setText (String [] strings) {
		log.info("Set text array: " + strings.toString());
		TableItemHandler.getInstance().setText(swtWidget, strings);
	}
	
	@Override
	public Control<?> getParentControl() {
		return getParent();
	}
}
