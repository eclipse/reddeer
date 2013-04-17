package org.jboss.reddeer.uiforms.hyperlink;

import org.eclipse.ui.forms.widgets.Hyperlink;
import org.jboss.reddeer.swt.handler.WidgetHandler;
import org.jboss.reddeer.swt.matcher.TextMatcher;
import org.jboss.reddeer.uiforms.lookup.UIFormHyperlinkLookup;


/**
 * Represents Hyperlink object in eclipse forms context
 * 
 * @author jjankovi
 *
 */
public class UIFormHyperlink {

	private Hyperlink hyperLink;
	
	/**
	 * Creates UIFormHyperlink
	 */
	public UIFormHyperlink() {
		this(0);
	}
	
	/**
	 * Creates UIFormHyperlink with given text
	 * @param text
	 */
	public UIFormHyperlink(String text) {
		this(0, text);
	}
	
	/**
	 * Creates UIFormHyperlink with given index
	 * @param index
	 */
	public UIFormHyperlink(int index) {
		this(index, null);
	}
	
	/**
	 * Creates UIFormHyperlink with given text and index
	 * @param text
	 * @param index
	 */
	public UIFormHyperlink(int index, String text) {
		if (text != null && !text.isEmpty()) {
			hyperLink = UIFormHyperlinkLookup.getInstance().getHyperlink(index, new TextMatcher(text));
		} else {
			hyperLink = UIFormHyperlinkLookup.getInstance().getHyperlink(index);
		}
		
		setFocus();
	}
	
	/**
	 * Text of UIFormHyperlink
	 * @return
	 */
	public String getText() {
		return WidgetHandler.getInstance().getText(hyperLink);
	}
	
	private void setFocus() {
		WidgetHandler.getInstance().setFocus(hyperLink);
	}
	
}
