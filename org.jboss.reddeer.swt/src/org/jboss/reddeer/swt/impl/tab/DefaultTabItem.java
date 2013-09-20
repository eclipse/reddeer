package org.jboss.reddeer.swt.impl.tab;

import org.jboss.reddeer.swt.lookup.TabItemLookup;
import org.jboss.reddeer.swt.matcher.WithMnemonicMatcher;
import org.jboss.reddeer.swt.reference.ReferencedComposite;

/**
 * Default TabItem implementation
 * 
 * @author Andrej Podhradsky
 * @author Vlado Pakan
 * 
 */
public class DefaultTabItem extends AbstractTabItem {

	/**
	 * Default parameter-less constructor
	 */
	public DefaultTabItem() {
		this(0);
	}

	/**
	 * TabItem inside given composite
	 * 
	 * @param referencedComposite
	 */
	public DefaultTabItem(ReferencedComposite referencedComposite) {
		this(referencedComposite, 0);
	}

	/**
	 * TabItem with specified index will be constructed
	 * 
	 * @param index
	 */
	public DefaultTabItem(int index) {
		this(index, null);
	}

	/**
	 * TabItem with specified index inside given composite will be constructed
	 * 
	 * @param referencedComposite
	 * @param index
	 */
	public DefaultTabItem(ReferencedComposite referencedComposite, int index) {
		this(referencedComposite, index, null);
	}

	/**
	 * TabItem with specified index and text will be constructed
	 * 
	 * @param index
	 * @param text
	 */
	public DefaultTabItem(int index, String text) {
		super((text != null && !text.isEmpty()) ? TabItemLookup.getInstance().getTabItem(null,
				index, new WithMnemonicMatcher(text)) : TabItemLookup.getInstance().getTabItem(
				null, index));
	}

	/**
	 * TabItem with specified index and text inside given composite will be
	 * constructed
	 * 
	 * @param referencedComposite
	 * @param index
	 * @param text
	 */
	public DefaultTabItem(ReferencedComposite referencedComposite, int index, String text) {
		super((text != null && !text.isEmpty()) ? TabItemLookup.getInstance().getTabItem(
				referencedComposite, index, new WithMnemonicMatcher(text)) : TabItemLookup
				.getInstance().getTabItem(referencedComposite, index));
	}

	/**
	 * TabItem with specified text will be constructed
	 * 
	 * @param text
	 */
	public DefaultTabItem(String text) {
		this(0, text);
	}

	/**
	 * TabItem with specified text inside given composite will be constructed
	 * 
	 * @param referencedComposite
	 * @param text
	 */
	public DefaultTabItem(ReferencedComposite referencedComposite, String text) {
		this(referencedComposite, 0, text);
	}
}
