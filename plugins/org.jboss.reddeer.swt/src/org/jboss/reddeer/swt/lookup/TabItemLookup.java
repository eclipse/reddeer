package org.jboss.reddeer.swt.lookup;

import org.eclipse.swt.widgets.TabItem;
import org.hamcrest.Matcher;
import org.jboss.reddeer.core.reference.ReferencedComposite;

/**
 * TabItem lookup containing lookup routines for TabItem widget type
 * 
 * @author Andrej Podhradsky
 * 
 */
public class TabItemLookup {

	private static TabItemLookup instance = null;

	private TabItemLookup() {
	}

	/**
	 * Creates and returns instance of Combo Lookup
	 * 
	 * @return ComboLookup instance
	 */
	public static TabItemLookup getInstance() {
		if (instance == null)
			instance = new TabItemLookup();
		return instance;
	}

	/**
	 * Return TabItem instance
	 * 
	 * @param matcher
	 * @return Combo Widget matching criteria
	 */
	@SuppressWarnings({ "rawtypes" })
	public TabItem getTabItem(ReferencedComposite refComposite, int index, Matcher... matchers) {
		return (TabItem) WidgetLookup.getInstance().activeWidget(refComposite, TabItem.class, index, matchers);
	}

}