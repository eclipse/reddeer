package org.jboss.reddeer.spy.widget;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Widget;

/**
 * List child is a child from {@link org.eclipse.swt.widgets.List} widget. This is workaround for 
 * getting children of a list, because list returns such items as strings not as widgets.
 * 
 * @author mlabuda@redhat.com
 * @since 0.8.0
 */
public class ListChild extends Widget {

	private String text;
	
	/**
	 * Creates a new ListChild with specified text.
	 * 
	 * @param parent parent of list child
	 * @param text
	 */
	public ListChild(Widget parent, String text) {
		super(parent, SWT.NONE);
		this.text = text;
	}
	
	/**
	 * Gets text of list child.
	 * @return text of list child
	 */
	public String getText() {
		return text;
	}
}
