package org.jboss.reddeer.gef.lookup;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPartViewer;
import org.hamcrest.Matcher;
import org.jboss.reddeer.common.logging.Logger;
import org.jboss.reddeer.gef.GEFLayerException;
import org.jboss.reddeer.gef.handler.ViewerHandler;

/**
 * Lookup for {@link org.eclipse.gef.EditPart}.
 * 
 * @author Andrej Podhradsky (andrej.podhradsky@gmail.com)
 *
 */
public class EditPartLookup {

	protected final Logger log = Logger.getLogger(this.getClass());

	private static EditPartLookup instance;

	private EditPartLookup() {

	}

	/**
	 * Gets the single instance of EditPartLookup.
	 *
	 * @return single instance of EditPartLookup
	 */
	public static EditPartLookup getInstance() {
		if (instance == null) {
			instance = new EditPartLookup();
		}
		return instance;
	}

	/**
	 * Finds an edit part which is fulfilled by the specified matcher at a given index. The edit part is searched in the
	 * active editor.
	 * 
	 * @param matcher
	 *            Matcher
	 * @param index
	 *            Index
	 * @return Edit part
	 */
	public EditPart findEditPart(Matcher<EditPart> matcher, int index) {
		return findEditPart(ViewerLookup.getInstance().findGraphicalViewer(), matcher, index, null);
	}

	/**
	 * Finds an edit part which is fulfilled by the specified matcher at a given index. The order is given by the
	 * comparator. The edit part is searched in the active editor.
	 * 
	 * @param matcher
	 *            Matcher
	 * @param index
	 *            Index
	 * @param comparator
	 *            Edit parts comparator
	 * @return Edit part
	 */
	public EditPart findEditPart(Matcher<EditPart> matcher, int index, Comparator<EditPart> comparator) {
		return findEditPart(ViewerLookup.getInstance().findGraphicalViewer(), matcher, index, comparator);
	}

	/**
	 * Finds an edit part which is fulfilled by the specified matcher at a given index. The order is given by the
	 * comparator. The edit part is searched in a given viewer.
	 * 
	 * @param viewer
	 *            Edit part viewer
	 * @param matcher
	 *            Matcher
	 * @param index
	 *            Index
	 * @param comparator
	 *            Edit parts comparator
	 * @return Edit part
	 */
	public EditPart findEditPart(EditPartViewer viewer, Matcher<EditPart> matcher, int index,
			Comparator<EditPart> comparator) {
		List<EditPart> editParts = ViewerHandler.getInstance().getEditParts(viewer, matcher);
		if (editParts.size() <= 0) {
			throw new GEFLayerException("Cannot find edit part with matcher " + matcher + " at index " + index);
		}
		if (comparator != null) {
			Collections.sort(editParts, comparator);
		}
		return editParts.get(index);
	}

}
