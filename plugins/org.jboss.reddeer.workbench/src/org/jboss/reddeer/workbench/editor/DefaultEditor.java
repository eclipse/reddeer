package org.jboss.reddeer.workbench.editor;

import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IEditorReference;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.jboss.reddeer.junit.logging.Logger;
import org.jboss.reddeer.swt.condition.ShellWithTextIsActive;
import org.jboss.reddeer.swt.impl.button.PushButton;
import org.jboss.reddeer.swt.impl.shell.DefaultShell;
import org.jboss.reddeer.swt.util.Display;
import org.jboss.reddeer.swt.util.ResultRunnable;
import org.jboss.reddeer.swt.wait.WaitWhile;
import org.jboss.reddeer.workbench.WorkbenchPart;
import org.jboss.reddeer.workbench.exception.WorkbenchPartNotFound;

/**
 * Represents general editor with basic operations implemented.
 * 
 * @author rhopp
 * 
 */

public class DefaultEditor extends WorkbenchPart implements Editor {

	protected final Logger log = Logger.getLogger(this.getClass());

	/**
	 * Initialize currently active editor
	 */

	public DefaultEditor() {
		super();
		if (!(workbenchPart instanceof IEditorPart)) {
			Display.syncExec(new Runnable() {

				@Override
				public void run() {
					workbenchPart = PlatformUI.getWorkbench()
							.getActiveWorkbenchWindow().getActivePage()
							.getActiveEditor();
				}
			});
		}
		if (workbenchPart == null) {
			throw new WorkbenchPartNotFound();
		}
		activate();
	}
	
	protected boolean needsOpenedView(){
		return true;
	}

	/**
	 * Initialize editor with given title and activate it.
	 * 
	 * @param title
	 *            Title of editor to initialize and activate
	 */

	public DefaultEditor(final String title) {
		super(title);
		if (!(workbenchPart instanceof IEditorPart)) {
			throw new WorkbenchPartNotFound();
		}
		activate();
	}

	/**
	 * Closes this editor
	 */

	@Override
	public void close() {
		close(false);
	}

	/**
	 * 
	 * @return true if editor is dirty
	 */

	public boolean isDirty() {
		return getEditorPart().isDirty();
	}

	/**
	 * Tries to perform save on this editor.
	 */

	public void save() {
		activate();
		log.info("Saving editor");
		Display.syncExec(new Runnable() {
			
			@Override
			public void run() {
				getEditorPart().doSave(new NullProgressMonitor());
			}
		});
	}

	/**
	 * Closes this editor. Allows to decide whether to save or discard changes.
	 * 
	 * @param save
	 *            If true, save is performed, otherwise changes are discarded
	 */

	public void close(final boolean save) {

		log.info("Closing editor " + getTitle());
		if (isDirty() && save){
			Display.asyncExec(new Runnable() {
				
				@Override
				public void run() {
					IWorkbenchWindow activeWorkbenchWindow = PlatformUI
							.getWorkbench().getActiveWorkbenchWindow();
					activeWorkbenchWindow.getActivePage().closeEditor(
							getEditorPart(), save);
				}
			});
			new DefaultShell("Save Resource");
			new PushButton("Yes").click();
			new WaitWhile(new ShellWithTextIsActive("Save Resource"));
		}else{
			Display.syncExec(new Runnable() {
	
				@Override
				public void run() {
					IWorkbenchWindow activeWorkbenchWindow = PlatformUI
							.getWorkbench().getActiveWorkbenchWindow();
					activeWorkbenchWindow.getActivePage().closeEditor(
							getEditorPart(), save);
				}
			});
		}
		log.info("Editor " + getTitle() + " is closed");
	}

	/**
	 * 
	 * @return whether is this editor currently active and has focus.
	 */
	
	@Override
	public boolean isActive() {
		return Display.syncExec(new ResultRunnable<Boolean>() {
			@Override
			public Boolean run() {
				return (getEditorPart() == PlatformUI.getWorkbench()
						.getActiveWorkbenchWindow().getActivePage()
						.getActivePart());
			}
		});
	}

	protected void activate() {
		if (!isActive()) {
			log.info("Activating editor " + getTitle());
			Display.syncExec(new Runnable() {

				@Override
				public void run() {
					PlatformUI.getWorkbench().getActiveWorkbenchWindow()
							.getActivePage().activate(getEditorPart());
					getEditorPart().setFocus();
				}
			});
		}
	}

	@Override
	protected IWorkbenchPart getPartByTitle(final String title) {
		return Display.syncExec(new ResultRunnable<IEditorPart>() {

			@Override
			public IEditorPart run() {
				IWorkbenchWindow activeWorkbenchWindow = PlatformUI
						.getWorkbench().getActiveWorkbenchWindow();
				IEditorReference[] editors = activeWorkbenchWindow
						.getActivePage().getEditorReferences();
				for (IEditorReference iEditorReference : editors) {
					if (iEditorReference.getEditor(false).getEditorInput()
							.getName().equals(title)) {
						return iEditorReference.getEditor(false);
					}
				}
				return null;
			}
		});
	}

	protected IEditorPart getEditorPart() {
		if (workbenchPart == null) {
			throw new RuntimeException("workbenchPart is null");
		}
		if (!(workbenchPart instanceof IEditorPart)) {
			throw new RuntimeException(
					"workbenchPart isn't instance of IEditorPart");
		}
		return (IEditorPart) workbenchPart;
	}

}
