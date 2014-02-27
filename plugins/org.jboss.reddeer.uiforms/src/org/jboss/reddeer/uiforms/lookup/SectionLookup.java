package org.jboss.reddeer.uiforms.lookup;

import org.eclipse.ui.forms.widgets.Section;
import org.hamcrest.Matcher;
import org.jboss.reddeer.swt.lookup.WidgetLookup;
import org.jboss.reddeer.swt.reference.ReferencedComposite;

/**
 * UIFormSection Lookup containing lookup routines for Eclipse Forms Section widget type
 * @author jjankovi
 *
 */
public class SectionLookup {

	private static SectionLookup instance = null;

	private SectionLookup() {
	}

	/**
	 * Creates and returns instance of UIFormSection Lookup
	 * 
	 * @return UIFormSectionLookup instance
	 */
	public static SectionLookup getInstance() {
		if (instance == null)
			instance = new SectionLookup();
		return instance;
	}
	
	/**
	 * Return Eclipse Forms Section instance
	 * 
	 * @param matcher
	 * @return Section Widget matching criteria
	 */
	@SuppressWarnings({ "rawtypes" })
	public Section getSection(ReferencedComposite referencedComposite, int index, Matcher... matchers) {
		return (Section)WidgetLookup.getInstance().activeWidget(referencedComposite, Section.class, index, matchers);
	}
	
}
