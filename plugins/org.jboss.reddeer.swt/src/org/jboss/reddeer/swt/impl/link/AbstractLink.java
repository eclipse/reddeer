package org.jboss.reddeer.swt.impl.link;

import java.util.List;

import org.hamcrest.Matcher;
import org.jboss.reddeer.common.logging.Logger;
import org.jboss.reddeer.swt.api.Link;
import org.jboss.reddeer.swt.handler.LinkHandler;
import org.jboss.reddeer.core.reference.ReferencedComposite;
import org.jboss.reddeer.swt.widgets.AbstractWidget;

public abstract class AbstractLink extends AbstractWidget<org.eclipse.swt.widgets.Link> implements Link {

	private static final Logger logger = Logger.getLogger(AbstractLink.class);

	protected AbstractLink(ReferencedComposite refComposite, int index, Matcher<?>... matchers){
		super(org.eclipse.swt.widgets.Link.class, refComposite, index, matchers);
	}
	
	public String getText() {
		return LinkHandler.getInstance().getText(swtWidget);
	}

	public List<String> getAnchorTexts() {
		return LinkHandler.getInstance().getAnchorTexts(swtWidget);
	}

	public void click(String text) {
		click(text, 0);
	}

	public void click(String text, int index) {
		logger.info("Click link with text '" + text + "' and index " + index);
		String eventText = LinkHandler.getInstance().getEventText(swtWidget, text,
				index);
		logger.info("Click link's text '" + eventText + "'");
		LinkHandler.getInstance().activate(swtWidget, eventText);
	}

	public void click() {
		click(0);
	}

	public void click(int index) {
		logger.info("Click link with index " + index);
		String eventText = LinkHandler.getInstance().getEventText(swtWidget, index);
		logger.info("Click link's text '" + eventText + "'");
		LinkHandler.getInstance().activate(swtWidget, eventText);
	}
}
