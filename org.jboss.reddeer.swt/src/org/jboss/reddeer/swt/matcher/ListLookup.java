package org.jboss.reddeer.swt.matcher;

import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Widget;
import org.hamcrest.Matcher;
import org.jboss.reddeer.swt.exception.SWTLayerException;
import org.jboss.reddeer.swt.lookup.impl.WidgetLookup;


/**
 * List lookup containing lookup routines for List widget type
 * @author Rastislav Wagner
 *
 */
public class ListLookup {

	private static ListLookup instance = null;

	private ListLookup() {
	}

	/**
	 * Creates and returns instance of List Lookup
	 * 
	 * @return ListLookup instance
	 */
	public static ListLookup getInstance() {
		if (instance == null)
			instance = new ListLookup();
		return instance;
	}

	/**
	 * Return List instance
	 * 
	 * @param matcher
	 * @return List Widget matching criteria
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List getList(int index, Matcher... matchers) {
		List list = null;
		ClassMatcher cm = new ClassMatcher(List.class);
		Matcher[] allMatchers = MatcherBuilder.getInstance().addMatcher(
				matchers, cm);
		AndMatcher am = new AndMatcher(allMatchers);

		Widget parentWidget = WidgetLookup.getInstance()
				.getActiveWidgetParentControl();
		java.util.List<Widget> listWidgets = WidgetLookup.getInstance().findControls(
				parentWidget, am, true);

		if (listWidgets.size() > index)
			list = (org.eclipse.swt.widgets.List) listWidgets.get(index);
		else
			throw new SWTLayerException("List widget not found");

		if (list == null)
			throw new SWTLayerException("list widget is null");
		return list;
	}

}
