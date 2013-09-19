package org.jboss.reddeer.uiforms;

import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.forms.widgets.Form;
import org.jboss.reddeer.swt.handler.WidgetHandler;
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
	 * Creates UIForm inside given composite
	 * @param referencedComposite
	 */
	public UIForm(ReferencedComposite referencedComposite) {
		this(referencedComposite, 0);
	}
	
	/**
	 * Creates UIForm with given index
	 * @param index
	 */
	public UIForm(int index) {
		form = UIFormLookup.getInstance().getForm(null, index);
		
		setFocus();
	}
	
	/**
	 * Creates UIForm with given index inside given composite
	 * @param referencedComposite
	 * @param index
	 */
	public UIForm(ReferencedComposite referencedComposite, int index) {
		form = UIFormLookup.getInstance().getForm(referencedComposite, index);
		
		setFocus();
	}
	
	private void setFocus() {
		WidgetHandler.getInstance().setFocus(form);
	}

	@Override
	public Control getControl() {
		return form;
	}
	
}
