package org.jboss.reddeer.workbench.impl.editor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.eclipse.jface.bindings.TriggerSequence;
import org.eclipse.jface.bindings.keys.KeyStroke;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.actions.ActionFactory;
import org.eclipse.ui.keys.IBindingService;
import org.eclipse.ui.texteditor.ITextEditorActionDefinitionIds;
import org.hamcrest.Matcher;
import org.jboss.reddeer.eclipse.jface.text.contentassist.ContentAssistant;
import org.jboss.reddeer.junit.logging.Logger;
import org.jboss.reddeer.swt.api.Table;
import org.jboss.reddeer.swt.condition.WaitCondition;
import org.jboss.reddeer.swt.exception.WaitTimeoutExpiredException;
import org.jboss.reddeer.swt.handler.ShellHandler;
import org.jboss.reddeer.swt.impl.table.DefaultTable;
import org.jboss.reddeer.swt.keyboard.KeyboardFactory;
import org.jboss.reddeer.swt.lookup.ShellLookup;
import org.jboss.reddeer.swt.matcher.WithTextMatcher;
import org.jboss.reddeer.swt.wait.WaitUntil;
import org.jboss.reddeer.workbench.api.Editor;
import org.jboss.reddeer.workbench.exception.WorkbenchLayerException;
import org.jboss.reddeer.workbench.exception.WorkbenchPartNotFound;
import org.jboss.reddeer.workbench.handler.EditorHandler;
import org.jboss.reddeer.workbench.handler.WorkbenchPartHandler;
import org.jboss.reddeer.workbench.lookup.WorkbenchPartLookup;

/**
 * Abstract class for all Editor implementations
 * 
 * @author rawagner
 * 
 */
public class AbstractEditor implements Editor {

	protected final Logger log = Logger.getLogger(this.getClass());
	protected IEditorPart editorPart;

	/**
	 * Initialize currently active editor
	 */
	public AbstractEditor() {
		editorPart = WorkbenchPartLookup.getInstance().getActiveEditor();
		EditorHandler.getInstance().activate(editorPart);
	}

	/**
	 * Initialize editor with given title and activate it.
	 * 
	 * @param title
	 *            Title of editor to initialize and activate
	 */
	public AbstractEditor(String title) {
		EditorPartIsFound found = new EditorPartIsFound(title);
		try {
			new WaitUntil(found);
		} catch (WaitTimeoutExpiredException ex) {
			throw new WorkbenchPartNotFound("Unable to find editor with title "
					+ title);
		}
		editorPart = found.getPart();
		EditorHandler.getInstance().activate(editorPart);
	}

	/**
	 * Initialize editor with given title matcher.
	 * 
	 * @param titleMatcher
	 *            Title matcher of editor to initialize and activate
	 */
	public AbstractEditor(final Matcher<String> title) {
		EditorPartIsFound found = new EditorPartIsFound(title);
		try {
			new WaitUntil(found);
		} catch (WaitTimeoutExpiredException ex) {
			throw new WorkbenchPartNotFound("Unable to find editor with title "
					+ title);
		}
		editorPart = found.getPart();
		EditorHandler.getInstance().activate(editorPart);
	}

	@Override
	public String getTitle() {
		return WorkbenchPartHandler.getInstance().getTitle(editorPart);
	}
	
	@Override
	public String getTitleToolTip(){
		return WorkbenchPartHandler.getInstance().getTitleToolTip(editorPart);
	}

	@Override
	public boolean isDirty() {
		return EditorHandler.getInstance().isDirty(editorPart);
	}

	@Override
	public void save() {
		EditorHandler.getInstance().save(editorPart);
	}

	@Override
	public void close() {
		close(false);
	}

	@Override
	public void close(boolean save) {
		EditorHandler.getInstance().close(save, editorPart);
	}

	@Override
	public boolean isActive() {
		return EditorHandler.getInstance().isActive(editorPart);
	}

	@Override
	public void maximize() {
		EditorHandler.getInstance().activate(editorPart);
		WorkbenchPartHandler.getInstance()
				.performAction(ActionFactory.MAXIMIZE);
	}

	@Override
	public void minimize() {
		EditorHandler.getInstance().activate(editorPart);
		WorkbenchPartHandler.getInstance()
				.performAction(ActionFactory.MINIMIZE);
	}
	
	/**
	 * @see org.jboss.reddeer.workbench.api.Editor#openContentAssistant()
	 */
	@Override
	public ContentAssistant openContentAssistant(){
		Shell[] shells1 = ShellLookup.getInstance().getShells();
		IBindingService bindingService = (IBindingService) PlatformUI.getWorkbench().getAdapter(IBindingService.class);
		TriggerSequence[] sequence = bindingService.getActiveBindingsFor(ITextEditorActionDefinitionIds.CONTENT_ASSIST_PROPOSALS);
		if(sequence.length > 0 && sequence[0].getTriggers().length > 0){
			if(sequence[0].getTriggers()[0] instanceof KeyStroke){
				KeyStroke k = ((KeyStroke)sequence[0].getTriggers()[0]);
				KeyboardFactory.getKeyboard().invokeKeyCombination(k.getModifierKeys(),k.getNaturalKey());
			} else {
				throw new WorkbenchLayerException("Unable to find key combination which invokes Content Assistant");
			}
		} else {
			throw new WorkbenchLayerException("Unable to find key combination which invokes Content Assistant");
		}
		Shell[] shells2 = ShellLookup.getInstance().getShells();
		Table contentAssistTable = findContentAssistTable(shells1, shells2);
		return new ContentAssistant(contentAssistTable);
	}
	
	private Table findContentAssistTable(Shell[] s1, Shell[] s2){
		List<Shell> s1List = new ArrayList<Shell>(Arrays.asList(s1));
		List<Shell> s2List = new ArrayList<Shell>(Arrays.asList(s2));
		s2List.removeAll(s1List);
		if(s2List.size() == 1){
			ShellHandler.getInstance().setFocus(s2List.get(0));
			return new DefaultTable();
		} else {
			throw new WorkbenchLayerException("Unable to find Content Assistant shell. "+
					(s2List.size() > 1 ? "More shells were opened":"No shell was opened"));
		}
	}

	protected IEditorPart getEditorPart() {
		return editorPart;
	}

	class EditorPartIsFound implements WaitCondition {

		private String title;
		private Matcher<String> titleMatcher;
		private IEditorPart part;

		public EditorPartIsFound(String title) {
			this.title = title;
		}

		public EditorPartIsFound(Matcher<String> title) {
			this.titleMatcher = title;
		}

		@Override
		public boolean test() {
			if (title != null) {
				Matcher<String> titleM = new WithTextMatcher(title);
				part = WorkbenchPartLookup.getInstance().getEditorByTitle(
						titleM);
			} else {
				part = WorkbenchPartLookup.getInstance().getEditorByTitle(
						titleMatcher);
			}
			if (part == null) {
				return false;
			}
			return true;
		}

		@Override
		public String description() {
			if (title != null) {
				return "editorPart with title " + title + " is found";
			}
			return "editorPart with title " + titleMatcher + " is found";
		}

		public IEditorPart getPart() {
			return part;
		}

	}

}
