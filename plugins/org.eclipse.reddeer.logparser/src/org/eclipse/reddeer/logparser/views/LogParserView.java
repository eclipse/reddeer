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
package org.eclipse.reddeer.logparser.views;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.part.*;
import org.eclipse.reddeer.logparser.LogParserActivator;
import org.eclipse.reddeer.logparser.LogParserLog;
import org.eclipse.reddeer.logparser.dialogs.LogParserPropertiesDialog;
import org.eclipse.reddeer.logparser.editors.LogParserEditor;
import org.eclipse.reddeer.logparser.model.LogData;
import org.eclipse.reddeer.logparser.model.LogParserDataModel;
import org.eclipse.reddeer.logparser.preferences.LogParserPreferencesPageModel;
import org.eclipse.jface.viewers.*;
import org.eclipse.swt.graphics.Image;

import java.io.File;
import java.util.ArrayList;

import org.eclipse.core.filesystem.EFS;
import org.eclipse.core.filesystem.IFileStore;
import org.eclipse.core.runtime.Assert;
import org.eclipse.jface.action.*;
import org.eclipse.jface.dialogs.InputDialog;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.util.IPropertyChangeListener;
import org.eclipse.jface.util.PropertyChangeEvent;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.ide.FileStoreEditorInput;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.SWT;

public class LogParserView extends ViewPart implements IPropertyChangeListener{

	/**
	 * The ID of the view as specified by the extension.
	 */
	public static final String ID = "org.eclipse.reddeer.logparser.views.LogParserView";

	private TableViewer logsViewer;
	private Action actionAdd;
	private Action actionModify;
	private Action actionRemove;
	private Action actionParse;
	private static Image LOG_PARSER_ICON;
	private ArrayList<LogData> currentLogs = null;

	class LogParserViewLabelProvider extends LabelProvider implements ITableLabelProvider {
		public String getColumnText(Object obj, int index) {
			return ((LogData) obj).getLocation();
		}

		public Image getColumnImage(Object obj, int index) {
			return getImage(obj);
		}

		public Image getImage(Object obj) {
			return LOG_PARSER_ICON;
		}
	}

	class LogLocationSorter extends ViewerSorter {
		public int compare(Viewer viewer, Object e1, Object e2) {
			return ((LogData) e1).getLocation().compareToIgnoreCase(((LogData) e2).getLocation());
		}
	}
	
	public LogParserView(){
		super();
		LogParserActivator.addPropertyChangeListener(this);
	}

	/**
	 * This is a callback that will allow us to create the viewer and initialize
	 * it.
	 */
	public void createPartControl(Composite parent) {
		if (LogParserView.LOG_PARSER_ICON == null) {
			LogParserView.LOG_PARSER_ICON = LogParserActivator.getImageDescriptor("icons/logparser.gif").createImage();
		}
		logsViewer = new TableViewer(parent, SWT.SINGLE | SWT.H_SCROLL | SWT.V_SCROLL);
		logsViewer.setContentProvider(new ArrayContentProvider());
		logsViewer.setLabelProvider(new LogParserViewLabelProvider());
		logsViewer.setSorter(new LogLocationSorter());
		currentLogs = LogParserDataModel.getLogParserData();
		logsViewer.setInput(currentLogs);
		getSite().setSelectionProvider(logsViewer);
		makeActions();
		enableActions();
		hookContextMenu();
		contributeToActionBars();
		logsViewer.addSelectionChangedListener(new ISelectionChangedListener() {
			@Override
			public void selectionChanged(SelectionChangedEvent event) {
				enableActions();
			}
		});

		logsViewer.addDoubleClickListener(new IDoubleClickListener() {
			@Override
			public void doubleClick(DoubleClickEvent event) {
				actionParse.run();
			}
		});
	}

	private void hookContextMenu() {
		MenuManager menuMgr = new MenuManager("#PopupMenu");
		menuMgr.setRemoveAllWhenShown(true);
		menuMgr.addMenuListener(new IMenuListener() {
			public void menuAboutToShow(IMenuManager manager) {
				LogParserView.this.fillContextMenu(manager);
			}
		});
		Menu menu = menuMgr.createContextMenu(logsViewer.getControl());
		logsViewer.getControl().setMenu(menu);
		getSite().registerContextMenu(menuMgr, logsViewer);
	}

	private void contributeToActionBars() {
		IActionBars bars = getViewSite().getActionBars();
		fillLocalToolBar(bars.getToolBarManager());
	}

	private void fillContextMenu(IMenuManager manager) {
		manager.add(actionParse);
		manager.add(new Separator());
		manager.add(actionAdd);
		manager.add(actionModify);
		manager.add(actionRemove);
	}

	private void fillLocalToolBar(IToolBarManager manager) {
		manager.add(actionParse);
		manager.add(new Separator());
		manager.add(actionAdd);
		manager.add(actionModify);
		manager.add(actionRemove);
	}

