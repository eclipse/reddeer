package org.jboss.reddeer.swt.impl.ctab;

import org.jboss.reddeer.swt.lookup.CTabItemLookup;
import org.jboss.reddeer.swt.matcher.WithMnemonicMatcher;
import org.jboss.reddeer.swt.reference.ReferencedComposite;

/**
 * Default CTabItem implementation
 * @author Vlado Pakan
 *
 */
public class DefaultCTabItem extends AbstractCTabItem {

	/**
	 * Default parameter-less constructor
	 */
	public DefaultCTabItem() {
		this(0);
	}
	
	/**
	 * CTabItem inside given composite
	 * @param referencedComposite
	 */
	public DefaultCTabItem(ReferencedComposite referencedComposite) {
		this(referencedComposite, 0);
	}
	
	/**
	 * CTabItem with specified index will be constructed 
	 * 
	 * @param index
	 */
	public DefaultCTabItem(int index) {
		this(index, null);
	}
	
	/**
	 * CTabItem with specified index inside given composite will be constructed 
	 * @param referencedComposite
	 * @param index
	 */
	public DefaultCTabItem(ReferencedComposite referencedComposite, int index) {
		this(referencedComposite, index, null);
	}
	
	/**
	 * CTabItem with specified index and text will be constructed 
	 * 
	 * @param index
	 * @param text
	 */
	public DefaultCTabItem(int index, String text) {
		super((text != null && !text.isEmpty()) ? CTabItemLookup.getInstance()
				.getCTabItem(null, index, new WithMnemonicMatcher(text))
				: CTabItemLookup.getInstance().getCTabItem(null, index));
	}
	
	/**
	 * CTabItem with specified index and text inside given composite will be constructed 
	 * @param referencedComposite
	 * @param index
	 * @param text
	 */
	public DefaultCTabItem(ReferencedComposite referencedComposite, int index, String text) {
		super((text != null && !text.isEmpty()) ? CTabItemLookup.getInstance()
				.getCTabItem(referencedComposite, index, new WithMnemonicMatcher(text))
				: CTabItemLookup.getInstance().getCTabItem(referencedComposite, index));
	}
	
	/**
	 * CTabItem with specified text will be constructed 
	 * 
	 * @param text
	 */
	public DefaultCTabItem(String text) {
		this(0, text);
	}
	
	/**
	 * CTabItem with specified text inside given composite will be constructed 
	 * @param referencedComposite
	 * @param text
	 */
	public DefaultCTabItem(ReferencedComposite referencedComposite, String text) {
		this(referencedComposite, 0, text);
	}
}
