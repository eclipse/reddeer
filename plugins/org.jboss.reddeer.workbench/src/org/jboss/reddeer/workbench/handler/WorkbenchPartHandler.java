package org.jboss.reddeer.workbench.handler;

import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.actions.ActionFactory;
import org.eclipse.ui.actions.ActionFactory.IWorkbenchAction;
import org.jboss.reddeer.swt.util.Display;
import org.jboss.reddeer.swt.util.ResultRunnable;

/**
 * WorkbenchPart handler handles operations which are common for both editor and view instances
 * @author rawagner
 *
 */
public class WorkbenchPartHandler {
	
	private static WorkbenchPartHandler instance;
	
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
	
	public void performAction(final ActionFactory actionFactory){
		Display.syncExec(new Runnable() {
			
			@Override
			public void run() {
				IWorkbenchAction action= actionFactory.create(PlatformUI.getWorkbench().getActiveWorkbenchWindow());
				action.run();
			}
		});
	}
	
	

}
