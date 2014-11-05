package org.jboss.reddeer.workbench.lookup;

import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IEditorReference;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.IViewReference;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.hamcrest.Matcher;
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
	
	public IViewPart getViewByTitle(final Matcher<String> title){
		return Display.syncExec(new ResultRunnable<IViewPart>() {

			@Override
			public IViewPart run() {
				IViewReference[] views = PlatformUI.getWorkbench()
						.getActiveWorkbenchWindow().getActivePage()
						.getViewReferences();
				for (IViewReference iViewReference : views) {
					if (title.matches(iViewReference.getPartName())) {
						return iViewReference.getView(false);
					}
				}
				return null;
			}
		});
	}

}
