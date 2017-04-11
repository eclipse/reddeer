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
package org.eclipse.reddeer.workbench.handler;

import java.util.Iterator;
import java.util.List;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Widget;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.IWorkbenchPartReference;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.actions.ActionFactory;
import org.eclipse.ui.actions.ActionFactory.IWorkbenchAction;
import org.eclipse.reddeer.common.exception.RedDeerException;
import org.eclipse.reddeer.common.logging.Logger;
import org.eclipse.reddeer.common.util.Display;
import org.eclipse.reddeer.common.util.ResultRunnable;
import org.eclipse.reddeer.core.handler.CTabItemHandler;
import org.eclipse.reddeer.core.handler.ControlHandler;
import org.eclipse.reddeer.core.lookup.WidgetLookup;
import org.eclipse.reddeer.core.util.TextWidgetUtil;
import org.eclipse.reddeer.workbench.core.lookup.WorkbenchPartLookup;
import org.eclipse.reddeer.workbench.exception.WorkbenchLayerException;

/**
 * WorkbenchPartHandler handles operations common for workbench part.
 * 
 * @author rawagner
 *
 */
public class WorkbenchPartHandler {
	
	protected final Logger log = Logger.getLogger(this.getClass());
	private static WorkbenchPartHandler instance;
	
	/**
	 * Gets instance of WorkbenchPartHandler.
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
	 * Gets title image of specified {@link IWorkbenchPart}.
	 *
	 * @param workbenchPart the workbench part
	 * @return title image of specified workbench part
	 */
	public Image getTitleImage(final IWorkbenchPart workbenchPart) {
		return Display.syncExec(new ResultRunnable<Image>() {

			@Override
			public Image run() {
				return workbenchPart.getTitleImage();
			}
		});
	}
	
	/**
	 * Calls set focus on specified workbench part. This should focus control inside workbench part
	 * @param workbenchPart to call focus on
	 */
	protected void setFocus(final IWorkbenchPart workbenchPart) {
		Display.syncExec(new Runnable() {
			
			@Override
			public void run() {
				workbenchPart.setFocus();
				
			}
		});
	}
	
	
	/**
	 * Focus child control.
	 */
	public void focusChildControl(IWorkbenchPart workbenchPart) {
		IWorkbenchPartReference workbenchPartReferece = getWorkbenchReference(workbenchPart);
		if(workbenchPartReferece != null){
			final Control workbenchControl = WorkbenchPartLookup.getInstance().getWorkbenchControl(workbenchPartReferece);
			log.debug("Active workbench control=" + 
					(workbenchControl == null ? "null" : getControlDesc(workbenchControl)));
			final Control focusedControl = WidgetLookup.getInstance().getFocusControl();
			log.debug("Focused control="
					+ (focusedControl == null ? "null" : getControlDesc(focusedControl)));
			if (hasControlSpecificParent(focusedControl, workbenchControl)) {
				return;
			}
			log.debug("No control in opened view has a focus!");
			log.debug("Setting implicit focus...");
			setFocusOnControlChild(workbenchControl);
		}
	}
	
	private IWorkbenchPartReference getWorkbenchReference(IWorkbenchPart workbenchPart) {
		return Display.syncExec(new ResultRunnable<IWorkbenchPartReference>() {

			@Override
			public IWorkbenchPartReference run() {
				return workbenchPart.getSite().getPage().getReference(workbenchPart);
			}
		});
	}
	
	private void setFocusOnControlChild(final Control workbenchControl) {
		Display.syncExec(new Runnable() {
			@Override
			public void run() {
				Control[] childrenControls= ((Composite) workbenchControl).getChildren();
				if (childrenControls.length > 0) {
					Control firstChildControl = childrenControls[0];
					firstChildControl.setFocus();
				} else {
					log.debug("View with title '" + workbenchControl.getToolTipText() + "' has "
							+ "no children!");
				}
			}
		});
	}
	
	private boolean hasControlSpecificParent(final Control focusedControl, final Control workbenchControl) {
		Control parent = Display.syncExec(new ResultRunnable<Control>() {
			@Override
			public Control run() {
				Control parent = focusedControl;
				while (parent != null && !parent.equals(workbenchControl) && !parent.isDisposed()) {
					parent = parent.getParent();
				}
				return parent; 
			}
		});
		return workbenchControl.equals(parent);
	}
	
	private String getControlDesc(Control control) {
		StringBuffer sbDesc = new StringBuffer("Class=");
		sbDesc.append(control.getClass().getName());
		sbDesc.append(" Text=");
		String value;
		try {
			value = TextWidgetUtil.getText(control);
		} catch (RedDeerException e) {
			value = "<unavailable>";
		}
		sbDesc.append(value);
		sbDesc.append(" TooltipText=");
		sbDesc.append(ControlHandler.getInstance().getToolTipText(control));
		
		return sbDesc.toString();
	}

	
    /**
     * Activates workbench part.
     * @param workbenchPart to activate
     */
    public void activate(final IWorkbenchPart workbenchPart) {
        if (!isActive(workbenchPart)) {
            log.debug("Activating workbench part " + getTitle(workbenchPart));
            Display.syncExec(new Runnable() {

                @Override
                public void run() {
                    PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().activate(workbenchPart);
                }
            });
        }
        setFocus(workbenchPart);
      	focusChildControl(workbenchPart);
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
	 * Finds out whether specified {@link IWorkbenchPart} is visible on active workbench window or not.
	 * 
	 * @param workbenchPart workbench part to handle
	 * @return true if specified workbench part is visible on active workbench window, false otherwise
	 */
	public boolean isWorkbenchPartVisible(final IWorkbenchPart workbenchPart) {
		return Display.syncExec(new ResultRunnable<Boolean>() {
			@Override
			public Boolean run() {
				return workbenchPart != null && PlatformUI.getWorkbench().getActiveWorkbenchWindow()
						.getActivePage().isPartVisible(workbenchPart);
			}
		});
				
	}
	
	/**
     * Checks if workbench part is active.
     * @param workbenchPart to be checked if it is active
     * @return true if workbench part is active, false otherwise
     */
    public boolean isActive(final IWorkbenchPart workbenchPart) {
        return Display.syncExec(new ResultRunnable<Boolean>() {
            @Override
            public Boolean run() {
                return workbenchPart == PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage()
                		.getActivePart();
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
			throw new WorkbenchLayerException("Unable to activate workbench part with widget. No cTabFolder found in widget path");
		}
	}
	
	/**
	 * Gets title of Workbench part containing specified widget.
	 *
	 * @param widget widget of workbench part to get title of
	 * @return the title of workbench part with widget
	 */
	public String getTitleOfWorkbenchPartWithWidget(Widget widget) {
		
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
			throw new WorkbenchLayerException("Unable to get title of workbench part with widget. No cTabFolder found in widget path");
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
		
		List<org.eclipse.swt.widgets.Widget> pathToWidget = WidgetLookup.getInstance().getPathToWidget(widget);
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
	
}