/*******************************************************************************
 * Copyright (c) 2017 Red Hat, Inc and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Red Hat Inc. - initial API and implementation
 *******************************************************************************/
package org.eclipse.reddeer.swt.test.impl.menu;

import org.eclipse.jface.layout.RowDataFactory;
import org.eclipse.jface.layout.RowLayoutFactory;
import org.eclipse.reddeer.common.util.Display;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MenuAdapter;
import org.eclipse.swt.events.MenuEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;
import org.junit.After;
import org.junit.Before;

public class AbstractMenuTest {
	
	public final String SHELL_TEXT="MenuShell";
	public Text text ;
	public Shell shell ;
	Tree tree;
	
	@Before
	public void createMenus() {
		Display.syncExec(new Runnable() {
			
			@Override
			public void run() {
				shell = new Shell();
				shell.setMenuBar(getShellMenuBar(shell));
				shell.setMenu(getShellContextMenu(shell));
				shell.setText(SHELL_TEXT);
				
				tree = new Tree(shell, SWT.SINGLE);
				
				ToolBar toolbar = new ToolBar(shell, SWT.FLAT);
				ToolItem toolItem = new ToolItem(toolbar, SWT.DROP_DOWN);
				toolItem.setToolTipText("toolItemMenu");
				
				ToolItem toolItem1 = new ToolItem(toolbar, SWT.PUSH);
				toolItem1.setToolTipText("genericToolItem");
				
				DropdownSelectionListener dropdownListener = new DropdownSelectionListener(toolItem);
				dropdownListener.add("ToolItemMenuItem1");
				dropdownListener.add("ToolItemMenuItem2");
				dropdownListener.add("ToolItemMenuItem3");
			    toolItem.addSelectionListener(dropdownListener);
			    
			    RowDataFactory.swtDefaults().applyTo(toolbar);
				
				
				
				TreeItem item = new TreeItem(tree, SWT.NONE);
				item.setText("TreeItem1");
				
				TreeItem item2 = new TreeItem(tree, SWT.NONE);
				item2.setText("TreeItem2");
				
				tree.setMenu(getTreeMenu(tree));
				
				text = new Text(shell, 0);
				text.setText("Test");
				text.setSize(100, 100);
				

				RowLayoutFactory.fillDefaults().applyTo(shell);
				RowDataFactory.swtDefaults().applyTo(tree);
				RowDataFactory.swtDefaults().applyTo(text);
				
				shell.layout();
				shell.open();
				
			}
		});
	}
	
	@After
	public void closeShell() {
		Display.syncExec(new Runnable() {
			
			@Override
			public void run() {
				if(shell != null && !shell.isDisposed()) {
					shell.close();
				}
			}
		});
	}
	
	
	
	public Menu getShellMenuBar(Shell shell) {
		Menu menu = new Menu(shell,SWT.BAR);
		MenuItem item = new MenuItem(menu, SWT.CASCADE);
		item.setText("ShellMenuBarItem");
		
		Menu menu2 = new Menu(shell, SWT.DROP_DOWN);
		item.setMenu(menu2);
		MenuItem item1 = new MenuItem(menu2, SWT.CASCADE);
		item1.setText("ShellMenuBarItem1");
		
		return menu;
	}
	
	public Menu getShellContextMenu(Shell shell) {
		Menu menu = new Menu(shell);
		MenuItem item = new MenuItem(menu, SWT.PUSH);
		item.setText("ShellContextMenuItem");
		return menu;
	}
	
	public Menu getTreeMenu(Tree tree) {
		Menu menu = new Menu(tree);
		MenuItem item1 = new MenuItem(menu, SWT.PUSH);
		addSelectionListener(item1);
		
		MenuItem item2= new MenuItem(menu, SWT.PUSH);
		addSelectionListener(item2);
		MenuItem item3= new MenuItem(menu, SWT.CASCADE);
		addSelectionListener(item3);
		
		Menu menu2 = new Menu(menu);
		item3.setMenu(menu2);
		
		MenuItem item4= new MenuItem(menu2, SWT.PUSH);
		addSelectionListener(item4);
		MenuItem item5= new MenuItem(menu2, SWT.PUSH);
		addSelectionListener(item5);
		
		MenuItem item6= new MenuItem(menu, SWT.CHECK);
		MenuItem item7= new MenuItem(menu, SWT.RADIO);
		menu.addMenuListener(new MenuAdapter() {
			
			@Override
			public void menuShown(MenuEvent e) {
				String menuText = tree.getSelection()[0].getText();
				item1.setText(menuText+"MenuItem1");
				item2.setText(menuText+"MenuItem2");
				item3.setText(menuText+"MenuItemWithMenu");
				item4.setText(menuText+"MenuItemWithMenuEnabledChild");
				item5.setText(menuText+"MenuItemWithMenuDisabledChild");
				item5.setEnabled(false);
				item6.setText(menuText+"MenuItemCheck");
				item7.setText(menuText+"MenuItemRadio");
				super.menuShown(e);
			}
		});
		return menu;
	}
	
	public void addSelectionListener(MenuItem item) {
		item.addSelectionListener(new SelectionAdapter() {
			
			@Override
			public void widgetSelected(SelectionEvent e) {
				text.setText("selected "+item.getText());
			}
		});
	}
	
	class DropdownSelectionListener extends SelectionAdapter {
		  private ToolItem dropdown;

		  private Menu menu;

		  public DropdownSelectionListener(ToolItem dropdown) {
		    this.dropdown = dropdown;
		    menu = new Menu(dropdown.getParent().getShell());
		  }

		  public void add(String item) {
		    MenuItem menuItem = new MenuItem(menu, SWT.NONE);
		    menuItem.setText(item);
		    menuItem.addSelectionListener(new SelectionAdapter() {
		      public void widgetSelected(SelectionEvent event) {
		        MenuItem selected = (MenuItem) event.widget;
		        dropdown.setText(selected.getText());
		      }
		    });
		  }

		  public void widgetSelected(SelectionEvent event) {
		    if (event.detail == SWT.ARROW) {
		      ToolItem item = (ToolItem) event.widget;
		      Rectangle rect = item.getBounds();
		      Point pt = item.getParent().toDisplay(new Point(rect.x, rect.y));
		      menu.setLocation(pt.x, pt.y + rect.height);
		      menu.setVisible(true);
		    }
		  }
		}

}
