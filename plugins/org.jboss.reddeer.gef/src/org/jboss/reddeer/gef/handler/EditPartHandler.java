package org.jboss.reddeer.gef.handler;

import org.eclipse.draw2d.IFigure;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.GraphicalEditPart;
import org.eclipse.gef.requests.DirectEditRequest;
import org.jboss.reddeer.junit.logging.Logger;
import org.jboss.reddeer.swt.util.Display;

/**
 * Handler for {@link org.eclipse.gef.EditPart}.
 * 
 * @author Andrej Podhradsky (andrej.podhradsky@gmail.com)
 *
 */
public class EditPartHandler {

	protected final Logger log = Logger.getLogger(this.getClass());

	private static EditPartHandler instance;

	private EditPartHandler() {

	}

	public static EditPartHandler getInstance() {
		if (instance == null) {
			instance = new EditPartHandler();
		}
		return instance;
	}

	/**
	 * Select a given edit part in the specified viewer.
	 * 
	 * @param editPart
	 *            Edit part
	 */
	public void select(final EditPart editPart) {
		Display.getDisplay().syncExec(new Runnable() {
			@Override
			public void run() {
				editPart.getViewer().select(editPart);
			}
		});
	}

	/**
	 * Calls direct editing on the specified edit part.
	 * 
	 * @param editPart
	 *            Edit part
	 */
	public void directEdit(final EditPart editPart) {
		Display.getDisplay().syncExec(new Runnable() {
			@Override
			public void run() {
				DirectEditRequest request = new DirectEditRequest();
				editPart.performRequest(request);
			}
		});
	}

	/**
	 * Returns an appropriate figure of a given edit part.
	 * 
	 * @param editPart
	 *            Edit part
	 * @return Figure
	 */
	public IFigure getFigure(final EditPart editPart) {
		return ((GraphicalEditPart) editPart).getFigure();
	}

}
