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
package org.jboss.reddeer.swt.impl.link;

import java.util.List;

import org.hamcrest.Matcher;
import org.jboss.reddeer.common.logging.Logger;
import org.jboss.reddeer.swt.api.Link;
import org.jboss.reddeer.core.handler.LinkHandler;
import org.jboss.reddeer.core.reference.ReferencedComposite;
import org.jboss.reddeer.swt.widgets.AbstractWidget;

public abstract class AbstractLink extends AbstractWidget<org.eclipse.swt.widgets.Link> implements Link {

	private static final Logger logger = Logger.getLogger(AbstractLink.class);

	protected AbstractLink(ReferencedComposite refComposite, int index, Matcher<?>... matchers){
		super(org.eclipse.swt.widgets.Link.class, refComposite, index, matchers);
	}
	
	protected AbstractLink(org.eclipse.swt.widgets.Link widget){
		super(widget);
	}
	
	/* (non-Javadoc)
	 * @see org.jboss.reddeer.swt.api.Link#getText()
	 */
	public String getText() {
		return LinkHandler.getInstance().getText(swtWidget);
	}

	/* (non-Javadoc)
	 * @see org.jboss.reddeer.swt.api.Link#getAnchorTexts()
	 */
	public List<String> getAnchorTexts() {
		return LinkHandler.getInstance().getAnchorTexts(swtWidget);
	}

	/* (non-Javadoc)
	 * @see org.jboss.reddeer.swt.api.Link#click(java.lang.String)
	 */
	public void click(String text) {
		click(text, 0);
	}

	/**
	 * Click.
	 *
	 * @param text the text
	 * @param index the index
	 */
	public void click(String text, int index) {
		logger.info("Click link with text '" + text + "' and index " + index);
		String eventText = LinkHandler.getInstance().getEventText(swtWidget, text,
				index);
		logger.info("Click link's text '" + eventText + "'");
		LinkHandler.getInstance().activate(swtWidget, eventText);
	}

	/* (non-Javadoc)
	 * @see org.jboss.reddeer.swt.api.Link#click()
	 */
	public void click() {
		click(0);
	}

	/* (non-Javadoc)
	 * @see org.jboss.reddeer.swt.api.Link#click(int)
	 */
	public void click(int index) {
		logger.info("Click link with index " + index);
		String eventText = LinkHandler.getInstance().getEventText(swtWidget, index);
		logger.info("Click link's text '" + eventText + "'");
		LinkHandler.getInstance().activate(swtWidget, eventText);
	}
}
