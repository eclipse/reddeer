package org.jboss.reddeer.gef.comparator;

import java.util.Comparator;

import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gef.EditPart;
import org.jboss.reddeer.gef.handler.EditPartHandler;

/**
 * Compares two edit parts according to their position.
 * 
 * @author Andrej Podhradsky (andrej.podhradsky@gmail.com)
 *
 */
public class PositionComparator implements Comparator<EditPart> {

	@Override
	public int compare(EditPart o1, EditPart o2) {
		Rectangle rec1 = EditPartHandler.getInstance().getFigure(o1).getBounds();
		Rectangle rec2 = EditPartHandler.getInstance().getFigure(o2).getBounds();
		int result = compare(rec1.x, rec2.x);
		if (result == 0) {
			result = compare(rec1.y, rec2.y);
		}
		return result;
	}

	private static int compare(int x, int y) {
		return (x < y) ? -1 : ((x == y) ? 0 : 1);
	}

}
