package org.jboss.reddeer.swt.condition;

import org.jboss.reddeer.swt.api.TableItem;
import org.jboss.reddeer.swt.api.TreeItem;
import org.jboss.reddeer.swt.exception.SWTLayerException;
import org.jboss.reddeer.swt.widgets.Widget;

/**
 * Condition is met when specified widget is checked.
 * 
 * @author Vlado Pakan
 *
 */
public class WidgetIsChecked implements WaitCondition {

	private Widget widget;

	/**
	 * Constructs WidgetIsChecked wait condition.
	 * Condition is met when specified widget is checked.
	 * 
	 * @param widget widget which should be checked to let the condition pass
	 */
	public WidgetIsChecked(Widget widget) {
		this.widget = widget;
	}

	@Override
	public boolean test() {
		if (widget instanceof TableItem){
			return ((TableItem)widget).isChecked();
		} else if (widget instanceof TreeItem){
			return ((TreeItem)widget).isChecked();
		} else {
			throw new SWTLayerException("Unable to call method isChecked() on widget of class " 
				+ widget.getClass());
		}

	}

	@Override
	public String description() {
		return "widget is checked";
	}

}