	private void makeActions() {

		actionParse = new Action() {
			public void run() {
				parseLog();
			}
		};
		actionParse.setText("Parse Log");
		actionParse.setToolTipText("Parse Log");
		actionParse.setImageDescriptor(LogParserActivator.getImageDescriptor("icons/parse.gif"));

		actionAdd = new Action() {
			public void run() {
				addLog();
			}
		};
		actionAdd.setText("Add Log");
		actionAdd.setToolTipText("Add Log");
		actionAdd.setImageDescriptor(LogParserActivator.getImageDescriptor("icons/addlog.gif"));

		actionModify = new Action() {
			public void run() {
				modifyLog();
			}
		};
		actionModify.setText("Modify Log rules");
		actionModify.setToolTipText("Modify parsing rules for Log");
		actionModify.setImageDescriptor(LogParserActivator.getImageDescriptor("icons/modifylog.gif"));

		actionRemove = new Action() {
			public void run() {
				removeLog();
			}
		};
		actionRemove.setText("Remove Log");
		actionRemove.setToolTipText("Remove Log");
		actionRemove.setImageDescriptor(LogParserActivator.getImageDescriptor("icons/removelog.gif"));
	}

	private void enableActions() {
		if (logsViewer.getSelection().isEmpty()) {
			actionParse.setEnabled(false);
			actionModify.setEnabled(false);
			actionRemove.setEnabled(false);
		} else {
			actionParse.setEnabled(true);
			actionModify.setEnabled(true);
			actionRemove.setEnabled(true);
		}
	}

	private void addLog() {
		LogParserPropertiesDialog logParserPropertiesDialog = new LogParserPropertiesDialog(
				logsViewer.getControl().getShell(), new LogData(),
				LogParserPreferencesPageModel.getParseRules(),
				this.currentLogs);
		if (logParserPropertiesDialog.open() == InputDialog.OK) {
			LogData newLogData = logParserPropertiesDialog.getLogData();
			logsViewer.add(newLogData);
			currentLogs.add(newLogData);
			logsViewer.setSelection(new StructuredSelection(newLogData));
		}
		LogParserDataModel.saveLogParserData(currentLogs);
	}

	private void modifyLog() {
		LogData updLogData = ((LogData) logsViewer.getStructuredSelection().getFirstElement()).clone();
		LogData updModelLogData = currentLogs.get(currentLogs.indexOf(updLogData));
		LogParserPropertiesDialog logParserPropertiesDialog = new LogParserPropertiesDialog(logsViewer.getControl().getShell(),
				updLogData,
				LogParserPreferencesPageModel.getParseRules(),
				this.currentLogs);
		if (logParserPropertiesDialog.open() == InputDialog.OK) {
			LogData.copyFields(logParserPropertiesDialog.getLogData(), updModelLogData);
			logsViewer.setInput(currentLogs);
			logsViewer.setSelection(new StructuredSelection(updModelLogData));
		}
		LogParserDataModel.saveLogParserData(currentLogs);
	}

	private void removeLog() {
		LogData removeLogData = ((LogData) logsViewer.getStructuredSelection().getFirstElement());
		logsViewer.remove(removeLogData);
		currentLogs.remove(removeLogData);
		LogParserDataModel.saveLogParserData(currentLogs);
	}

	private void parseLog() {
		LogData logData = ((LogData) logsViewer.getStructuredSelection().getFirstElement());
		openLogParserEditor (logData);
	}
	
	private void openLogParserEditor(LogData logData){
		File fileToOpen = new File(logData.getLocation());
		Assert.isNotNull(fileToOpen);
		if (fileToOpen.exists() && fileToOpen.isFile()) {
		    IFileStore fileStore = EFS.getLocalFileSystem().getStore(fileToOpen.toURI());
			IWorkbenchPage page = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
		    try {
		    	int numOpenedEditors = page.getEditorReferences().length;
		        IEditorPart editorPart = page.openEditor( new FileStoreEditorInput(fileStore),
		        	LogParserEditor.ID);
		        // Editor was already opened needs to refresh content
		        LogParserEditor logParserEditor = (LogParserEditor)editorPart;
		        if (page.getEditorReferences().length == numOpenedEditors){
		        	logParserEditor.refreshLog();
		        }
	        	logParserEditor.parseLog(logData);
		    } catch ( PartInitException pie ) {
		        LogParserLog.logError("Unable to open log in editor" , pie);
		    }
		}
		else {
			MessageDialog.openError(logsViewer.getControl().getShell(),
			    	"Error",
			    	"Log file: " + fileToOpen.getAbsolutePath() + " doesn't exist.");
		}			
	}
	/**
	 * Passing the focus request to the viewer's control.
	 */
	public void setFocus() {
		logsViewer.getControl().setFocus();
	}

	public static void disposeImages() {
		if (LogParserView.LOG_PARSER_ICON != null) {
			LogParserView.LOG_PARSER_ICON.dispose();
			LogParserView.LOG_PARSER_ICON = null;
		}
	}
	@Override
	public void propertyChange(PropertyChangeEvent event) {
		IStructuredSelection logsSelection = logsViewer.getStructuredSelection();
		currentLogs = LogParserDataModel.getLogParserData();
		logsViewer.setInput(currentLogs);
		logsViewer.setSelection(logsSelection);
	}

}
