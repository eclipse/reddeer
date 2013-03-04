package org.jboss.reddeer.swt.lookup.impl;

import static org.eclipse.swtbot.swt.finder.finders.UIThreadRunnable.syncExec;

import org.eclipse.swtbot.swt.finder.results.Result;
import org.eclipse.ui.IViewReference;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchPartReference;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;

/**
 * Workbench lookup class. Provides access to active workbench 
 * or active view reference
 * 
 * @author jjankovi
 *
 */
public class WorkbenchLookup {

	/**
	 * Returns active workbench window
	 * @return active workbench window
	 */
	public static IWorkbenchWindow activeWorkbenchWindow() {
		return syncExec(new Result<IWorkbenchWindow>() {
			public IWorkbenchWindow run() {
				return PlatformUI.getWorkbench().getActiveWorkbenchWindow();
			}
		});
	}
	
	/**
	 * Returns active view reference from current active workbench window
	 * @return active view reference
	 */
	public static IViewReference findActiveView() {
		return syncExec(new Result<IViewReference>() {
			public IViewReference run() {
				return findActiveViewInternal();
			}
		});
	}

	private static IWorkbenchPage activePageInternal() {
		return activeWorkbenchWindow().getActivePage();
	}
	
	private static IViewReference findActiveViewInternal() {
		IWorkbenchPartReference partReference = activePageInternal().getActivePartReference();
		if (partReference instanceof IViewReference)
			return (IViewReference) partReference;
		return null;
	}
	
}