package org.jboss.reddeer.swt.api;

import java.util.List;

import org.jboss.reddeer.swt.widgets.Widget;

/**
 * API For Link manipulation
 * 
 * @author Jiri Peterka, rhopp
 *
 */
public interface Link extends Widget {

	/**
	 * Returns text of link stripped of &lt;a&gt; and &lt;/a&gt; tags.
	 * <p/>
	 * For example "This is a &lt;a&gt;link&lt;/a&gt;" will result in
	 * "This is a link"
	 * 
	 * @return
	 */
	String getText();

	/**
	 * Return array of anchor texts (text between &lt;a&gt; and &lt;/a&gt;)
	 * <p/>
	 * For example "This is a &lt;a&gt;link&lt;/a&gt;" will result in "link"
	 * 
	 * @return list of texts between &lt;a&gt; and &lt;/a&gt;
	 */

	List<String> getAnchorTexts();

	/**
	 * Clicks on anchor with text within this link widget
	 * 
	 * Link widget can have multiple anchors in them. Example:
	 * "Link &lt;a&gt;link1&lt;/a&gt; and &lt;A href="test"&gt;link2&lt;A\&gt;"
	 * <p/>
	 * 
	 * link.click(link1) envokes SWT.Selection event with "link1" as event's
	 * text.
	 * <p/>
	 * link.click(link2) envokes SWT.Selection event with "test" as event's
	 * text.
	 * 
	 */
	void click(String text);

	/**
	 * Clicks on first anchor of this link widget.
	 */
	void click();

	/**
	 * Clicks on the nth anchor of this link widget.
	 */
	void click(int index);

	org.eclipse.swt.widgets.Link getSWTWidget();

}
