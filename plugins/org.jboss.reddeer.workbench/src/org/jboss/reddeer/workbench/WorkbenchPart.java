package org.jboss.reddeer.workbench;

import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.actions.ActionFactory;
import org.hamcrest.Matcher;
import org.jboss.reddeer.junit.logging.Logger;
import org.jboss.reddeer.swt.condition.WaitCondition;
import org.jboss.reddeer.swt.exception.WaitTimeoutExpiredException;
import org.jboss.reddeer.swt.matcher.TextMatcher;
import org.jboss.reddeer.swt.wait.WaitUntil;
import org.jboss.reddeer.workbench.exception.WorkbenchPartNotFound;
import org.jboss.reddeer.workbench.handler.WorkbenchPartHandler;
import org.jboss.reddeer.workbench.lookup.WorkbenchPartLookup;

/**
 * Superclass of Editor and View with ability to be closed, minimized and
 * maximized
 * 
 * @author jjankovi
 * @author rhopp
 * @deprecated use org.jboss.reddeer.workbench.part.WorkbenchPart
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
		workbenchPart = WorkbenchPartLookup.getInstance().getActiveWorkbenchPart();
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
	 * Initialize part given title matcher
	 * 
	 * @param title title of part to initialize
	 */
	
	public WorkbenchPart(final Matcher<String> title) throws WorkbenchPartNotFound{
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
		return WorkbenchPartHandler.getInstance().getTitle(workbenchPart);
	}
	
	/**
	 * 
	 * @return the workbench part title tool tip (not {@code null})
	 */
	public String getTitleToolTip() {
		return WorkbenchPartHandler.getInstance().getTitleToolTip(workbenchPart);
	}

	/**
	 * Minimizes this workbench part (editor/view) 
	 * 
	 */	
	public abstract void minimize();

	/**
	 * Maximizes this workbench part (editor/view)
	 * 
	 */
	public abstract void maximize();

	abstract protected IWorkbenchPart getPartByTitle(Matcher<String> title);
	
	protected void performAction(final ActionFactory actionFactory){
		WorkbenchPartHandler.getInstance().performAction(actionFactory);
	}
	
	class PartIsFound implements WaitCondition{
		
		private String title;
		private Matcher<String> titleMatcher;
		private IWorkbenchPart part;
		
		public PartIsFound(String title){
			this.title = title;
		}

		public PartIsFound(Matcher<String> title){
			this.titleMatcher = title;
		}

		@Override
		public boolean test() {
			if(title != null){
				Matcher<String> titleM = new TextMatcher(title);
				part = getPartByTitle(titleM);
			} else {
				part = getPartByTitle(titleMatcher);
			}
			if(part == null){
				return false;
			}
			return true;
		}

		@Override
		public String description() {
			if(title != null){
				return "workbenchPart with title "+title+" is found";
			}
			return "workbenchPart with title "+titleMatcher+" is found";
		}
		
		public IWorkbenchPart getPart(){
			return part;
		}
		
	}
}
