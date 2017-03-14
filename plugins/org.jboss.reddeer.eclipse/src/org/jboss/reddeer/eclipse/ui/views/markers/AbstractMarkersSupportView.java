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
package org.jboss.reddeer.eclipse.ui.views.markers;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import org.jboss.reddeer.common.exception.RedDeerException;
import org.jboss.reddeer.common.wait.WaitWhile;
import org.jboss.reddeer.core.condition.ShellWithTextIsAvailable;
import org.jboss.reddeer.core.exception.CoreLayerException;
import org.jboss.reddeer.eclipse.exception.EclipseLayerException;
import org.jboss.reddeer.eclipse.ui.markers.AbstractMarker;
import org.jboss.reddeer.eclipse.ui.markers.matcher.AbstractMarkerMatcher;
import org.jboss.reddeer.jface.viewer.handler.TreeViewerHandler;
import org.jboss.reddeer.swt.api.Tree;
import org.jboss.reddeer.swt.api.TreeItem;
import org.jboss.reddeer.swt.impl.button.OkButton;
import org.jboss.reddeer.swt.impl.button.PushButton;
import org.jboss.reddeer.workbench.impl.menu.ViewMenu;
import org.jboss.reddeer.swt.impl.shell.DefaultShell;
import org.jboss.reddeer.swt.impl.table.DefaultTable;
import org.jboss.reddeer.swt.impl.tree.DefaultTree;
import org.jboss.reddeer.workbench.impl.view.WorkbenchView;

/**
 * Abstract view supporting markers. 
 * 
 * @author mlabuda@redhat.com
 *
 */
public class AbstractMarkersSupportView extends WorkbenchView {
	
	protected TreeViewerHandler treeViewerHandler = TreeViewerHandler.getInstance();
	
	public AbstractMarkersSupportView(String title) {
		super(title);
	}
	
	/**
	 * Gets columns in markers view.
	 * 
	 * @return list of columns in a tree of markers view
	 */
	public List<String> getProblemColumns() {
		activate();
		return getViewTree().getHeaderColumns();
	}
	
	/**
	 * Shows specified columns in a tree of markers view. 
	 * If one of columns is already shown, nothing happens for that one,
	 * others are shows as expected.
	 * 
	 * @param columns columns to show in a tree of markers view
	 */
	public void showProblemColumns(Column... columns) {
		String[] columnsToShow = getColumnsToShow(columns);
		if (columnsToShow.length > 0) {
			openConfigureColumnsShell();
			
			new DefaultTable(0).select(columnsToShow);
			new PushButton("Show ->").click();
			
			confirmChangesAndCloseConfigureColumnsShell();
		}
	}
	
	/**
	 * Hides specified column in a tree of the view.
	 * If one of columns is already hidden, nothing happens for that one,
	 * other are hidden as expected.
	 * 
	 * @param columns columns to hide in a tree of the view 
	 */
	public void hideProblemColumn(Column... columns) {
		String[] columnsToHide = getColumnsToHide(columns);
		if (columnsToHide.length > 0) {
			openConfigureColumnsShell();
			
			new DefaultTable(1).select(columnsToHide);
			new PushButton("<- Hide ").click();
			
			confirmChangesAndCloseConfigureColumnsShell();
		}
	}
	
	/**
	 * Shows default marker columns for a view supporting markers.
	 */
	public void showDefaultProblemColumns() {
		openConfigureColumnsShell();
		
		new PushButton("Restore Defaults").click();
		
		confirmChangesAndCloseConfigureColumnsShell();
	}
	
	private void openConfigureColumnsShell() {
		activate();
		new ViewMenu("View Menu", "Configure Columns...").select();
		
		new DefaultShell("Configure Columns");
	}
	
	private void confirmChangesAndCloseConfigureColumnsShell() {
		new OkButton().click();
		
		new WaitWhile(new ShellWithTextIsAvailable("Configure Columns"));
	}
	
	/**
	 * Gets index of a specific column in markers view.
	 * 
	 * @param column column to get index of
	 * @return index of a specified column
	 */
	public int getIndexOfColumn(String column) {
		return getColumnIndex(getViewTree().getHeaderColumns(), column);
	}
	
	/**
	 * Gets index of a specific column in markers view.
	 * 
	 * @param column column to get index of
	 * @return index of a specified column
	 */
	public int getIndexOfColumn(Column column) {
		return getColumnIndex(getViewTree().getHeaderColumns(), column.toString());
	}	
	
	private String[] getColumnsToHide(Column[] columns) {
		List<String> visibleColumns = getProblemColumns();
		List<String> columnsToHide = new ArrayList<String>();
		
		if (columns != null && columns.length > 0) {
			for (Column column: columns) {
				if (visibleColumns.contains(column.toString())) {
					columnsToHide.add(column.toString());
				}
			}
		}
		
		return columnsToHide.toArray(new String[columnsToHide.size()]);
	}
	
