package org.jboss.reddeer.uiforms.hyperlink;

import org.eclipse.ui.forms.widgets.Hyperlink;
import org.jboss.reddeer.swt.handler.WidgetHandler;
import org.jboss.reddeer.swt.matcher.WithMnemonicMatcher;
import org.jboss.reddeer.swt.reference.ReferencedComposite;
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
	 * Creates UIFormHyperlink inside given composite
	 * @param referencedComposite
	 */
	public UIFormHyperlink(ReferencedComposite referencedComposite) {
		this(referencedComposite, 0);
	}
	
	/**
	 * Creates UIFormHyperlink with given text
	 * @param text
	 */
	public UIFormHyperlink(String text) {
		this(0, text);
	}
	
	/**
	 * Creates UIFormHyperlink with given text inside given composite
	 * @param referencedComposite
	 * @param text
	 */
	public UIFormHyperlink(ReferencedComposite referencedComposite, String text) {
		this(referencedComposite, 0, text);
	}
	
	/**
	 * Creates UIFormHyperlink with given index
	 * @param index
	 */
	public UIFormHyperlink(int index) {
		this(index, null);
	}
	
	/**
	 * Creates UIFormHyperlink with given index inside given composite
	 * @param referencedComposite
	 * @param index
	 */
	public UIFormHyperlink(ReferencedComposite referencedComposite, int index) {
		this(referencedComposite, index, null);
	}
	
	/**
	 * Creates UIFormHyperlink with given text and index
	 * @param text
	 * @param index
	 */
	public UIFormHyperlink(int index, String text) {
		if (text != null && !text.isEmpty()) {
			hyperLink = UIFormHyperlinkLookup.getInstance().getHyperlink(null, index, new WithMnemonicMatcher(text));
		} else {
			hyperLink = UIFormHyperlinkLookup.getInstance().getHyperlink(null, index);
		}
		
		setFocus();
	}
	
	/**
	 * Creates UIFormHyperlink with given text and index inside given composite
	 * @param referencedComposite
	 * @param text
	 * @param index
	 */
	public UIFormHyperlink(ReferencedComposite referencedComposite, int index, String text) {
		if (text != null && !text.isEmpty()) {
			hyperLink = UIFormHyperlinkLookup.getInstance().getHyperlink(referencedComposite, index, new WithMnemonicMatcher(text));
		} else {
			hyperLink = UIFormHyperlinkLookup.getInstance().getHyperlink(referencedComposite, index);
		}
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
	
	public void activate() {
		WidgetHandler.getInstance().activate(hyperLink);
	}

	
}
