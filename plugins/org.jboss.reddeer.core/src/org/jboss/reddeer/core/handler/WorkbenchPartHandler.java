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
package org.jboss.reddeer.core.handler;

import java.util.Iterator;
import java.util.List;

import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Widget;
import org.eclipse.ui.IViewReference;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.IWorkbenchPartReference;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.actions.ActionFactory;
import org.eclipse.ui.actions.ActionFactory.IWorkbenchAction;
import org.jboss.reddeer.common.logging.Logger;
import org.jboss.reddeer.core.exception.CoreLayerException;
import org.jboss.reddeer.core.lookup.WorkbenchPartLookup;
import org.jboss.reddeer.core.util.Display;
import org.jboss.reddeer.core.util.ResultRunnable;

/**
 * WorkbenchPartHandler handles operations common for both editor and view instances.
 * 
 * @author rawagner
 *
 */
public class WorkbenchPartHandler {
	
	private static WorkbenchPartHandler instance;
	
	protected final Logger log = Logger.getLogger(this.getClass());
	
	private WorkbenchPartHandler(){
		
	}
	
	/**
	 * Gets WorkbenchPartHandler instance.
	 *  
	 * @return instance of WorkbenchPartHandler
	 */
	public static WorkbenchPartHandler getInstance(){
		if(instance == null){
			instance = new WorkbenchPartHandler();
		}
		return instance;
	}
	
	/**
	 * Gets title of specified {@link IWorkbenchPart}.
	 *
	 * @param workbenchPart the workbench part
	 * @return title of specified workbench part
	 */
	public String getTitle(final IWorkbenchPart workbenchPart) {
		return Display.syncExec(new ResultRunnable<String>() {

			@Override
			public String run() {
				return workbenchPart.getTitle();
			}
		});
	}
	
	/**
	 * Gets title tool tip of specified {@link IWorkbenchPart}.
	 *
	 * @param workbenchPart the workbench part
	 * @return title tool tip text of specified workbench part
	 */
	public String getTitleToolTip(final IWorkbenchPart workbenchPart) {
		return Display.syncExec(new ResultRunnable<String>() {

			@Override
			public String run() {
				return workbenchPart.getTitleToolTip();
			}
		});
	}
	
	/**
	 * Gets active {@link IWorkbenchPart}.
	 *  
	 * @return active workbench part
	 */
	public IWorkbenchPart getActiveWorkbenchPart() {
		return Display.syncExec(new ResultRunnable<IWorkbenchPart>() {

			@Override
			public IWorkbenchPart run() {
				return PlatformUI.getWorkbench().getActiveWorkbenchWindow()
						.getActivePage().getActivePart();
			}
		});
	}
	
	/**
	 * Gets active {@link IWorkbenchPartReference}.
	 * 
	 * @return active workbench part reference
	 */
	public IWorkbenchPartReference getActiveWorkbenchPartReference() {
		return Display.syncExec(new ResultRunnable<IWorkbenchPartReference>() {

			@Override
			public IWorkbenchPartReference run() {
				return PlatformUI.getWorkbench().getActiveWorkbenchWindow()
						.getActivePage().getActivePartReference();
			}
		});
	}
	
	/**
	 * Gets workbench {@link IViewReference}s.
	 * 
	 * @return array of workbench view reference
	 */
	public IViewReference[] getWorkbenchViewReferences() {
		return Display.syncExec(new ResultRunnable<IViewReference[]>() {

			@Override
			public IViewReference[] run() {
				return PlatformUI.getWorkbench().getActiveWorkbenchWindow()
						.getActivePage().getViewReferences();
			}
		});
	}
	
	/**
	 * Perform action created from specified {@link ActionFactory}.
	 * 
	 * @param actionFactory action factory to create action to perform
	 */
	public void performAction(final ActionFactory actionFactory){
		Display.syncExec(new Runnable() {
			
			@Override
			public void run() {
				IWorkbenchAction action= actionFactory.create(PlatformUI.getWorkbench().getActiveWorkbenchWindow());
				action.run();
			}
		});
	}
	
