package org.jboss.reddeer.workbench.lookup;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IEditorReference;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.IViewReference;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.hamcrest.Matcher;
import org.jboss.reddeer.common.logging.Logger;
import org.jboss.reddeer.swt.util.Display;
import org.jboss.reddeer.swt.util.ResultRunnable;
import org.jboss.reddeer.workbench.exception.WorkbenchPartNotFound;
import org.jboss.reddeer.workbench.handler.WorkbenchPartHandler;
import org.jboss.reddeer.workbench.matcher.EditorPartTitleMatcher;

/**
 * WorkbenchPart lookup containing lookup routines for Workbench parts such as editor and view
 * @author rawagner
 *
 */
public class WorkbenchPartLookup {

	private static final Logger log = Logger.getLogger(WorkbenchPartLookup.class);

	private static WorkbenchPartLookup instance;

	private WorkbenchPartLookup(){

	}

	public static WorkbenchPartLookup getInstance(){
		if(instance == null){
			instance = new WorkbenchPartLookup();
		}
		return instance;
	}

	public IWorkbenchPart getActiveWorkbenchPart() {
		return WorkbenchPartHandler.getInstance().getActiveWorkbenchPart();
	}

	/**
	 * @deprecated Use {@link EditorPartLookup#getActiveEditor()}
	 * @return
	 */
	public IEditorPart getActiveEditor(){
		IWorkbenchPart workbenchPart =  getActiveWorkbenchPart();
		IEditorPart editorPart = null;
		if (!(workbenchPart instanceof IEditorPart)) {
			editorPart = Display.syncExec(new ResultRunnable<IEditorPart>() {

				@Override
				public IEditorPart run() {
					return PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage()
							.getActiveEditor();
				}
			});
		} else {
			editorPart = (IEditorPart)workbenchPart;
		}
		if (editorPart == null) {
			throw new WorkbenchPartNotFound();
		}
		return editorPart;
	}

	/**
	 * @deprecated Use {@link EditorPartLookup#getEditorByTitle(Matcher)()}
	 * and {@link EditorPartTitleMatcher}
	 * @return
	 */
	public IEditorPart getEditorByTitle(final Matcher<String> title) {
		return Display.syncExec(new ResultRunnable<IEditorPart>() {

			@Override
			public IEditorPart run() {
				IWorkbenchWindow activeWorkbenchWindow = PlatformUI
						.getWorkbench().getActiveWorkbenchWindow();
				IEditorReference[] editors = activeWorkbenchWindow
						.getActivePage().getEditorReferences();
				for (IEditorReference iEditorReference : editors) {
					if (title.matches(iEditorReference.getPartName())) {
						return iEditorReference.getEditor(false);
					} else if (title.matches(iEditorReference.getEditor(false).getEditorInput().getName())) {
						return iEditorReference.getEditor(false);
					} else if (title.matches(iEditorReference.getEditor(false).getEditorInput().getToolTipText())) {
						return iEditorReference.getEditor(false);
					}
				}
				return null;
			}
		});
	}

	/**
	 * Returns all views that are currently open in the current perspective (including those
	 * on non-active tabs)
	 * 
	 * @param title
	 * @return
	 */
	public List<IViewPart> getOpenViews(){
		IWorkbenchPage activePage = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
		List<IViewPart> views = new ArrayList<IViewPart>();

		log.debug("Looking up all open views");
		for (IViewReference viewReference : activePage.getViewReferences()){
			IViewPart view = viewReference.getView(false);
			if (view == null){
				log.trace("\tView " + viewReference.getTitle() + " was not open or it cannot be restored");
				continue;
			}

			IViewPart[] stackedViews = activePage.getViewStack(view);
			if (stackedViews == null){
				log.trace("\tView " + view.getTitle() + " does not belong to the currently active page");
				continue;
			} 

			for (IViewPart part : viewReference.getPage().getViewStack(viewReference.getView(false))){
				log.trace("\tFound view " + view.getTitle());
				views.add(part);
			}
		}
		return views;
	}

	/**
	 * Returns view if is open in the current perspective by its name
	 * @param name
	 * @return
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
				log.debug("View matching " + name + " not found");
				log.debug("List of found views:");
				for (IViewPart view : views) {
					log.debug("\t" + view.getViewSite().getRegisteredName());
				}
			}
		});
	}
}
