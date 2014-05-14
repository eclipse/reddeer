package org.jboss.reddeer.gef.comparator;

import java.util.Comparator;

import org.eclipse.gef.EditPart;

/**
 * Compares two edit parts according to the number of their children.
 * 
 * @author Andrej Podhradsky (andrej.podhradsky@gmail.com)
 *
 */
public class ChildrenComparator implements Comparator<EditPart> {

	@Override
	public int compare(EditPart o1, EditPart o2) {
		return compare(o1.getChildren().size(), o2.getChildren().size());
	}

	private static int compare(int x, int y) {
		return (x < y) ? -1 : ((x == y) ? 0 : 1);
	}

}
