package org.jboss.reddeer.graphiti.lookup;

import org.eclipse.graphiti.ui.editor.DiagramEditor;
import org.eclipse.ui.IEditorPart;
import org.jboss.reddeer.gef.GEFLayerException;
import org.jboss.reddeer.junit.logging.Logger;
import org.jboss.reddeer.swt.util.Display;
import org.jboss.reddeer.swt.util.ResultRunnable;
import org.jboss.reddeer.workbench.impl.editor.DefaultEditor;

/**
 * Lookup for {@link org.eclipse.graphiti.ui.editor.DiagramEditor}
 * 
 * @author Andrej Podhradsky (andrej.podhradsky@gmail.com)
 *
 */
public class DiagramEditorLookup {

	protected final Logger log = Logger.getLogger(this.getClass());

	private static DiagramEditorLookup instance;

	private DiagramEditorLookup() {

	}

	public static DiagramEditorLookup getInstance() {
		if (instance == null) {
			instance = new DiagramEditorLookup();
		}
		return instance;
	}

	/**
	 * Finds a diagram editor in an active editor
	 * 
	 * @return Diagram editor
	 */
	public DiagramEditor findDiagramEditor() {
		return findDiagramEditor(new ActiveEditor().getIEditorPart());
	}

	/**
	 * Finds a diagram editor in a given editor part.
	 * 
	 * @param editorPart
	 *            Editor part
	 * @return Diagram editor
	 */
	public DiagramEditor findDiagramEditor(final IEditorPart editorPart) {
		DiagramEditor diagramEditor = Display.syncExec(new ResultRunnable<DiagramEditor>() {
			@Override
			public DiagramEditor run() {
				return (DiagramEditor) editorPart.getAdapter(DiagramEditor.class);
			}
		});
		if (diagramEditor == null) {
			throw new GEFLayerException("Cannot find diagram editor in a given editor part");
		}
		return diagramEditor;
	}

	/**
	 * Helper class for achieving active {@link org.eclipse.ui.IEditorPart}. This class can be removed when
	 * {@link org.jboss.reddeer.workbench.lookup} is exported.
	 * 
	 * @author Andrej Podhradsky (andrej.podhradsky@gmail.com)
	 *
	 */
	private class ActiveEditor extends DefaultEditor {

		public ActiveEditor() {
			super();
		}

		public IEditorPart getIEditorPart() {
			return getEditorPart();
		}
	}
}
