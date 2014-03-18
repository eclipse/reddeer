package org.jboss.reddeer.swt.condition;

import org.jboss.reddeer.swt.widgets.Widget;

public class WidgetIsEnabled implements WaitCondition{
	
	private Widget widget;
    
    public WidgetIsEnabled(Widget widget){
    	this.widget=widget;
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
