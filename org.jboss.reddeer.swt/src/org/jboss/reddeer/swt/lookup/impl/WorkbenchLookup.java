package org.jboss.reddeer.swt.lookup.impl;

import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.IEditorReference;
import org.eclipse.ui.IViewReference;
import org.eclipse.ui.IWorkbenchPartReference;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.internal.WorkbenchPartReference;
import org.jboss.reddeer.swt.util.Display;
import org.jboss.reddeer.swt.util.ResultRunnable;

/**
 * Workbench lookup class. Provides access to active workbench 
 * or active view reference
 * 
 * @author jjankovi
 *
 */
@SuppressWarnings("restriction")
public class WorkbenchLookup {

	/**
	 * Returns active workbench window
	 * @return active workbench window
	 */
	public static IWorkbenchWindow activeWorkbenchWindow() {
		return Display.syncExec(new ResultRunnable<IWorkbenchWindow>() {
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
		return Display.syncExec(new ResultRunnable<IWorkbenchPartReference>() {
			@Override
			public IWorkbenchPartReference run() {
				return activeWorkbenchWindow().getActivePage().getActivePartReference();
			}
		});
	}
	
	/**
	 * Returns all active views references 
	 * @return all active views references
	 */
	public static IViewReference[] findAllViews() {
		return Display.syncExec(new ResultRunnable<IViewReference[]>() {
			@Override
			public IViewReference[] run() {
				return activeWorkbenchWindow().getActivePage().getViewReferences();
			}
		});
	}
	
	/**
	 * Returns all active editor references
	 * @return all active editor references
	 */
	public static IEditorReference[] findAllEditors() {
		return Display.syncExec(new ResultRunnable<IEditorReference[]>() {
			@Override
			public IEditorReference[] run() {
				return activeWorkbenchWindow().getActivePage().getEditorReferences();
			}
		});
	}
	
	
	/**
	 * Returns active view reference from current active workbench window
	 * @return active view reference
	 */
	public static IViewReference findActiveView() {
		return Display.syncExec(new ResultRunnable<IViewReference>() {
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
		return Display.syncExec(new ResultRunnable<IEditorReference>() {
			public IEditorReference run() {
				return findActiveEditorInternal();
			}
		});
	}
	
	/**
	 * Return control object associated to active workbench
	 * @param activeWorkbenchReference
	 * @return
	 */
	public static Control getWorkbenchControl(
			final IWorkbenchPartReference activeWorkbenchReference) {
		return Display.syncExec(new ResultRunnable<Control>() {

			@Override
			public Control run() {
				return ((WorkbenchPartReference)activeWorkbenchReference)
						.getPane()
						.getControl();
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