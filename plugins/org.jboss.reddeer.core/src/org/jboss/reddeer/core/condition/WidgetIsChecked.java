/******************************************************************************* 
 * Copyright (c) 2016 Red Hat, Inc. 
 * Distributed under license by Red Hat, Inc. All rights reserved. 
 * This program is made available under the terms of the 
 * Eclipse Public License v1.0 which accompanies this distribution, 
 * and is available at http://www.eclipse.org/legal/epl-v10.html 
 * 
 * Contributors: 
 * Red Hat, Inc. - initial API and implementation 
 ******************************************************************************/ 
package org.jboss.reddeer.core.condition;

import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.TreeItem;
import org.eclipse.swt.widgets.Widget;
import org.jboss.reddeer.common.condition.AbstractWaitCondition;
import org.jboss.reddeer.common.util.Display;
import org.jboss.reddeer.common.util.ResultRunnable;
import org.jboss.reddeer.core.exception.CoreLayerException;

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

	/* (non-Javadoc)
	 * @see org.jboss.reddeer.common.condition.WaitCondition#test()
	 */
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

	/* (non-Javadoc)
	 * @see org.jboss.reddeer.common.condition.AbstractWaitCondition#description()
	 */
	@Override
	public String description() {
		return "widget is checked";
	}

}
