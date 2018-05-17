/*******************************************************************************
 * Copyright (c) 2017 Red Hat, Inc and others.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     Red Hat, Inc - initial API and implementation
 *******************************************************************************/
package org.eclipse.reddeer.swt.impl.link;

import java.util.List;

import org.hamcrest.Matcher;
import org.eclipse.reddeer.common.logging.Logger;
import org.eclipse.reddeer.swt.api.Link;
import org.eclipse.reddeer.core.handler.LinkHandler;
import org.eclipse.reddeer.core.reference.ReferencedComposite;
import org.eclipse.reddeer.swt.widgets.AbstractControl;

public abstract class AbstractLink extends AbstractControl<org.eclipse.swt.widgets.Link> implements Link {

	private static final Logger logger = Logger.getLogger(AbstractLink.class);

	protected AbstractLink(ReferencedComposite refComposite, int index, Matcher<?>... matchers){
		super(org.eclipse.swt.widgets.Link.class, refComposite, index, matchers);
	}
	
	protected AbstractLink(org.eclipse.swt.widgets.Link widget){
		super(widget);
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.reddeer.swt.api.Link#getText()
	 */
	public String getText() {
		return LinkHandler.getInstance().getText(swtWidget);
	}

	/* (non-Javadoc)
	 * @see org.eclipse.reddeer.swt.api.Link#getAnchorTexts()
	 */
	public List<String> getAnchorTexts() {
		return LinkHandler.getInstance().getAnchorTexts(swtWidget);
	}

	/* (non-Javadoc)
	 * @see org.eclipse.reddeer.swt.api.Link#click(java.lang.String)
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
	 * @see org.eclipse.reddeer.swt.api.Link#click()
	 */
	public void click() {
		click(0);
	}

	/* (non-Javadoc)
	 * @see org.eclipse.reddeer.swt.api.Link#click(int)
	 */
	public void click(int index) {
		logger.info("Click link with index " + index);
		String eventText = LinkHandler.getInstance().getEventText(swtWidget, index);
		logger.info("Click link's text '" + eventText + "'");
		LinkHandler.getInstance().activate(swtWidget, eventText);
	}
}
