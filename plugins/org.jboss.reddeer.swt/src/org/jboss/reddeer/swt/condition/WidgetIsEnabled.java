package org.jboss.reddeer.swt.condition;

import org.jboss.reddeer.common.condition.WaitCondition;
import org.jboss.reddeer.swt.widgets.Widget;

/**
 * Condition is met when specified widget is enabled.
 * 
 * @author mlabuda@redhat.com
 *
 */
public class WidgetIsEnabled implements WaitCondition {

	private Widget widget;

	/**
	 * Constructs WidgetIsEnabled wait condition.
	 * Condition is met when specified widget is enabled.
	 * 
	 * @param widget widget which should be enabled to let the condition pass
	 */
	public WidgetIsEnabled(Widget widget) {
		this.widget = widget;
	}

	@Override
	public boolean test() {
		return widget.isEnabled();
	}

	@Override
	public String description() {
		return "widget is enabled";
	}

}
