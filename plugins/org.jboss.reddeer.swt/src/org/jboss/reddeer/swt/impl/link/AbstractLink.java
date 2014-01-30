package org.jboss.reddeer.swt.impl.link;

import org.jboss.reddeer.junit.logging.Logger;
import org.jboss.reddeer.swt.api.Link;
import org.jboss.reddeer.swt.handler.WidgetHandler;
import org.jboss.reddeer.swt.lookup.WidgetLookup;

public abstract class AbstractLink implements Link{
	
	protected org.eclipse.swt.widgets.Link link;

	protected final Logger logger = Logger.getLogger(this.getClass());
	
	public String getText(){
		return WidgetHandler.getInstance().getText(link);
	}
	
	public void click(){
		WidgetHandler.getInstance().activate(link);
	}
	
	public org.eclipse.swt.widgets.Link getSWTWidget(){
		return link;
	}
	
	@Override
	public boolean isEnabled() {
		return WidgetLookup.getInstance().isEnabled(link);
	}

}
