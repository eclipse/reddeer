package org.jboss.reddeer.workbench;

import org.jboss.reddeer.junit.logging.Logger;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.PlatformUI;
import org.jboss.reddeer.swt.condition.WaitCondition;
import org.jboss.reddeer.swt.exception.WaitTimeoutExpiredException;
import org.jboss.reddeer.swt.util.Display;
import org.jboss.reddeer.swt.util.ResultRunnable;
import org.jboss.reddeer.swt.wait.WaitUntil;
import org.jboss.reddeer.workbench.exception.WorkbenchPartNotFound;

/**
 * Superclass of Editor and View with ability to be closed, minimized and
 * maximized
 * 
 * @author jjankovi
 * @author rhopp
 * 
 */
public abstract class WorkbenchPart{

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
	
	public WorkbenchPart(final String title) throws WorkbenchPartNotFound{
		PartIsFound found = new PartIsFound(title);
		try{
			new WaitUntil(found);
		} catch (WaitTimeoutExpiredException ex){
			if(needsOpenedView()){
				throw new WorkbenchPartNotFound("Unable to find Workbench part with title "+title);
			}
		}
		workbenchPart = found.getPart();
	}
	
	/**
	 * Declares whether particular View implementation requires view 
	 * to be opened (returns true ) or is able to open View by itself (return false)
	 * @return false if opened View is needed, true otherwise
	 */
	abstract protected boolean needsOpenedView();

	abstract public void close();

	/**
	 * 
	 * @return title text
	 */
	
	public String getTitle() {
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
	public String getTitleToolTip() {
		return Display.syncExec(new ResultRunnable<String>() {

			@Override
			public String run() {
				return workbenchPart.getTitleToolTip();
			}
		});
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
	
	class PartIsFound implements WaitCondition{
		
		private String title;
		private IWorkbenchPart part;
		
		public PartIsFound(String title){
			this.title = title;
		}

		@Override
		public boolean test() {
			part = getPartByTitle(title);
			if(part == null){
				return false;
			}
			return true;
		}

		@Override
		public String description() {
			return "WorkbenchPart with title "+title+" is found.";
		}
		
		public IWorkbenchPart getPart(){
			return part;
		}
		
	}
	
}
