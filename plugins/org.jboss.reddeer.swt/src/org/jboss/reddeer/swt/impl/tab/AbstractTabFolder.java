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
package org.jboss.reddeer.swt.impl.tab;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.eclipse.swt.widgets.TabItem;
import org.hamcrest.Matcher;
import org.jboss.reddeer.swt.api.TabFolder;
import org.jboss.reddeer.core.handler.ItemHandler;
import org.jboss.reddeer.core.handler.TabFolderHandler;
import org.jboss.reddeer.core.reference.ReferencedComposite;
import org.jboss.reddeer.swt.widgets.AbstractControl;

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
	 * @see org.jboss.reddeer.swt.api.TabFolder#getTabItemLabels()
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
	public List<org.jboss.reddeer.swt.api.TabItem> getSelection(){
		List<TabItem> items = TabFolderHandler.getInstance().getSelection(swtWidget);
		List<org.jboss.reddeer.swt.api.TabItem> rdItems = items.stream().map(t -> new DefaultTabItem(t)).collect(Collectors.toList());
		return rdItems;
	}
	
	@Override
	public List<org.jboss.reddeer.swt.api.TabItem> getItems(){
		List<TabItem> items = Arrays.asList(TabFolderHandler.getInstance().getTabItems(swtWidget));
		List<org.jboss.reddeer.swt.api.TabItem> rdItems = items.stream().map(t -> new DefaultTabItem(t)).collect(Collectors.toList());
		return rdItems;
	}
	
	
	
	
}
