package org.jboss.reddeer.swt.impl.link;

import java.util.List;

import org.jboss.reddeer.common.logging.Logger;
import org.jboss.reddeer.swt.api.Link;
import org.jboss.reddeer.swt.handler.LinkHandler;
import org.jboss.reddeer.swt.handler.WidgetHandler;

public abstract class AbstractLink implements Link {

	protected org.eclipse.swt.widgets.Link link;

	protected final Logger logger = Logger.getLogger(this.getClass());

	public String getText() {
		return LinkHandler.getInstance().getText(link);
	}

	public List<String> getAnchorTexts() {
		return LinkHandler.getInstance().getAnchorTexts(link);
	}

	public void click(String text) {
		click(text, 0);
	}

	public void click(String text, int index) {
		String eventText = LinkHandler.getInstance().getEventText(link, text,
				index);
		LinkHandler.getInstance().activate(link, eventText);
	}

	public void click() {
		click(0);
	}

	public void click(int index) {
		String eventText = LinkHandler.getInstance().getEventText(link, index);
		LinkHandler.getInstance().activate(link, eventText);
	}

	public org.eclipse.swt.widgets.Link getSWTWidget() {
		return link;
	}

	@Override
	public boolean isEnabled() {
		return WidgetHandler.getInstance().isEnabled(link);
	}

}
