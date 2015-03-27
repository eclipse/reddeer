package org.jboss.reddeer.uiforms.lookup;

import org.eclipse.ui.forms.widgets.Hyperlink;
import org.eclipse.ui.forms.widgets.ImageHyperlink;
import org.hamcrest.Matcher;
import org.jboss.reddeer.core.exception.SWTLayerException;
import org.jboss.reddeer.swt.lookup.WidgetLookup;
import org.jboss.reddeer.core.reference.ReferencedComposite;
import org.jboss.reddeer.swt.widgets.AbstractWidget;

/**
 * UIFormHyperlink Lookup containing lookup routines for Eclipse Forms Hyperlink widget type
 * @deprecated Use {@link AbstractWidget} instead of lookups. Will be removed in 1.0.0
 * @author jjankovi
 * 
 */
public class HyperlinkLookup {

	private static HyperlinkLookup instance = null;

	private HyperlinkLookup() {
	}

	/**
	 * Creates and returns instance of UIFormHyperlink Lookup
	 * 
	 * @return UIFormHyperlinkLookup instance
	 */
	public static HyperlinkLookup getInstance() {
		if (instance == null)
			instance = new HyperlinkLookup();
		return instance;
	}
	
	/**
	 * Return Eclipse Forms Hyperlink instance
	 * 
	 * @param matcher
	 * @return Hyperlink Widget matching criteria
	 */
	@SuppressWarnings({ "rawtypes" })
	public Hyperlink getHyperlink(ReferencedComposite referencedComposite, int index, Matcher... matchers) {
		Hyperlink link;
		try{
			link= (Hyperlink)WidgetLookup.getInstance().activeWidget(referencedComposite, Hyperlink.class, index, matchers);
		} catch(SWTLayerException ex){
			link= (Hyperlink)WidgetLookup.getInstance().activeWidget(referencedComposite, ImageHyperlink.class, index, matchers);
		}
		return link;
	}
	
}
