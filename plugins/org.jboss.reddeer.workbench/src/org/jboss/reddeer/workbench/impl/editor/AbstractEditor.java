package org.jboss.reddeer.workbench.impl.editor;

import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.actions.ActionFactory;
import org.hamcrest.Matcher;
import org.jboss.reddeer.junit.logging.Logger;
import org.jboss.reddeer.swt.condition.WaitCondition;
import org.jboss.reddeer.swt.exception.WaitTimeoutExpiredException;
import org.jboss.reddeer.swt.matcher.TextMatcher;
import org.jboss.reddeer.swt.wait.WaitUntil;
import org.jboss.reddeer.workbench.api.Editor;
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
				Matcher<String> titleM = new TextMatcher(title);
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
				return "EditorPart with title " + title + " is found.";
			}
			return "EditorPart with title " + titleMatcher + " is found.";
		}

		public IEditorPart getPart() {
			return part;
		}

	}

}
