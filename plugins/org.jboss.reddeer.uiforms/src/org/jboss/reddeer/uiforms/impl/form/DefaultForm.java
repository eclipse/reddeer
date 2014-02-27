package org.jboss.reddeer.uiforms.impl.form;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.jboss.reddeer.swt.reference.ReferencedComposite;
import org.jboss.reddeer.swt.util.Display;
import org.jboss.reddeer.swt.util.ResultRunnable;
import org.jboss.reddeer.uiforms.lookup.FormLookup;

/**
 * Represents Eclipse Form. This class will be used mainly for its children discovering
 * 
 * @author jjankovi
 *
 */
public class DefaultForm extends AbstractForm {

	/**
	 * Default constructor, represents the first form. 
	 */
	public DefaultForm() {
		this(0);
	}
	
	/**
	 * Represents the form with the specified order. 
	 * @param index
	 */
	public DefaultForm(int index) {
		this(null, index);
	}
	
	/**
	 * Represents the form with the specified title. 
	 * @param text
	 */
	public DefaultForm(String title) {
		this(null, title);
	}
	
	/**
	 * Represents the form that fulfills specified matchers
	 * @param matchers
	 */
	public DefaultForm(Matcher<?>... matchers) {
		this(null, matchers);
	}
	
	/**
	 * Represents the first form inside specified composite
	 * @param referencedComposite
	 */
	public DefaultForm(ReferencedComposite referencedComposite) {
		this(referencedComposite, 0);
	}
	
	/**
	 * Represents the form with the specified order inside specified composite
	 * @param referencedComposite
	 * @param index
	 */
	public DefaultForm(ReferencedComposite referencedComposite, int index) {
		form = FormLookup.getInstance().getForm(referencedComposite, index);
		setFocus();
	}
	
	/**
	 * Represents the form with the specified title inside specified composite
	 * @param referencedComposite
	 * @param text
	 */
	public DefaultForm(ReferencedComposite referencedComposite, String title) {
		this(referencedComposite, new FormTextMatcher(title));
	}
	
	/**
	 * Represents the form that fulfills specified matchers inside specified composite
	 * @param referencedComposite
	 * @param matchers
	 */
	public DefaultForm(ReferencedComposite referencedComposite, Matcher<?>... matchers) {
		form = FormLookup.getInstance().getForm(referencedComposite, 0, matchers);
		setFocus();
	}
	
	private static class FormTextMatcher extends TypeSafeMatcher<org.eclipse.ui.forms.widgets.Form> {

		private String title;
		
		private FormTextMatcher(String title) {
			if (title == null){
				throw new IllegalArgumentException("Title cannot be null");
			}
			this.title = title;
		}
		
		@Override
		protected boolean matchesSafely(final org.eclipse.ui.forms.widgets.Form form) {
			String realTitle = Display.syncExec(new ResultRunnable<String>() {
				@Override
				public String run() {
					return form.getText();
				}
			});
			
			return title.equals(realTitle);
		}

		@Override
		public void describeTo(Description description) {
			description.appendText("The form object does not have title ");
		}
	}
}
