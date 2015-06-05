package org.jboss.reddeer.core.lookup;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.IViewReference;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.IWorkbenchPartReference;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.internal.WorkbenchPartReference;
import org.hamcrest.Matcher;
import org.jboss.reddeer.common.logging.Logger;
import org.jboss.reddeer.core.handler.WorkbenchPartHandler;
import org.jboss.reddeer.core.util.Display;
import org.jboss.reddeer.core.util.ResultRunnable;

/**
 * Workbench part lookup contains methods for looking up specific workbench part (mostly views).
 * 
 * @author rawagner
 *
 */
@SuppressWarnings("restriction")
public class WorkbenchPartLookup {

	private static final Logger log = Logger.getLogger(WorkbenchPartLookup.class);

	private static WorkbenchPartLookup instance;

	private WorkbenchPartLookup(){

	}

	/**
	 * Gets instance of WorkbenchPartLookup.
	 * 
	 * @return WorkbenchPartLookup instance
	 */
	public static WorkbenchPartLookup getInstance(){
		if(instance == null){
			instance = new WorkbenchPartLookup();
		}
		return instance;
	}

	/**
	 * Gets active workbench part.
	 * 
	 * @return active workbench part
	 */
	public IWorkbenchPart getActiveWorkbenchPart() {
		return WorkbenchPartHandler.getInstance().getActiveWorkbenchPart();
	}

	
	/**
	 * Gets active workbench part reference from current active workbench window.
	 * 
	 * @return active workbench part reference
	 */
	public IWorkbenchPartReference findActiveWorkbenchPartReference() {
		return WorkbenchPartHandler.getInstance().getActiveWorkbenchPartReference();
	}

	/**
	 * Gets all currently opened views as list of view parts. Includes also views on non-active tabs.
	 * 
	 * @return list of currently opened view parts
	 */
	public List<IViewPart> getOpenViews(){
		return Display.syncExec(new ResultRunnable<List<IViewPart>>() {

			@Override
			public List<IViewPart> run() {
				IWorkbenchPage activePage = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
				List<IViewPart> views = new ArrayList<IViewPart>();

				log.debug("Looking up all open views");
				for (IViewReference viewReference : activePage.getViewReferences()){
					IViewPart view = viewReference.getView(false);
					if (view == null){
						log.trace("\tView '" + viewReference.getTitle() + "' was not open or it cannot be restored");
						continue;
					}

					IViewPart[] stackedViews = activePage.getViewStack(view);
					if (stackedViews == null){
						log.trace("\tView '" + view.getTitle() + "' does not belong to the currently active page");
						continue;
					} 

					for (IViewPart part : viewReference.getPage().getViewStack(viewReference.getView(false))){
						log.trace("\tFound view '" + part.getTitle() + "'");
						views.add(part);
					}
				}
				return views;
			}
		});
	}
	
	/**
	 * Gets all view references. 
	 * 
	 * @return array of all view references
	 */
	public IViewReference[] findAllViewReferences() {
		return WorkbenchPartHandler.getInstance().getWorkbenchViewReferences();
	}

	/**
	 * Gets active view as a view reference.
	 * 
	 * @return active view
	 */
	public IViewReference getActiveView() {
		return Display.syncExec(new ResultRunnable<IViewReference>() {
		public IViewReference run() {
			IWorkbenchPartReference activeWorkbenchPart = findActiveWorkbenchPartReference();
			if (activeWorkbenchPart instanceof IViewReference) {
				return (IViewReference) activeWorkbenchPart;
			}
				return null;
			}
		});
	}
		
	
	/**
	 * Gets view as a view part with title matching specified matcher.
	 * 
	 * @param name matcher to match title
	 * @return view part matching specified matcher
	 */
	public IViewPart getViewByTitle(final Matcher<String> name){
		return Display.syncExec(new ResultRunnable<IViewPart>() {

			@Override
			public IViewPart run() {
				List<IViewPart> views = getOpenViews();

				for (IViewPart view : views) {
					if (name.matches(view.getViewSite().getRegisteredName())) {
						return view;
					}
				}

				logAllViews(views);
				return null;
			}

			private void logAllViews(List<IViewPart> views) {
				log.debug("View matching '" + name + "' not found");
				log.debug("List of found views:");
				for (IViewPart view : views) {
					log.debug("\t'" + view.getViewSite().getRegisteredName() + "'");
				}
			}
		});
	}
	
	/**
	 * Gets control object associated to active workbench.
	 * 
	 * @param activeWorkbenchReference active workbench reference
	 * @return workbench control associated to active workbench
	 */
	public Control getWorkbenchControl(
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
}
