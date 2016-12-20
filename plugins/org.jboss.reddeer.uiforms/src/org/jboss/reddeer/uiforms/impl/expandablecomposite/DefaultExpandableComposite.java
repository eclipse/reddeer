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
package org.jboss.reddeer.uiforms.impl.expandablecomposite;

import org.eclipse.swt.widgets.Widget;
import org.eclipse.ui.forms.widgets.ExpandableComposite;
import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.jboss.reddeer.common.matcher.MatcherBuilder;
import org.jboss.reddeer.core.lookup.WidgetLookup;
import org.jboss.reddeer.core.matcher.ClassMatcher;
import org.jboss.reddeer.core.matcher.WithMnemonicTextMatcher;
import org.jboss.reddeer.core.reference.ReferencedComposite;

/**
 * Default implementation of {@link ExpandableComposite}.
 *
 * @author Radoslav Rabara
 *
 */
public class DefaultExpandableComposite extends AbstractExpandableComposite {

	/**
	 * {@link StrictClassMatcher} is used when the {@link WidgetLookup} is
	 * searching for {@link ExpandableComposite} because WidgetLookup
	 * is matching for {@link Widget} with given type or with type extending
	 * given type. The problem is that
	 * {@link import org.eclipse.ui.forms.widgets.Section} extends
	 * {@link org.eclipse.ui.forms.widgets.ExpandableComposite} so the
	 * WidgetLookup can return Section instead of ExpandableComposite.
	 */
	private static final StrictClassMatcher classMatcher = new StrictClassMatcher();

	/**
	 * Represents the first expandable composite.
	 */
	public DefaultExpandableComposite() {
		this(0);
	}
	
	public DefaultExpandableComposite(ExpandableComposite widget) {
		super(widget);
	}

	/**
	 * Represents the first expandable composite inside the specified
	 * {@link ReferencedComposite}.
	 *
	 * @param referencedComposite the referenced composite
	 */
	public DefaultExpandableComposite(ReferencedComposite referencedComposite) {
		this(referencedComposite, 0);
	}

	/**
	 * Expandable composite that matches given matchers.
	 *
	 * @param matchers the matchers
	 */
	public DefaultExpandableComposite(Matcher<?>... matchers) {
		this(null, matchers);
	}
	
	/**
	 * Expandable composite that matches given matchers.
	 *
	 * @param referencedComposite the referenced composite
	 * @param matchers the matchers
	 */
	public DefaultExpandableComposite(ReferencedComposite referencedComposite, Matcher<?>... matchers) {
		this(referencedComposite, 0, matchers);
	}
	
	/**
	 * Represents expandable composite with the specified <var>index</var> that matches given matchers.
	 *
	 * @param index expandable composite index
	 * @param matchers the matchers
	 */
	public DefaultExpandableComposite(int index, Matcher<?>... matchers) {
		this(null, index);
	}

	/**
	 * Represents expandable composite inside the specified
	 * {@link ReferencedComposite} with the specified <var>index</var> that matches given matchers.
	 *
	 * @param referencedComposite the referenced composite
	 * @param index expandable composite index
	 * @param matchers the matchers
	 */
	public DefaultExpandableComposite(ReferencedComposite referencedComposite, int index, Matcher<?>... matchers) {
		super(referencedComposite, index, MatcherBuilder.getInstance().addMatcher(matchers, classMatcher));
	}

	/**
	 * Represents expandable composite with the specified <var>text</var>.
	 *
	 * @param text expandable composite text
	 */
	public DefaultExpandableComposite(String text) {
		this(null, text);
	}

	/**
	 * Represents expandable composite inside the specified
	 * {@link ReferencedComposite} with the specified <var>text</var>.
	 *
	 * @param referencedComposite the referenced composite
	 * @param text expandable composite text
	 */
	public DefaultExpandableComposite(ReferencedComposite referencedComposite, String text) {
		this(referencedComposite, 0, new WithMnemonicTextMatcher(text));
	}

	/**
	 * The purpose of this class is to match with {@link Widget}
	 * that has the {@link ExpandableComposite} type.
	 * Is more strict as {@link ClassMatcher} because it doesn't match
	 * {@link Widget} with type extending the {@link ExpandableComposite}.
	 *
	 * @author Radoslav Rabara
	 * 
	 */
	private static class StrictClassMatcher extends BaseMatcher<Object> {

		@Override
		public boolean matches(Object item) {
			return ExpandableComposite.class == item.getClass();
		}

		@Override
		public void describeTo(Description description) {
			description.appendText("with the ExpandableComposite type");
		}

		@Override
		public String toString() {
			return "Matcher matching widget with the ExpandableComposite type";
		}
	}
}