	private String[] getColumnsToShow(Column[] columns) {
		List<String> visibleColumns = getProblemColumns();
		List<String> columnsToShow = new ArrayList<String>();
		

		if (columns != null && columns.length > 0) {
			for (Column column: columns) {
				if (!visibleColumns.contains(column.toString())) {
					columnsToShow.add(column.toString());
				}
			}
		}
		
		return columnsToShow.toArray(new String[columnsToShow.size()]);
	}
	
	private int getColumnIndex(List<String> columns, String column) {
		int index = 0;
		while (index < columns.size()) {
			if (columns.get(index).equals(column)) {
				return index;
			}
			index++;
		}
		throw new EclipseLayerException("Specified column " + column + " is not presented in a tree of markers.");
	}
	
	/**
	 * Gets markers of any type matching specified matcher as a list of markers of specific class.
	 * 
	 * @param <T> type of marker
	 * @param clazz class of marker to get
	 * @param markerType type of a marker (Error, Warning, Java Problem etc.)
	 * @param matchers matchers to filter markers
	 * @return list of matching markers of specific type
	 */
	protected <T extends AbstractMarker> List<T> getMarkers(Class<T> clazz, String markerType, AbstractMarkerMatcher... matchers) {
		List<T> filteredResult = new ArrayList<T>();
		List<TreeItem> markerItems = getAllSpecificMarkerItems(markerType);
		if (markerItems != null) {
			for (TreeItem markerItem: markerItems) {
				if (matchMarkerTreeItem(markerItem, matchers)) {
					try {
						filteredResult.add(clazz.getConstructor(String.class, TreeItem.class).newInstance(
								markerType, markerItem));
					} catch (InstantiationException | IllegalAccessException | IllegalArgumentException
							| InvocationTargetException | NoSuchMethodException | SecurityException e) {
						// if something bad happen, print stack trace and throw RedDeer Exception 
						e.printStackTrace();
						throw new EclipseLayerException("Cannot create a new marker.");
					}
				}
			}
		}
		return filteredResult;
	}

	/** 
	 * Matches a specific marker with provided matchers. 
	 * @param item tree item of marker
	 * @param matchers matchers to match marker
	 * @return true if marker matches all provided matchers, false otherwise
	 */
	private boolean matchMarkerTreeItem(TreeItem item, AbstractMarkerMatcher... matchers) {
		boolean itemFitsMatchers = true;
		if(matchers != null){
			for (AbstractMarkerMatcher matcher: matchers) {
				try{
					if (!matcher.matches(item.getCell(getIndexOfColumn(matcher.getColumn())))) {
						itemFitsMatchers = false;
						break;
					}
				} catch (CoreLayerException ex){
					//if widget is disposed we can ignore it - problem disappeared
					if(!item.isDisposed()){
						throw ex;
					} else {
						itemFitsMatchers = false;
					}
				}
			}
		}
		return itemFitsMatchers;
	}
	
	/**
	 * Gets a list of tree items of a specific marker type. If such type is not listed, return null.
	 * @param markerType type of a marker
	 * @return list of markers of specific type or null if such type is not present (no such items)
	 */
	private List<TreeItem> getAllSpecificMarkerItems(String markerType) {
		// suffix of marker type can be '(XX items)' or 'XXX of XXX items) 
		String markerTypeSuffix = " \\(\\d+ .*\\)";
		activate();
		List<TreeItem> markerTypeItems = getViewTree().getItems();
		for (TreeItem item: markerTypeItems) {
			try {
				if (item.getText().matches(markerType + markerTypeSuffix)) {
					return item.getItems();
				}
			} catch (RedDeerException ex) {
				//if widget is disposed we can ignore it - problem disappeared
				if (!item.isDisposed()) {
					// if widget is not disposed, something else went wrong and we throw such exception
					throw ex;
				}
			}
		}
		// If there is no group of specific marker type, return null
		return null;
	}
	
	private Tree getViewTree(){
		return new DefaultTree(cTabItem);
	}
	
	/**
	 * Enum for columns of a tree in view supporting markers.
	 * 
	 * @author mlabuda@redhat.com
	 * @since 2.0
	 */
	public enum Column {
		
		DESCRIPTION("Description"),
		RESOURCE("Resource"),
		PATH("Path"),
		ID("ID"),
		LOCATION("Location"),
		TYPE("Type"),
		CREATION_TIME("Creation Time");
		
		private final String text;
		
		private Column(String text) {
			this.text = text;
		}
		
		public String toString() {
			return text;
		}
	}
}