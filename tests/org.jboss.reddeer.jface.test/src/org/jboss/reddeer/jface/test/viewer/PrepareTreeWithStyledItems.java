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
package org.jboss.reddeer.jface.test.viewer;

import org.eclipse.jface.preference.JFacePreferences;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.StyledCellLabelProvider;
import org.eclipse.jface.viewers.StyledString;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerCell;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.jboss.reddeer.common.wait.WaitWhile;
import org.jboss.reddeer.core.condition.ShellWithTextIsAvailable;
import org.junit.After;
import org.junit.Before;

/**
 * 
 * SetUp tree with styled tree items. Structure of tree is following. Italics are styled texts:
 * 
 * 
 * @author mlabuda@redhat.com
 *
 */
public class PrepareTreeWithStyledItems {

	protected static String title = "Testing shell";
	
	@Before
	public void setUp() {
		org.jboss.reddeer.common.util.Display.syncExec(new Runnable() {
			@Override
			public void run() {
				JFaceResources.getColorRegistry().put(JFacePreferences.COUNTER_COLOR,
						new RGB(0, 127, 174));

				Shell shell = new Shell(Display.getDefault(), SWT.CLOSE | SWT.RESIZE);
				shell.setText(title);
				shell.setSize(400, 450);
				shell.setLayout(new GridLayout(1, true));
				
				Composite composite = createPartControl(shell);
				composite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1,
						1));
				
				shell.open();
			}
		});
	}

	@After
	public void cleanup() {
		org.jboss.reddeer.common.util.Display.syncExec(new Runnable() {
			@Override
			public void run() {
				for (Shell shell : org.jboss.reddeer.common.util.Display.getDisplay().getShells()) {
					if (shell.getText().equals(title)) {
						shell.dispose();
						break;
					}
				}
			}
		});
		new WaitWhile(new ShellWithTextIsAvailable(title));
	}
	
	
	// Creates 2 trees with styled tree items
	private Composite createPartControl(Composite parent) {
		Composite composite = new Composite(parent, SWT.NONE);

		composite.setLayout(new GridLayout(1, true));

		Label label = new Label(composite, SWT.NONE);
		label.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, false, false));
		label.setText("Tree with decorators");
		
		final TreeViewer treeViewer = new TreeViewer(composite, SWT.MULTI
				| SWT.H_SCROLL | SWT.V_SCROLL);
		
		StyledTreeItemLabelProvider labelProvider = new StyledTreeItemLabelProvider();
		TreeItemProvider treeItemProvider = new TreeItemProvider();
		
		treeViewer.getControl().setSize(400, 400);
		treeViewer.setLabelProvider(labelProvider);
		treeViewer.setContentProvider(treeItemProvider);

		GridData data = new GridData(GridData.CENTER, GridData.FILL, true, true);
		treeViewer.getControl().setLayoutData(data);
		treeViewer.setInput(new Object());
		
		composite.pack();
		
		return composite;
	}

	private static class StyledTreeItemLabelProvider extends StyledCellLabelProvider {

		public StyledTreeItemLabelProvider() {
		}

		@Override
		public void update(ViewerCell cell) {
			StyledString styledString = new StyledString();
			int digitSum = getDigitSumOfContent(cell);
			String cellContent = (String) cell.getElement();
			switch (digitSum) {
				case 0:
					appendText(styledString, cellContent);
					break;
				case 1:
					appendPrefix(styledString);
					appendText(styledString, cellContent);
					break;
				case 2:
					appendText(styledString, cellContent);
					appendPostfix(styledString);
					break;
				case 3:
					appendPrefix(styledString);
					appendText(styledString, cellContent);
					appendPostfix(styledString);
					break;
			}
			
			cell.setText(styledString.toString());
			cell.setStyleRanges(styledString.getStyleRanges());

			super.update(cell);
		}

		private StyledString appendPrefix(StyledString styledString) {
			styledString.append("prefix", StyledString.COUNTER_STYLER);
			styledString.append(" ", null);
			return styledString;
		}
		
		private StyledString appendPostfix(StyledString styledString) {
			styledString.append(" ", null);
			styledString.append("postfix", StyledString.COUNTER_STYLER);
			return styledString;
		}
		
		private StyledString appendText(StyledString styledString, String text) {
			styledString.append(text, null);
			return styledString;
		}

		@Override
		protected void measure(Event event, Object element) {
			super.measure(event, element);
		}
	}
	
	private static class TreeItemProvider implements ITreeContentProvider {

		@Override
		public Object[] getElements(Object element) {
			String[] items = new String[4];
			items[0] = "item 0";
			items[1] = "item 1";
			items[2] = "item x";
			items[3] = "item x";
			return items;
		}
		
		@Override
		public void dispose() {
		}

		@Override
		public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
		}

		@Override
		public Object[] getChildren(Object parentItem) {
			String parentString = (String) parentItem;
			if (!parentString.contains("x")) {
				return new String[] {parentItem + "0", parentItem + "1"};
			} else {
				return new String[] {parentItem + "x", parentItem + "x", parentItem + "y", parentItem + "y"};
			}
		}

		@Override
		public Object getParent(Object object) {
			String currentItem = (String) object;
			return currentItem.substring(0, currentItem.length() - 2);
		}

		@Override
		public boolean hasChildren(Object object) {
			return ((String) object).length() < 8;
		}
	}
	
	private static int getDigitSumOfContent(ViewerCell cell) {
		String cellText = (String) cell.getElement();
		String[] parts = cellText.split(" ");
		int digitSum = 0;
		if (parts.length > 1) {
			if (!parts[1].contains("x")) {
				int number = Integer.valueOf(parts[1]);
				digitSum = 0;
				while (number > 0) {
					digitSum += number % 10;
					number /= 10;
				}
			} else {
				digitSum = parts[1].length();
			}
		} else {
			return 0;
		}
		return digitSum;
	}
}
