package org.jboss.reddeer.core.condition;

import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.TreeItem;
import org.eclipse.swt.widgets.Widget;
import org.jboss.reddeer.common.condition.AbstractWaitCondition;
import org.jboss.reddeer.core.exception.CoreLayerException;
import org.jboss.reddeer.core.util.Display;
import org.jboss.reddeer.core.util.ResultRunnable;

/**
 * Condition is met when specified Eclipse Widget is checked.
 * 
 * @author Jiri Peterka
 *
 */
public class WidgetIsChecked extends AbstractWaitCondition {

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
		Boolean ret = Display.syncExec(new ResultRunnable<Boolean>() {
		

			@Override
			public Boolean run() {
				if (widget instanceof TableItem){
					return ((TableItem)widget).getChecked();
				} else if (widget instanceof TreeItem){
					return ((TreeItem)widget).getChecked();
				} else {
					throw new CoreLayerException("Unable to call method getChecked() on widget of class " 
						+ widget.getClass());
				}
				
			}
			
		});
		return ret;
	}

	@Override
	public String description() {
		return "widget is checked";
	}

}
