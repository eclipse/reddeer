package org.jboss.reddeer.swt.impl.link;

import org.apache.log4j.Logger;
import org.jboss.reddeer.swt.api.Link;
import org.jboss.reddeer.swt.handler.WidgetHandler;

public abstract class AbstractLink implements Link{
	
	protected org.eclipse.swt.widgets.Link link;

	protected final Logger logger = Logger.getLogger(this.getClass());
	
	public String getText(){
		return WidgetHandler.getInstance().getText(link);
	}
	
	public void click(){
		WidgetHandler.getInstance().activate(link);
	}

}
