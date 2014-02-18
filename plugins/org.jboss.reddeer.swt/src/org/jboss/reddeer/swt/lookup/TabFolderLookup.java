package org.jboss.reddeer.swt.lookup;

import org.eclipse.swt.widgets.TabFolder;
import org.hamcrest.Matcher;
import org.jboss.reddeer.swt.reference.ReferencedComposite;

/**
 * TabFolder lookup containing lookup routines for TabFolder widget type
 * 
 * @author Andrej Podhradsky
 * 
 */
public class TabFolderLookup {

	private static TabFolderLookup instance = null;

	private TabFolderLookup() {
	}

	/**
	 * Creates and returns instance of TabFolder Lookup
	 * 
	 * @return ComboLookup instance
	 */
	public static TabFolderLookup getInstance() {
		if (instance == null)
			instance = new TabFolderLookup();
		return instance;
	}

	/**
	 * Return TabFolder instance
	 * 
	 * @param matcher
	 * @return TabFolder Widget matching criteria
	 */
	@SuppressWarnings({ "rawtypes" })
	public TabFolder getTabFolder(ReferencedComposite refComposite, int index, Matcher... matchers) {
		return (TabFolder) WidgetLookup.getInstance().activeWidget(refComposite, TabFolder.class, index, matchers);
	}

}