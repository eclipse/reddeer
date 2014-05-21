package org.jboss.reddeer.gef.util;

import java.util.List;

import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.Label;
import org.eclipse.draw2d.text.TextFlow;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPartViewer;
import org.eclipse.gef.GraphicalEditPart;
import org.jboss.reddeer.gef.handler.ViewerHandler;
import org.jboss.reddeer.gef.lookup.ViewerLookup;

/**
 * Helper functions for dumping info about object used in GEF.
 * 
 * @author Andrej Podhradsky (andrej.podhradsky@gmail.com)
 *
 */
public class DumpUtils {

	/**
	 * Dumps info about edit parts and their figures in an active part viewer.
	 * 
	 * @param viewer
	 *            Edit part viewer
	 */
	public static void dump() {
		dump(ViewerLookup.getInstance().findGraphicalViewer());
	}

	/**
	 * Dumps info about edit parts and their figures in a given part viewer.
	 * 
	 * @param viewer
	 *            Edit part viewer
	 */
	public static void dump(EditPartViewer viewer) {
		dump("====== All Figures ======", 0);
		dump(ViewerHandler.getInstance().getFigureCanvas(viewer).getContents(), 0);
		dump("====== Edit Parts ======", 0);
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
		dump(parent.getClass(), indent);
		if (parent instanceof Label) {
			dump("> Label: " + ((Label) parent).getText(), indent + 1);
		}
		if (parent instanceof TextFlow) {
			dump("> TextFlow: " + ((TextFlow) parent).getText(), indent + 1);
		}
		IFigure tooltip = parent.getToolTip();
		if (tooltip instanceof Label) {
			dump("> Tooltip: " + ((Label) tooltip).getText(), indent + 1);
		}
		@SuppressWarnings("unchecked")
		List<IFigure> list = parent.getChildren();
		for (IFigure figure : list) {
			dump(figure, indent + 1);
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
