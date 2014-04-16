package org.jboss.reddeer.uiforms.impl.formtext;

import org.hamcrest.Matcher;
import org.jboss.reddeer.swt.handler.WidgetHandler;
import org.jboss.reddeer.swt.reference.ReferencedComposite;
import org.jboss.reddeer.uiforms.lookup.FormTextLookup;
import org.jboss.reddeer.uiforms.matcher.FormTextWithTextMatcher;

/**
 * Default implementation of FormText widget
 * 
 * @author rhopp
 *
 */

public class DefaultFormText extends AbstractFormText {

	/**
	 * Represents widget of type FormText
	 */

	public DefaultFormText() {
		this(0);
	}

	/**
	 * Represents nth widget of type FormText
	 * 
	 * @param index
	 */

	public DefaultFormText(int index) {
		this(null, index);
	}

	/**
	 * Represents widget of type FormText with given text
	 * 
	 * @param text
	 */

	public DefaultFormText(String text) {
		this(null, text);
	}

	/**
	 * Represents widget of type FormText which
	 * 
	 * @param matchers
	 */

	public DefaultFormText(Matcher<?>... matchers) {
		this(null, matchers);
	}

	/**
	 * Represents first widget of type FormText inside of given
	 * ReferencedComposite
	 * 
	 * @param referencedComposite
	 */

	public DefaultFormText(ReferencedComposite referencedComposite) {
		this(referencedComposite, 0);
	}

	/**
	 * Represents nth widget of type FormText inside of given
	 * ReferencedComposite
	 * 
	 * @param referencedComposite
	 * @param index
	 */

	public DefaultFormText(ReferencedComposite referencedComposite, int index) {
		widget = FormTextLookup.getInstance().getFormText(referencedComposite,
				index);
		setFocus();
	}

	/**
	 * Represents widget of type FormText inside of given ReferencedComposite
	 * with given text
	 * 
	 * @param referencedComposite
	 * @param text
	 */

	public DefaultFormText(ReferencedComposite referencedComposite, String text) {
		this(referencedComposite, new FormTextWithTextMatcher(text));
	}

	/**
	 * Represents first widget of type FormText inside of given
	 * ReferencedComposite that fulfils given matcher
	 * 
	 * @param referencedComposite
	 * @param matchers
	 */

	public DefaultFormText(ReferencedComposite referencedComposite,
			Matcher<?>... matchers) {
		this(referencedComposite, 0, matchers);
	}

	/**
	 * Represents nth widget of type FormText inside of given
	 * ReferencedComposite that fulfils given matcher
	 * 
	 * @param referencedComposite
	 * @param index
	 * @param matchers
	 */

	public DefaultFormText(ReferencedComposite referencedComposite, int index,
			Matcher<?>... matchers) {
		widget = FormTextLookup.getInstance().getFormText(referencedComposite,
				index, matchers);
		setFocus();
	}
	
	public boolean isEnabled() {
		return WidgetHandler.getInstance().isEnabled(widget);
	}
}
