package org.jboss.reddeer.uiforms.impl.expandablecomposite;

import org.eclipse.swt.widgets.Widget;
import org.eclipse.ui.forms.widgets.ExpandableComposite;
import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.jboss.reddeer.swt.lookup.WidgetLookup;
import org.jboss.reddeer.swt.matcher.ClassMatcher;
import org.jboss.reddeer.swt.matcher.WithMnemonicTextMatcher;
import org.jboss.reddeer.swt.reference.ReferencedComposite;

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

	/**
	 * Represents the first expandable composite inside the specified
	 * {@link ReferencedComposite}.
	 *
	 * @param referencedComposite
	 */
	public DefaultExpandableComposite(ReferencedComposite referencedComposite) {
		this(referencedComposite, 0);
	}

	/**
	 * Represents expandable composite with the specified <var>index</var>.
	 *
	 * @param index expandable composite index
	 */
	public DefaultExpandableComposite(int index) {
		this(null, index);
	}

	/**
	 * Represents expandable composite inside the specified
	 * {@link ReferencedComposite} with the specified <var>index</var>.
	 *
	 * @param referencedComposite
	 * @param index expandable composite index
	 */
	public DefaultExpandableComposite(ReferencedComposite referencedComposite, int index) {
		this(referencedComposite, index, null);
	}

	/**
	 * Represents expandable composite with the specified <var>text</var>.
	 *
	 * @param text expandable composite text
	 */
	public DefaultExpandableComposite(String text) {
		this(null, 0, text);
	}

	/**
	 * Represents expandable composite inside the specified
	 * {@link ReferencedComposite} with the specified <var>text</var>.
	 *
	 * @param referencedComposite
	 * @param text expandable composite text
	 */
	public DefaultExpandableComposite(ReferencedComposite referencedComposite, String text) {
		this(referencedComposite, 0, text);
	}

	/**
	 * Represents expandable composite inside the specified
	 * {@link ReferencedComposite} with the specified <var>text</var> and
	 * the specified <var>index</var>.
	 *
	 * @param referencedComposite
	 * @param index expandable composite index
	 * @param text expandable composite text
	 */
	public DefaultExpandableComposite(ReferencedComposite referencedComposite, int index, String text) {
		if (text != null && !text.isEmpty()) {
			composite = (ExpandableComposite) WidgetLookup.getInstance()
					.activeWidget(referencedComposite,
							ExpandableComposite.class, index,
							new WithMnemonicTextMatcher(text), classMatcher);
		} else {
			composite = (ExpandableComposite) WidgetLookup.getInstance()
					.activeWidget(referencedComposite,
							ExpandableComposite.class, index, classMatcher);
		}
	}

	/**
	 * Represents expandable composite with the specified <var>text</var> and
	 * the specified <var>index</var>.
	 *
	 * @param index expandable composite index
	 * @param text expandable composite text
	 */
	public DefaultExpandableComposite(int index, String text) {
		this(null, index, text);
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
	private static class StrictClassMatcher extends BaseMatcher {

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
