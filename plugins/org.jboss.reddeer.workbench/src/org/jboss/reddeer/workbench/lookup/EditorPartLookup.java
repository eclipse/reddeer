package org.jboss.reddeer.workbench.lookup;

import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IEditorReference;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.hamcrest.Matcher;
import org.jboss.reddeer.common.condition.WaitCondition;
import org.jboss.reddeer.common.exception.WaitTimeoutExpiredException;
import org.jboss.reddeer.swt.matcher.AndMatcher;
import org.jboss.reddeer.core.util.Display;
import org.jboss.reddeer.core.util.ResultRunnable;
import org.jboss.reddeer.common.wait.WaitUntil;
import org.jboss.reddeer.workbench.exception.WorkbenchLayerException;
import org.jboss.reddeer.workbench.exception.WorkbenchPartNotFound;

/**
 * Contains lookup methods for editors. 
 * 
 * @author Lucia Jelinkova
 *
 */
public class EditorPartLookup {

	private static EditorPartLookup instance;

	private EditorPartLookup(){

	}

	/**
	 * Returns the singleton instance of this class.
	 * @return
	 */
	public static EditorPartLookup getInstance(){
		if(instance == null){
			instance = new EditorPartLookup();
		}
		return instance;
	}
	
	/**
	 * Returns currently active editor or first inactive. If no editor at all si found than 
	 * throws {@link WorkbenchLayerException}. 
	 * @return 
	 */
	public IEditorPart getEditor(){
		IEditorPart editorPart = getActiveEditor();
		if (editorPart == null) {
			final IEditorReference[] editorReferences = getAllEditors();
			if(editorReferences.length == 0){
				throw new WorkbenchPartNotFound();
			}
			editorPart = Display.syncExec(new ResultRunnable<IEditorPart>() {
					
				@Override
				public IEditorPart run() {
					return editorReferences[0].getEditor(false);
				}
			});
		}
		return editorPart;
	}

	/**
	 * Returns currently active editor or null. 
	 * @return 
	 */
	public IEditorPart getActiveEditor(){
		IWorkbenchPart workbenchPart =  WorkbenchPartLookup.getInstance().getActiveWorkbenchPart();
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
		return editorPart;
	}
	
	/**
	 * Returns editor matching given matchers or throws {@link WorkbenchLayerException}
	 * if none is found
	 * @param matchers
	 * @return
	 */
	public IEditorPart getEditor(final Matcher<IEditorPart>... matchers) {
		EditorPartIsFound found = new EditorPartIsFound(matchers);
        try {
            new WaitUntil(found);
        } catch (WaitTimeoutExpiredException ex) {
            throw new WorkbenchPartNotFound("Unable to find editor matching specified matchers");
        }
        return found.getPart();
	}
	
	private IEditorPart getEditorInternal(final Matcher<IEditorPart>... matchers) {
		final IEditorReference[] editorReferences = getAllEditors();
		
		return Display.syncExec(new ResultRunnable<IEditorPart>() {

			@Override
			public IEditorPart run() {
				AndMatcher andMatcher  = new AndMatcher(matchers);
				
				for (IEditorReference editorReference : editorReferences) {
					IEditorPart part = editorReference.getEditor(false);
					if (andMatcher.matches(part)) {
						return part;
					}
				}
				return null;
			}
		});
	}
	
	private IEditorReference[] getAllEditors(){
		return Display.syncExec(new ResultRunnable<IEditorReference[]>() {

			@Override
			public IEditorReference[] run() {
				IWorkbenchWindow activeWorkbenchWindow = PlatformUI.getWorkbench().getActiveWorkbenchWindow();
				return activeWorkbenchWindow.getActivePage().getEditorReferences();
			}
		});
	}
	
    /**
     * Condition to check if editor part is found.
     * @author rawagner
     */
    private class EditorPartIsFound implements WaitCondition {

        private Matcher<IEditorPart>[] matchers;
        
        private IEditorPart part;

        public EditorPartIsFound(Matcher<IEditorPart>[] matchers) {
            this.matchers = matchers;
        }

        @Override
        public boolean test() {
            part = EditorPartLookup.getInstance().getEditorInternal(matchers);
            
            if (part == null) {
                return false;
            }
            
            return true;
        }

        @Override
        public String description() {
            return "editorPart matching " + matchers + " is found";
        }

        public IEditorPart getPart() {
            return part;
        }
    }
}
