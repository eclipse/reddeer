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
package org.jboss.reddeer.core.matcher;

import java.util.List;

import org.eclipse.swt.widgets.Link;
import org.eclipse.swt.widgets.Widget;
import org.jboss.reddeer.core.handler.LinkHandler;
import org.jboss.reddeer.core.matcher.WithTextMatcher;

/**
 * Matcher matching {@link org.eclipse.swt.widgets.Link} with specified anchor text. 
 *  
 * @author rawagner
 *
 */
public class AnchorLinkTextMatcher extends WithTextMatcher {
	
	private String text;
	
	/**
	 * Creates new AnchorLinkTextMatcher to match link to specified text.
	 * 
	 * @param text text to match
	 */
	public AnchorLinkTextMatcher(String text) {
		super(text);
		this.text=text;
	}

	/* (non-Javadoc)
	 * @see org.jboss.reddeer.core.matcher.AbstractWidgetWithTextMatcher#extractWidgetText(org.eclipse.swt.widgets.Widget)
	 */
	@Override
	protected String extractWidgetText(Widget widget) {
		if (widget instanceof Link){
				List<String> links = LinkHandler.getInstance().getAnchorTexts((Link) widget);
				if(links.size() > 0){
					for(String t: LinkHandler.getInstance().getAnchorTexts((Link) widget)){
						if(text.equals(t)){
							return t; 
						}
					}
				}
		}
		return null;
	}
}