	/**
	 * Activates workbench part containing specified widget.
	 * 
	 * @param widget widget of workbench part to activate
	 */
	public void activateWorkbenchPartWithWidget(Widget widget) {
		
		final WorkbenchPartWidgets workbenchPartWidgets = getWorkbenchPartWidgetsForWidget(widget);
		
		if (workbenchPartWidgets != null){
			Display.syncExec(new Runnable() {			
				@Override
				public void run() {
					CTabItem[] cTabItems = workbenchPartWidgets.workbenchPartCTabFolder.getItems();
					int index = 0;
					boolean cTabItemFound = false;
					while (!cTabItemFound && index < cTabItems.length){
						if (cTabItems[index].getControl() == workbenchPartWidgets.firstCTabItemControl){
							cTabItemFound = true;
							log.debug("Activating Workbench part with label: '" + cTabItems[index].getText() + "'");
							CTabItemHandler.getInstance().activate(cTabItems[index]);
						}		
						else{
							index++;
						}
					}
				}
			});
		}
		else{
			throw new CoreLayerException("Unable to activate workbench part with widget. No cTabFolder found in widget path");
		}
	}
	
	/**
	 * Gets title of Workbench part containing specified widget.
	 *
	 * @param widget widget of workbench part to get title of
	 * @return the title of workbench part with widget
	 */
	public String getTitleOfWorkbenchPartWithWidget (Widget widget) {
		
		final WorkbenchPartWidgets workbenchPartWidgets = getWorkbenchPartWidgetsForWidget(widget);
		
		if (workbenchPartWidgets != null){
			return Display.syncExec(new ResultRunnable<String>() {
				@Override
				public String run() {
					CTabItem[] cTabItems = workbenchPartWidgets.workbenchPartCTabFolder.getItems();
					int index = 0;
					String cTabItemTitle = null;
					while (cTabItemTitle == null && index < cTabItems.length){
						if (cTabItems[index].getControl() == workbenchPartWidgets.firstCTabItemControl){
							cTabItemTitle = cTabItems[index].getText();
						}		
						else{
							index++;
						}
					}
					return cTabItemTitle;
				}
			});
		}
		else{
			throw new CoreLayerException("Unable to get title of workbench part with widget. No cTabFolder found in widget path");
		}
	}
	
	/**
	 * Stores information about top workbench part widgets.
	 * 
	 * @author vlado pakan
	 */
	private class WorkbenchPartWidgets {
		
		public WorkbenchPartWidgets(CTabFolder workbenchPartCTabFolder,
				Control firstCTabItemControl) {
			super();
			this.workbenchPartCTabFolder = workbenchPartCTabFolder;
			this.firstCTabItemControl = firstCTabItemControl;
		}
		
		public CTabFolder workbenchPartCTabFolder = null;
		public Control firstCTabItemControl = null;

	}
	
	/**
	 * Gets CTabFolder and CTabItem containing specified widget.
	 * 
	 * @param widget widget contained within returned {@link WorkbenchPartWidgets}
	 * @return {@link WorkbenchPartWidgets}
	 */
	private WorkbenchPartWidgets getWorkbenchPartWidgetsForWidget(Widget widget){
		
		WorkbenchPartWidgets workbenchPartWidgets = null;
		
		List<org.eclipse.swt.widgets.Widget> pathToWidget = WidgetHandler.getInstance().getPathToWidget(widget);
		Iterator<org.eclipse.swt.widgets.Widget> itWidget = pathToWidget.iterator();
		boolean foundCTabFolder = false;
		org.eclipse.swt.widgets.Widget currentWidget = null;
		while (!foundCTabFolder && itWidget.hasNext()){
			currentWidget = itWidget.next();
			if (currentWidget instanceof org.eclipse.swt.custom.CTabFolder){
				foundCTabFolder = true;
			}
		}
		
		if (foundCTabFolder){
			workbenchPartWidgets = new WorkbenchPartWidgets((CTabFolder)currentWidget,
				(Control)itWidget.next());
		}
		
		return workbenchPartWidgets;
	}

	/**
	 * Gets title of active view.
	 * 
	 * @return title of active view
	 */
	public String getActiveViewTitle() {

		final IViewReference findActiveView = WorkbenchPartLookup.getInstance().getActiveView();
		String title = Display.syncExec(new ResultRunnable<String>() {

			@Override
			public String run() {
				return findActiveView.getTitle();
			}
		});

		return title;
	}
}
