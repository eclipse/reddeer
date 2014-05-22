package org.jboss.reddeer.swt.handler;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Link;
import org.jboss.reddeer.swt.exception.SWTLayerException;
import org.jboss.reddeer.swt.util.Display;
import org.jboss.reddeer.swt.util.ResultRunnable;

/**
 * Contains methods that handle UI operations on {@link Link} widgets.
 * 
 * @author Lucia Jelinkova, rhopp
 *
 */
public class LinkHandler {

	private static LinkHandler instance;

	private LinkHandler() {

	}

	/**
	 * Creates and returns instance of TreeHandler class
	 * 
	 * @return
	 */
	public static LinkHandler getInstance() {
		if (instance == null) {
			instance = new LinkHandler();
		}
		return instance;
	}

	/**
	 * This method parses given link text for containing anchors, then returns
	 * those anchor's texts as list of strings
	 * <p/>
	 * For example
	 * "This is a &lt;a&gt;link1&lt;/a&gt; and &lt;a&gt;link2&lt;/a&gt;" will
	 * result in ["link1","link2"]
	 * 
	 * @param l
	 * @return
	 */
	public List<String> getAnchorTexts(final Link l) {

		String linkText = getTextInternal(l);
		List<String> anchorTextList = new ArrayList<String>();
		for (AnchorPair anchorPair : parseLinks(linkText)) {
			anchorTextList.add(anchorPair.getAnchorText());
		}
		return anchorTextList;
	}

	/**
	 * This method returns text, which should be passed as event.text in
	 * selection event, for anchor in given link, specified by its text and
	 * index.
	 * 
	 * @param l
	 *            given link
	 * @param text
	 *            text of the anchor (text between &lt;a&gt; and &lt;/a&gt;)
	 * @param index
	 *            index of anchor, given that multiple anchors have same text
	 * @return
	 */
	public String getEventText(final Link l, String text, int index) {
		String linkText = getTextInternal(l);
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
		throw new SWTLayerException("There is no anchor with text [" + text
				+ "] and index " + index + " in this link");
	}

	/**
	 * This method returns text, which should be passed as event.text in
	 * selection event, for anchor in given link, specified by its index.
	 * 
	 * @param l
	 *            given link
	 * @param index
	 *            index of anchod within given link
	 * @return
	 */
	public String getEventText(final Link l, int index) {
		String linkText = getTextInternal(l);
		List<AnchorPair> list = parseLinks(linkText);
		if (list.size() <= index) {
			throw new SWTLayerException("There are only " + list.size() +
					" anchors in this link and you requested anchor #" + index);
		}
		Collections.sort(list);
		AnchorPair ap = list.get(index);
		return ap.getAnchorHref().equals("") ? ap.getAnchorText() : ap
				.getAnchorHref();
	}

	/**
	 * This method strips given link's text of anchor tags.
	 * <p/>
	 * For link with text "This is a &lt;a&gt;link&lt;/a&gt;" this method will
	 * return "This is a link"
	 * 
	 * @param l given link
	 * @return
	 */

	public String getText(final Link l) {
		return getTextInternal(l).replaceAll("<[aA]([^>]*)>(.+?)</[aA]>", "$2");
	}
	
	/**
	 * Activates widget - link/hyperlink etc
	 * 
	 * @param w
	 *            widget to activate
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
