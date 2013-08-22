package org.jboss.reddeer.uiforms.lookup;

import org.eclipse.ui.forms.widgets.Form;
import org.hamcrest.Matcher;
import org.jboss.reddeer.swt.lookup.WidgetLookup;

/**
 * UIForm Lookup containing lookup routines for Eclipse Form widget type
 * @author jjankovi
 *
 */
public class UIFormLookup {

	private static UIFormLookup instance = null;

	private UIFormLookup() {
	}

	/**
	 * Creates and returns instance of UIForm Lookup
	 * 
	 * @return UIFormLookup instance
	 */
	public static UIFormLookup getInstance() {
		if (instance == null)
			instance = new UIFormLookup();
		return instance;
	}
	
	/**
	 * Return Eclipse Form instance
	 * 
	 * @param matcher
	 * @return Form Widget matching criteria
	 */
	@SuppressWarnings({ "rawtypes" })
	public Form getForm(int index, Matcher... matchers) {
		return (Form)WidgetLookup.getInstance().activeWidget(Form.class, index, matchers);
	}
	
}
