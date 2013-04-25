package org.jboss.reddeer.workbench;

import org.apache.log4j.Logger;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.PlatformUI;
import org.jboss.reddeer.swt.util.Display;
import org.jboss.reddeer.swt.util.ResultRunnable;
import org.jboss.reddeer.workbench.exception.WorkbenchPartNotFound;

/**
 * Superclass of Editor and View with ability to be closed, minimized and
 * maximized
 * 
 * @author jjankovi
 * @author rhopp
 * 
 */
public abstract class WorkbenchPart {

	protected IWorkbenchPart workbenchPart;

	protected final Logger log = Logger.getLogger(this.getClass());

	/**
	 * Initialize to currently active part (either IEditorPart or IViewPart).
	 * Subclasses are supposed to override this constructor.
	 * 
	 * @throws WorkbenchPartNotFound
	 *             if there is no WorkbenchPart active
	 */
	public WorkbenchPart() throws WorkbenchPartNotFound {
		workbenchPart = getActiveWorkbenchPart();
		if (workbenchPart == null) {
			throw new WorkbenchPartNotFound(
					"There is no active WorkbenchPart on currently active Page");
		}
	}

	/**
	 * Initialize part given title
	 * 
	 * @param title title of part to initialize
	 */
	
	public WorkbenchPart(final String title) {
		workbenchPart = getPartByTitle(title);
	}

	abstract public void close();

	/**
	 * 
	 * @return title text
	 */
	
	public String getTitle() {
		return workbenchPart.getTitle();
	}

	public void minimize() {
		throw new UnsupportedOperationException("not implemented yet");
	}

	public void maximize() {
		throw new UnsupportedOperationException("not implemented yet");
	}

	abstract protected IWorkbenchPart getPartByTitle(String title);

	protected IWorkbenchPart getActiveWorkbenchPart() {
		return Display.syncExec(new ResultRunnable<IWorkbenchPart>() {

			@Override
			public IWorkbenchPart run() {
				return PlatformUI.getWorkbench().getActiveWorkbenchWindow()
						.getActivePage().getActivePart();
			}
		});
	}
}
