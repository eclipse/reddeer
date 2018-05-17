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
package org.eclipse.reddeer.swt.impl.ctab;

import org.eclipse.swt.widgets.Control;
import org.hamcrest.Matcher;
import org.eclipse.reddeer.core.handler.CTabFolderHandler;
import org.eclipse.reddeer.core.handler.ItemHandler;
import org.eclipse.reddeer.core.reference.ReferencedComposite;
import org.eclipse.reddeer.swt.api.CTabFolder;
import org.eclipse.reddeer.swt.api.CTabItem;
import org.eclipse.reddeer.swt.widgets.AbstractControl;

/**
 * Abstract class for all {@link CTabFolder} implementations
 * 
 * @author Lucia Jelinkova
 *
 */
public abstract class AbstractCTabFolder extends AbstractControl<org.eclipse.swt.custom.CTabFolder> implements CTabFolder {

	protected AbstractCTabFolder(ReferencedComposite referencedComposite, int index, Matcher<?>... matchers) {
		super(org.eclipse.swt.custom.CTabFolder.class, referencedComposite, index, matchers);
	}
	
	protected AbstractCTabFolder(org.eclipse.swt.custom.CTabFolder swtWidget){
		super(swtWidget);
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.reddeer.swt.api.CTabFolder#getSelection()
	 */
	@Override
	public CTabItem getSelection() {
		org.eclipse.swt.custom.CTabItem selectedItem = CTabFolderHandler.getInstance().getSelection(swtWidget);
		if(selectedItem == null){
			return null;
		}
		return new DefaultCTabItem(selectedItem);
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.reddeer.swt.api.TabFolder#getTabItemLabels()
	 */
	@Override
	public String[] getTabItemLabels() {
		org.eclipse.swt.custom.CTabItem[] tabItem = CTabFolderHandler.getInstance().getTabItems(swtWidget);
		String[] tabItemLabel = new String[tabItem.length];
		for (int i = 0; i < tabItem.length; i++) {
			tabItemLabel[i] = ItemHandler.getInstance().getText(tabItem[i]);
		}
		return tabItemLabel;
	}

	@Override
	public Control getControl() {
		return swtWidget;
	}
}
