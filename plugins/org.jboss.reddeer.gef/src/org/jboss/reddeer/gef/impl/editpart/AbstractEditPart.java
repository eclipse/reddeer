package org.jboss.reddeer.gef.impl.editpart;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Control;
import org.jboss.reddeer.gef.api.EditPart;
import org.jboss.reddeer.gef.handler.EditPartHandler;
import org.jboss.reddeer.swt.api.Text;
import org.jboss.reddeer.swt.handler.WidgetHandler;
import org.jboss.reddeer.swt.impl.text.DefaultText;
import org.jboss.reddeer.swt.reference.ReferencedComposite;

/**
 * Abstract class for EditPart implementation
 * 
 * @author Andrej Podhradsky (andrej.podhradsky@gmail.com)
 *
 */
public abstract class AbstractEditPart implements EditPart, ReferencedComposite {

	protected org.eclipse.gef.GraphicalViewer viewer;
	protected org.eclipse.gef.EditPart editPart;

	/**
	 * Constructs edit part from {@link org.eclipse.gef.EditPart}.
	 * 
	 * @param editPart
	 */
	public AbstractEditPart(org.eclipse.gef.EditPart editPart) {
		this.editPart = editPart;
	}

	@Override
	public void select() {
		EditPartHandler.getInstance().select(editPart);
	}

	@Override
	public void setLabel(String text) {
		EditPartHandler.getInstance().select(editPart);
		EditPartHandler.getInstance().directEdit(editPart);
		Text inputText = new DefaultText(this, 0);
		inputText.setText(text);
		WidgetHandler.getInstance().notify(SWT.DefaultSelection, inputText.getSWTWidget());
	}

	@Override
	public Control getControl() {
		return editPart.getViewer().getControl();
	}

}
