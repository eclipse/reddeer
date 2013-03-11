package org.jboss.reddeer.swt.lookup.impl;

import static org.eclipse.swtbot.swt.finder.finders.UIThreadRunnable.syncExec;

import org.eclipse.swtbot.swt.finder.results.Result;
import org.eclipse.ui.IEditorReference;
import org.eclipse.ui.IViewReference;
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
	 * Returns active workbench part reference from current active workbench window
	 * @return active workbench part reference
	 */
	public static IWorkbenchPartReference findActiveWorkbenchPart() {
		return syncExec(new Result<IWorkbenchPartReference>() {
			@Override
			public IWorkbenchPartReference run() {
				return activeWorkbenchWindow().getActivePage().getActivePartReference();
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
	
	/**
	 * Returns active editor reference from current active workbench window
	 * @return active editor reference
	 */
	public static IEditorReference findActiveEditor() {
		return syncExec(new Result<IEditorReference>() {
			public IEditorReference run() {
				return findActiveEditorInternal();
			}
		});
	}

	private static IViewReference findActiveViewInternal() {
		IWorkbenchPartReference activeWorkbenchPart = findActiveWorkbenchPart(); 
		if (activeWorkbenchPart instanceof IViewReference) {
			return (IViewReference)activeWorkbenchPart;
		}
		return null;
	}
	
	private static IEditorReference findActiveEditorInternal() {
		IWorkbenchPartReference activeWorkbenchPart = findActiveWorkbenchPart(); 
		if (activeWorkbenchPart instanceof IEditorReference) {
			return (IEditorReference)activeWorkbenchPart;
		}
		return null;
	}
	
}