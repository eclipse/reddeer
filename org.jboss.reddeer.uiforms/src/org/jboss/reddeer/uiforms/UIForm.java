package org.jboss.reddeer.uiforms;

import org.eclipse.ui.forms.widgets.Form;
import org.jboss.reddeer.swt.handler.WidgetHandler;
import org.jboss.reddeer.swt.reference.ReferenceComposite;
import org.jboss.reddeer.swt.reference.ReferencedComposite;
import org.jboss.reddeer.uiforms.lookup.UIFormLookup;

/**
 * Represents Eclipse Form. This class will be used mainly for its children discovering
 * 
 * @author jjankovi
 *
 */
public class UIForm implements ReferencedComposite {

	private Form form;
	
	/**
	 * Creates UIForm
	 */
	public UIForm() {
		this(0);
	}
	
	/**
	 * Creates UIForm with given index
	 * @param index
	 */
	public UIForm(int index) {
		form = UIFormLookup.getInstance().getForm(index);
		
		setFocus();
		setAsReference();
	}

	@Override
	public void setAsReference() {
		ReferenceComposite.setComposite(form);
	}
	
	private void setFocus() {
		WidgetHandler.getInstance().setFocus(form);
	}
	
}
