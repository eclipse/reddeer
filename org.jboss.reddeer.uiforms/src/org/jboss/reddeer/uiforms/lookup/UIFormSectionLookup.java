package org.jboss.reddeer.uiforms.lookup;

import org.eclipse.ui.forms.widgets.Section;
import org.hamcrest.Matcher;
import org.jboss.reddeer.swt.lookup.impl.WidgetLookup;

/**
 * UIFormSection Lookup containing lookup routines for Eclipse Forms Section widget type
 * @author jjankovi
 *
 */
public class UIFormSectionLookup {

	private static UIFormSectionLookup instance = null;

	private UIFormSectionLookup() {
	}

	/**
	 * Creates and returns instance of UIFormSection Lookup
	 * 
	 * @return UIFormSectionLookup instance
	 */
	public static UIFormSectionLookup getInstance() {
		if (instance == null)
			instance = new UIFormSectionLookup();
		return instance;
	}
	
	/**
	 * Return Eclipse Forms Section instance
	 * 
	 * @param matcher
	 * @return Section Widget matching criteria
	 */
	@SuppressWarnings({ "rawtypes" })
	public Section getSection(int index, Matcher... matchers) {
		return (Section)WidgetLookup.getInstance().activeWidget(Section.class, index, matchers);
	}
	
}
