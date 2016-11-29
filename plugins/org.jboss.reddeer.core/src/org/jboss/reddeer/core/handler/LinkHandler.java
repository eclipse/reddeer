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
package org.jboss.reddeer.core.handler;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Link;
import org.jboss.reddeer.common.util.Display;
import org.jboss.reddeer.common.util.ResultRunnable;
import org.jboss.reddeer.core.exception.CoreLayerException;

/**
 * Contains methods for handling UI operations on {@link Link} widgets.
 * 
 * @author Lucia Jelinkova, rhopp
 *
 */
public class LinkHandler {

	private static LinkHandler instance;

	private LinkHandler() {

	}

	/**
	 * Gets instance of LinkHandler.
	 * 
	 * @return instance of LinkHandler
	 */
	public static LinkHandler getInstance() {
		if (instance == null) {
			instance = new LinkHandler();
		}
		return instance;
	}

	/**
	 * Parses text of specified {@link Link} to get anchors.
	 * <p/>
	 * Example
	 * "This is a &lt;a&gt;link1&lt;/a&gt; and &lt;a&gt;link2&lt;/a&gt;" will
	 * result in ["link1","link2"]
	 * 
	 * @param link link to handle
	 * @return list of anchors in specified link
	 */
	public List<String> getAnchorTexts(final Link link) {

		String linkText = getTextInternal(link);
		List<String> anchorTextList = new ArrayList<String>();
		for (AnchorPair anchorPair : parseLinks(linkText)) {
			anchorTextList.add(anchorPair.getAnchorText());
		}
		return anchorTextList;
	}

	/**
	 * Gets text, passed as {@link Event.text} in the selection event, 
	 * of the anchor in specified link, with specified text, on the specified index.
	 * 
	 * @param link link to handle
	 * @param text text of the anchor (text between &lt;a&gt; and &lt;/a&gt;)
	 * @param index index of the anchor
	 * @return text of the selection event
	 */
	public String getEventText(final Link link, String text, int index) {
		String linkText = getTextInternal(link);
		int counter = 0;
		for (AnchorPair ap : parseLinks(linkText)) {
			if (ap.getAnchorText().equals(text)) {
				if (counter != index) {
					counter++;
				} else {
					return ap.getAnchorHref().equals("") ? ap.getAnchorText()
							: ap.getAnchorHref();
				}
			}
		}
		throw new CoreLayerException("There is no anchor with text [" + text
				+ "] and index " + index + " in this link");
	}

	/**
	 * Gets text, passed as {@link Event.text} in the selection event,
	 * of the anchor in specified link, on specified index.
	 * 
	 * @param link link to handle
	 * @param index index of the anchor
	 * @return text of the selection event
	 */
	public String getEventText(final Link link, int index) {
		String linkText = getTextInternal(link);
		List<AnchorPair> list = parseLinks(linkText);
		if (list.size() <= index) {
			throw new CoreLayerException("There are only " + list.size()
					+ " anchors in this link and you requested anchor #"
					+ index);
		}
		Collections.sort(list);
		AnchorPair ap = list.get(index);
		return ap.getAnchorHref().equals("") ? ap.getAnchorText() : ap
				.getAnchorHref();
	}

	/**
	 * Gets text without anchor characters.
	 * <p/>
	 * For link with the text "This is a &lt;a&gt;link&lt;/a&gt;" this method
	 * returns "This is a link".
	 * 
	 * @param link link to handle
	 * @return text without anchor characters
	 */

	public String getText(final Link link) {
		return getTextInternal(link).replaceAll("<[aA]([^>]*)>(.+?)</[aA]>", "$2");
	}

	/**
	 * Activates specified {@link Link}.
	 * 
	 * @param link link to handle
	 * @param text text of the notification event
	 */
	public void activate(final Link link, final String text) {
		Display.asyncExec(new Runnable() {

			@Override
			public void run() {
				link.setFocus();
				WidgetHandler.getInstance().notify(SWT.MouseDown, link);
				notifySelection(link, text);
				WidgetHandler.getInstance().notify(SWT.MouseUp, link);
			}
		});
	}

	private List<AnchorPair> parseLinks(String linkText) {
		List<AnchorPair> list = new ArrayList<AnchorPair>();
		Pattern p = Pattern.compile("<[aA]([^>]*)>(.+?)</[aA]>");
		Matcher m = p.matcher(linkText);
		int counter = 0;
		while (m.find()) {
			list.add(new AnchorPair(counter, m.group(2), parseHref(m.group(1))));
			counter++;
		}
		return list;
	}

	private String parseHref(String text) {
		return text.replaceAll(".*href=\"([^\"]*).*", "$1");
	}

	private String getTextInternal(final Link l) {
		String text = Display.syncExec(new ResultRunnable<String>() {
			@Override
			public String run() {
				return l.getText();
			}
		});
		return text;
	}

	private void notifySelection(Link link, String text) {
		Event event = new Event();
		event.time = (int) System.currentTimeMillis();
		event.widget = link;
		event.display = Display.getDisplay();
		event.text = text;
		event.type = SWT.Selection;
		link.notifyListeners(event.type, event);
	}

	private class AnchorPair implements Comparable<AnchorPair> {
		private int index;
		private String anchorText;
		private String anchorHref;

		public AnchorPair(int index, String anchorText, String anchorHref) {
			this.anchorHref = anchorHref;
			this.anchorText = anchorText;
		}

		public int getIndex() {
			return index;
		}

		public String getAnchorHref() {
			return anchorHref;
		}

		public String getAnchorText() {
			return anchorText;
		}

		@Override
		public int compareTo(AnchorPair o) {
			return o.getIndex() - index;
		}
	}

}
