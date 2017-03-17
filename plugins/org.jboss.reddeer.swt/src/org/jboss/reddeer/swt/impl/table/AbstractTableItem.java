/******************************************************************************* 
 * Copyright (c) 2016 Red Hat, Inc. 
 * Distributed under license by Red Hat, Inc. All rights reserved. 
 * This program is made available under the terms of the 
 * Eclipse Public License v1.0 which accompanies this distribution, 
 * and is available at http://www.eclipse.org/legal/epl-v10.html 
 * 
 * Contributors: 
 * Red Hat, Inc. - initial API and implementation 
 ******************************************************************************/ 
package org.jboss.reddeer.swt.impl.table;

import org.eclipse.swt.graphics.Image;
import org.hamcrest.Matcher;
import org.jboss.reddeer.common.logging.Logger;
import org.jboss.reddeer.swt.api.Table;
import org.jboss.reddeer.swt.api.TableItem;
import org.jboss.reddeer.swt.api.TreeItem;
import org.jboss.reddeer.core.handler.TableHandler;
import org.jboss.reddeer.core.handler.TableItemHandler;
import org.jboss.reddeer.core.handler.WidgetHandler;
import org.jboss.reddeer.core.reference.ReferencedComposite;
import org.jboss.reddeer.swt.widgets.AbstractWidget;

public abstract class AbstractTableItem extends AbstractWidget<org.eclipse.swt.widgets.TableItem> implements TableItem {
	
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
	 * @see org.jboss.reddeer.swt.api.TableItem#isChecked()
	 */
	@Override
	public boolean isChecked() {
		return TableItemHandler.getInstance().isChecked(swtWidget);
	}
	
	/* (non-Javadoc)
	 * @see org.jboss.reddeer.swt.api.TableItem#getText()
	 */
	@Override
	public String getText() {
		return WidgetHandler.getInstance().getText(swtWidget);
	}
	
	/* (non-Javadoc)
	 * @see org.jboss.reddeer.swt.api.TableItem#getText(int)
	 */
	@Override
	public String getText(int cellIndex) {
		return TableItemHandler.getInstance().getText(swtWidget, cellIndex);
	}

	/* (non-Javadoc)
	 * @see org.jboss.reddeer.swt.api.TableItem#isSelected()
	 */
	@Override
	public boolean isSelected() {
		return TableItemHandler.getInstance().isSelected(swtWidget);
	}

	/* (non-Javadoc)
	 * @see org.jboss.reddeer.swt.api.TableItem#select()
	 */
	@Override
	public void select() {
		TableItemHandler.getInstance().select(swtWidget);
	}
	
	/* (non-Javadoc)
	 * @see org.jboss.reddeer.swt.api.TableItem#isGrayed()
	 */
	@Override
	public boolean isGrayed() {
		return TableHandler.getInstance().isGrayed(swtWidget);
	}
	
	/* (non-Javadoc)
	 * @see org.jboss.reddeer.swt.api.TableItem#getImage(int)
	 */
	@Override
	public Image getImage(int imageIndex) {
		return TableHandler.getInstance().getItemImage(swtWidget, imageIndex);
	}

	/* (non-Javadoc)
	 * @see org.jboss.reddeer.swt.api.TableItem#getParent()
	 */
	@Override
	public Table getParent() {
		return new DefaultTable(TableItemHandler.getInstance().getParent(swtWidget));
	}
	
	/* (non-Javadoc)
	 * @see org.jboss.reddeer.swt.api.TableItem#doubleClick()
	 */
	@Override
	public void doubleClick(){
		log.info("Double click table item " + getText());
		TableHandler.getInstance().doubleClick(swtWidget, 0);
	}
	
	/* (non-Javadoc)
	 * @see org.jboss.reddeer.swt.api.TableItem#click()
	 */
	@Override
	public void click(){
		click(0);
	}
	
	/* (non-Javadoc)
	 * @see org.jboss.reddeer.swt.api.TableItem#click(int)
	 */
	@Override
	public void click(int column){
		log.info("Double click table item " + getText());
		TableHandler.getInstance().click(swtWidget, column);
	}
	
	/* (non-Javadoc)
	 * @see org.jboss.reddeer.swt.api.TableItem#doubleClick(int)
	 */
	@Override
	public void doubleClick(int column){
		log.info("Double click column " + column + " of table item " + getText());
		TableHandler.getInstance().doubleClick(swtWidget, column);
	}
}
