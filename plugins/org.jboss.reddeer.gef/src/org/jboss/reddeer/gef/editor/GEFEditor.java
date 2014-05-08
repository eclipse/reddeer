package org.jboss.reddeer.gef.editor;

import org.eclipse.draw2d.FigureCanvas;
import org.eclipse.gef.GraphicalViewer;
import org.eclipse.swt.SWT;
import org.jboss.reddeer.gef.GEFLayerException;
import org.jboss.reddeer.gef.api.Palette;
import org.jboss.reddeer.gef.condition.EditorHasEditParts;
import org.jboss.reddeer.gef.handler.ViewerHandler;
import org.jboss.reddeer.gef.view.PaletteView;
import org.jboss.reddeer.swt.handler.WidgetHandler;
import org.jboss.reddeer.swt.util.Display;
import org.jboss.reddeer.swt.util.ResultRunnable;
import org.jboss.reddeer.swt.wait.WaitUntil;
import org.jboss.reddeer.workbench.impl.editor.DefaultEditor;

/**
 * 
 * GEF Editor
 * 
 * @author apodhrad
 * 
 */
public class GEFEditor extends DefaultEditor {

	protected GraphicalViewer viewer;

	/**
	 * Constructs a GEF editor with currently active editor. It also initializes graphical viewer which can be
	 * overridden by initGraphicalViewer().
	 */
	public GEFEditor() {
		super();
		initGraphicalViewer();
	}

	/**
	 * Constructs a GEF editor with a given title. It also initializes graphical viewer which can be overridden by
	 * initGraphicalViewer().
	 * 
	 * @param title
	 *            Editor title
	 */
	public GEFEditor(String title) {
		super(title);
		initGraphicalViewer();
	}

	/**
	 * Initializes a graphical viewer which is needed for GEF operations.
	 */
	protected void initGraphicalViewer() {
		viewer = Display.syncExec(new ResultRunnable<GraphicalViewer>() {
			@Override
			public GraphicalViewer run() {
				return (GraphicalViewer) getEditorPart().getAdapter(GraphicalViewer.class);
			}
		});
	}

	protected GraphicalViewer getGraphicalViewer() {
		if (viewer == null) {
			throw new GEFLayerException(
					"No graphical viewer has been initialized. Graphical viewer is needed for many GED operations.");
		}
		return viewer;
	}

	private FigureCanvas getFigureCanvas() {
		return (FigureCanvas) viewer.getControl();
	}

	/**
	 * Returns the number of all available edit parts.
	 * 
	 * @return Number of edit parts
	 */
	public int getNumberOfEditParts() {
		return ViewerHandler.getInstance().getEditParts(viewer).size();
	}

	/**
	 * Clicks at the specified coordinates.
	 * 
	 * @param x
	 *            X-axis
	 * @param y
	 *            Y-axis
	 */
	public void click(int x, int y) {
		WidgetHandler handler = WidgetHandler.getInstance();
		FigureCanvas figureCanvas = getFigureCanvas();
		handler.notifyItemMouse(SWT.MouseMove, 0, figureCanvas, null, x, y, 0);
		handler.notifyItemMouse(SWT.MouseDown, 0, figureCanvas, null, x, y, 1);
		handler.notifyItemMouse(SWT.MouseUp, 0, figureCanvas, null, x, y, 1);
	}

	/**
	 * Returns a palette.
	 * 
	 * @return Palette
	 */
	public Palette getPalette() {
		new PaletteView().open();
		return ViewerHandler.getInstance().getPalette(viewer);
	}

	/**
	 * Adds a tool from a palette to the specified coordinates.
	 * 
	 * @param tool
	 *            Tool label
	 * @param x
	 *            X-axis
	 * @param y
	 *            Y-axis
	 */
	public void addToolFromPalette(String tool, final int x, final int y) {
		addToolFromPalette(tool, null, x, y);
	}

	/**
	 * Adds a tool in a given group from a palette to the specified coordinates.
	 * 
	 * @param tool
	 *            Tool label
	 * @param group
	 *            Group label
	 * @param x
	 *            X-axis
	 * @param y
	 *            Y-axis
	 */
	public void addToolFromPalette(String tool, String group, final int x, final int y) {
		int oldCount = getNumberOfEditParts();

		getPalette().activateTool(tool, group);
		click(x, y);

		new WaitUntil(new EditorHasEditParts(this, oldCount));
	}

}
