package org.jboss.reddeer.core.handler;

import java.util.Iterator;
import java.util.List;

import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Widget;
import org.eclipse.ui.IViewReference;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.IWorkbenchPartReference;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.actions.ActionFactory;
import org.eclipse.ui.actions.ActionFactory.IWorkbenchAction;
import org.jboss.reddeer.common.logging.Logger;
import org.jboss.reddeer.core.handler.WidgetHandler;
import org.jboss.reddeer.core.lookup.WorkbenchPartLookup;
import org.jboss.reddeer.core.util.Display;
import org.jboss.reddeer.core.util.ResultRunnable;
import org.jboss.reddeer.core.exception.CoreLayerException;

/**
 * WorkbenchPart handler handles operations which are common for both editor and view instances.
 * 
 * @author rawagner
 *
 */
public class WorkbenchPartHandler {
	
	private static WorkbenchPartHandler instance;
	
	protected final Logger log = Logger.getLogger(this.getClass());
	
	private WorkbenchPartHandler(){
		
	}
	
	public static WorkbenchPartHandler getInstance(){
		if(instance == null){
			instance = new WorkbenchPartHandler();
		}
		return instance;
	}
	
	/**
	 * 
	 * @return title text
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
	 * 
	 * @return the workbench part title tool tip (not {@code null})
	 */
	public String getTitleToolTip(final IWorkbenchPart workbenchPart) {
		return Display.syncExec(new ResultRunnable<String>() {

			@Override
			public String run() {
				return workbenchPart.getTitleToolTip();
			}
		});
	}
	
	public IWorkbenchPart getActiveWorkbenchPart() {
		return Display.syncExec(new ResultRunnable<IWorkbenchPart>() {

			@Override
			public IWorkbenchPart run() {
				return PlatformUI.getWorkbench().getActiveWorkbenchWindow()
						.getActivePage().getActivePart();
			}
		});
	}
	
	public IWorkbenchPartReference getActiveWorkbenchPartReference() {
		return Display.syncExec(new ResultRunnable<IWorkbenchPartReference>() {

			@Override
			public IWorkbenchPartReference run() {
				return PlatformUI.getWorkbench().getActiveWorkbenchWindow()
						.getActivePage().getActivePartReference();
			}
		});
	}
	
	public IViewReference[] getWorkbenchViewReferences() {
		return Display.syncExec(new ResultRunnable<IViewReference[]>() {

			@Override
			public IViewReference[] run() {
				return PlatformUI.getWorkbench().getActiveWorkbenchWindow()
						.getActivePage().getViewReferences();
			}
		});
	}
	
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
	 * Activates Workbench part containing specified widget.
	 * 
	 * @param widget
	 */
	public void activateWorkbenchPartWithWidget (Widget widget) {
		
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
							if (workbenchPartWidgets.workbenchPartCTabFolder.getSelectionIndex() != index){
								log.debug("Activating Workbench part with label: " + cTabItems[index].getText());
								workbenchPartWidgets.workbenchPartCTabFolder.setSelection(index);	
							}
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
	 * @param widget
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
	 * Returns CTabFolder and CTabItem containing specified widget.
	 * 
	 * @param widget - widget contained within returned {@link WorkbenchPartWidgets}
	 * @return {@link WorkbenchPartWidgets}
	 */
	private WorkbenchPartWidgets getWorkbenchPartWidgetsForWidget (Widget widget){
		
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
	 * Closes all editors in active workbench.
	 */
	public void closeAllEditors() {
		Display.syncExec(new Runnable() {

			@Override
			public void run() {
				IWorkbench workbench = PlatformUI.getWorkbench();
				final IWorkbenchPage activePage = workbench
						.getActiveWorkbenchWindow().getActivePage();

				activePage.closeEditors(activePage.getEditorReferences(), true);
			}
		});
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
