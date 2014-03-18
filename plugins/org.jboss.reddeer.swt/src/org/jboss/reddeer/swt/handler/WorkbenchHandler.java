package org.jboss.reddeer.swt.handler;

import org.eclipse.ui.IViewReference;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PlatformUI;
import org.jboss.reddeer.swt.lookup.WorkbenchLookup;
import org.jboss.reddeer.swt.util.Display;
import org.jboss.reddeer.swt.util.ResultRunnable;

/**
 * Contains methods that handle UI operations on {@link IWorkbench}. 
 * @author Jiri Peterka
 *
 */
public class WorkbenchHandler {
	
	private static WorkbenchHandler instance;
	
	private WorkbenchHandler() {}
	
	/**
	 * Returns workbench handler instance
	 * @return workbench handler class instance
	 */
	public static WorkbenchHandler getInstance() {
		if (instance == null) instance = new WorkbenchHandler();
		return instance;
	}

	/**
	 * Closes all editor in Active Workbench
	 */
	public void closeAllEditors() {
		Display.syncExec(new Runnable() {

			@Override
			public void run() {
				IWorkbench workbench = PlatformUI.getWorkbench();
				final IWorkbenchPage activePage = workbench.getActiveWorkbenchWindow().getActivePage();

				activePage.closeEditors(activePage.getEditorReferences(), true);		
			}			
		});
	}
	
	
	/**
	 * Get Active view title
	 */
	public String getActiveViewTitle() {
		
		final IViewReference findActiveView = WorkbenchLookup.findActiveView();
		String title = Display.syncExec(new ResultRunnable<String>() {

			@Override
			public String run() {
				return findActiveView.getTitle();
			}
		});
	
		return title;
	}
	
}
