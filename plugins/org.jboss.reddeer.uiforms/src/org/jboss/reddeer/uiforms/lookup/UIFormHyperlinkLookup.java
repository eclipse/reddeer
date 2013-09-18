package org.jboss.reddeer.uiforms.lookup;

import org.eclipse.ui.forms.widgets.Hyperlink;
import org.eclipse.ui.forms.widgets.ImageHyperlink;
import org.hamcrest.Matcher;
import org.jboss.reddeer.swt.exception.SWTLayerException;
import org.jboss.reddeer.swt.lookup.WidgetLookup;
import org.jboss.reddeer.swt.reference.ReferencedComposite;

/**
 * UIFormHyperlink Lookup containing lookup routines for Eclipse Forms Hyperlink widget type
 * @author jjankovi
 * 
 */
public class UIFormHyperlinkLookup {

	private static UIFormHyperlinkLookup instance = null;

	private UIFormHyperlinkLookup() {
	}

	/**
	 * Creates and returns instance of UIFormHyperlink Lookup
	 * 
	 * @return UIFormHyperlinkLookup instance
	 */
	public static UIFormHyperlinkLookup getInstance() {
		if (instance == null)
			instance = new UIFormHyperlinkLookup();
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
