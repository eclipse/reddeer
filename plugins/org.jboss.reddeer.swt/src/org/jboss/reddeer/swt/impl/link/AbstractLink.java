package org.jboss.reddeer.swt.impl.link;

import org.jboss.reddeer.junit.logging.Logger;
import org.jboss.reddeer.swt.api.Link;
import org.jboss.reddeer.swt.handler.LinkHandler;
import org.jboss.reddeer.swt.handler.WidgetHandler;

public abstract class AbstractLink implements Link{
	
	protected org.eclipse.swt.widgets.Link link;

	protected final Logger logger = Logger.getLogger(this.getClass());
	
	public String getText(){
		return LinkHandler.getInstance().getText(link);
	}
	
	public void click(){
		LinkHandler.getInstance().activate(link);
	}
	
	public org.eclipse.swt.widgets.Link getSWTWidget(){
		return link;
	}
	
	@Override
	public boolean isEnabled() {
		return WidgetHandler.getInstance().isEnabled(link);
	}

}
