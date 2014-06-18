package org.jboss.reddeer.swt.api;

import java.util.List;

import org.jboss.reddeer.swt.widgets.Widget;

/**
 * API for link manipulation.
 * 
 * @author Jiri Peterka, rhopp
 *
 */
public interface Link extends Widget {

	/**
	 * Returns text of link stripped of &lt;a&gt; and &lt;/a&gt; tags.
	 * <p/>
	 * For example "This is a &lt;a&gt;link&lt;/a&gt;" will result in
	 * "This is a link".
	 * 
	 * @return clean text of the link
	 */
	String getText();

	/**
	 * Returns array of anchor texts (text between &lt;a&gt; and &lt;/a&gt;).
	 * <p/>
	 * For example "This is a &lt;a&gt;link&lt;/a&gt;" will result in "link".
	 * 
	 * @return list of texts between &lt;a&gt; and &lt;/a&gt;
	 */
	List<String> getAnchorTexts();

	/**
	 * Clicks on anchor with specified text within the link.
	 * 
	 * Link widget can have multiple anchors in itself. Example:
	 * "Link &lt;a&gt;link1&lt;/a&gt; and &lt;A href="test"&gt;link2&lt;A\&gt;"
	 * <p/>
	 * 
	 * link.click(link1) invokes SWT.Selection event with "link1" as event's
	 * text.
	 * <p/>
	 * link.click(link2) invokes SWT.Selection event with "test" as event's
	 * text.
	 * 
	 * @param text text of anchor to click on
	 */
	void click(String text);

	/**
	 * Clicks on first anchor of the link.
	 */
	void click();

	/**
	 * Clicks on the anchor with the specified index in the link.
	 * 
	 * @param index index of anchor
	 */
	void click(int index);

	org.eclipse.swt.widgets.Link getSWTWidget();

}
