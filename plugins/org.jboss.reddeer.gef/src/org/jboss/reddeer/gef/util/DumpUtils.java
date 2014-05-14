package org.jboss.reddeer.gef.util;

import java.util.List;

import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.Label;
import org.eclipse.draw2d.text.TextFlow;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPartViewer;
import org.eclipse.gef.GraphicalEditPart;
import org.hamcrest.core.IsInstanceOf;
import org.jboss.reddeer.gef.finder.FigureFinder;

/**
 * Helper functions for dumping info about object used in GEF.
 * 
 * @author Andrej Podhradsky (andrej.podhradsky@gmail.com)
 *
 */
public class DumpUtils {

	/**
	 * Dumps info about edit parts and their figures in a given part viewer.
	 * 
	 * @param viewer
	 *            Edit part viewer
	 */
	public static void dump(EditPartViewer viewer) {
		dump("====== DEBUG ======", 0);
		dump(viewer.getContents(), 0);
		dump("===================", 0);
	}

	/**
	 * Dumps info about a given edit part and its figures.
	 * 
	 * @param parent
	 *            Edit part
	 * @param indent
	 *            Indentation
	 */
	public static void dump(EditPart parent, int indent) {
		dump(parent.getClass(), indent);
		if (parent instanceof GraphicalEditPart) {
			dump(((GraphicalEditPart) parent).getFigure(), indent + 1);
		}
		@SuppressWarnings("unchecked")
		List<EditPart> editParts = parent.getChildren();
		for (EditPart editPart : editParts) {
			dump(editPart, indent + 1);
		}
	}

	/**
	 * Dumps info about a given figure.
	 * 
	 * @param parent
	 *            Figure
	 * @param indent
	 *            Indentation
	 */
	public static void dump(IFigure parent, int indent) {
		List<IFigure> list = new FigureFinder().find(parent, new IsInstanceOf(IFigure.class));
		for (IFigure figure : list) {
			dump(figure.getClass(), indent + 1);
			if (figure instanceof Label) {
				dump("> Label: " + ((Label) figure).getText(), indent + 1);
			}
			if (figure instanceof TextFlow) {
				dump("> TextFlow: " + ((TextFlow) figure).getText(), indent + 1);
			}
			IFigure tooltip = figure.getToolTip();
			if (tooltip instanceof Label) {
				dump("> Tooltip: " + ((Label) tooltip).getText(), indent + 1);
			}
		}
	}

	/**
	 * Dumps simple info about a given object.
	 * 
	 * @param obj
	 *            Any object
	 * @param indent
	 *            Indentation
	 */
	public static void dump(Object obj, int indent) {
		for (int i = 0; i < indent; i++) {
			System.out.print("  ");
		}
		System.out.println(obj);
	}

}
