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
package org.eclipse.reddeer.swt.impl.tab;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.eclipse.swt.widgets.TabItem;
import org.hamcrest.Matcher;
import org.eclipse.reddeer.swt.api.TabFolder;
import org.eclipse.reddeer.core.handler.ItemHandler;
import org.eclipse.reddeer.core.handler.TabFolderHandler;
import org.eclipse.reddeer.core.reference.ReferencedComposite;
import org.eclipse.reddeer.swt.widgets.AbstractControl;

/**
 * Abstract class for all TabFolder implementations
 * 
 * @author Andrej Podhradsky
 * 
 */
public abstract class AbstractTabFolder extends AbstractControl<org.eclipse.swt.widgets.TabFolder> implements TabFolder {

	protected AbstractTabFolder(ReferencedComposite refComposite, int index, Matcher<?>... matchers){
		super(org.eclipse.swt.widgets.TabFolder.class, refComposite, index, matchers);
	}
	
	protected AbstractTabFolder(org.eclipse.swt.widgets.TabFolder widget) {
		super(widget);
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.reddeer.swt.api.TabFolder#getTabItemLabels()
	 */
	@Override
	public String[] getTabItemLabels() {
		TabItem[] tabItem = TabFolderHandler.getInstance().getTabItems(swtWidget);
		String[] tabItemLabel = new String[tabItem.length];
		for (int i = 0; i < tabItem.length; i++) {
			tabItemLabel[i] = ItemHandler.getInstance().getText(tabItem[i]);
		}
		return tabItemLabel;
	}
	
	@Override
	public List<org.eclipse.reddeer.swt.api.TabItem> getSelection(){
		List<TabItem> items = TabFolderHandler.getInstance().getSelection(swtWidget);
		List<org.eclipse.reddeer.swt.api.TabItem> rdItems = items.stream().map(t -> new DefaultTabItem(t)).collect(Collectors.toList());
		return rdItems;
	}
	
	@Override
	public List<org.eclipse.reddeer.swt.api.TabItem> getItems(){
		List<TabItem> items = Arrays.asList(TabFolderHandler.getInstance().getTabItems(swtWidget));
		List<org.eclipse.reddeer.swt.api.TabItem> rdItems = items.stream().map(t -> new DefaultTabItem(t)).collect(Collectors.toList());
		return rdItems;
	}
	
	
	
	
}
