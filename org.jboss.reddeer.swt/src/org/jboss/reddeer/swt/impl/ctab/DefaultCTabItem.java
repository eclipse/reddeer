package org.jboss.reddeer.swt.impl.ctab;

import org.jboss.reddeer.swt.lookup.impl.CTabItemLookup;
import org.jboss.reddeer.swt.matcher.WithMnemonicMatcher;

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
	 * CTabItem with specified index will be constructed 
	 * 
	 * @param index
	 */
	public DefaultCTabItem(int index) {
		this(index, null);
	}
	/**
	 * CTabItem with specified index and text will be constructed 
	 * 
	 * @param index
	 * @param text
	 */
	public DefaultCTabItem(int index, String text) {
		super((text != null && !text.isEmpty()) ? CTabItemLookup.getInstance()
				.getCTabItem(index, new WithMnemonicMatcher(text))
				: CTabItemLookup.getInstance().getCTabItem(index));
	}
	/**
	 * CTabItem with specified text will be constructed 
	 * 
	 * @param text
	 */
	public DefaultCTabItem(String text) {
		this(0, text);
	}
}
