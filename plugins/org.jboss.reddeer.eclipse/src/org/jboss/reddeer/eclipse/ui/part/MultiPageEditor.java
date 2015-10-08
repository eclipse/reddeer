package org.jboss.reddeer.eclipse.ui.part;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.part.MultiPageEditorPart;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.jboss.reddeer.core.matcher.EditorPartTitleMatcher;
import org.jboss.reddeer.core.matcher.WithTextMatcher;
import org.jboss.reddeer.core.util.Display;
import org.jboss.reddeer.core.util.ResultRunnable;
import org.jboss.reddeer.swt.impl.ctab.DefaultCTabItem;
import org.jboss.reddeer.workbench.impl.editor.AbstractEditor;

/**
 * Represents {@link MultiPageEditorPart} - an editor with multiple pages, 
 * each of which may contain an editor or an arbitrary SWT control. 
 * 
 * @author Lucia Jelinkova
 *
 */
public class MultiPageEditor extends AbstractEditor {

	/**
	 * Find MultiPageEditorPart with the given title
	 * @param title
	 */
	public MultiPageEditor(String title) {
		this(new WithTextMatcher(title));
	}
	
	/**
	 * Find MultiPageEditorPart with the given title matcher
	 * @param title
	 */
	@SuppressWarnings("unchecked")
	public MultiPageEditor(Matcher<String> titleMatcher) {
		this(titleMatcher, MultiPageEditorPart.class);
	}
	
	/**
	 * Find MultiPageEditorPart with the given title matcher and 
	 * of given type (has to be child of {@link MultiPageEditorPart}) that
	 * is matching given matchers. 
	 * @param title
	 */
	@SuppressWarnings("unchecked")
	protected MultiPageEditor(Matcher<String> titleMatcher, 
			Class<? extends MultiPageEditorPart> clazz, Matcher<IEditorPart>... matchers) {
		super(createEditorMatchers(titleMatcher, clazz, matchers));
	}
	
	/**
	 * Activate the page (tab) with the given name
	 * @param name
	 */
	public void selectPage(String name) {
		new DefaultCTabItem(name).activate();
	}
	
	/**
	 * Returns the {@link Object} associated with the current page. 
	 * It could be editor or any {@link Composite}
	 * @return
	 */
	protected Object getSelectedPage(){
		return Display.syncExec(new ResultRunnable<Object>() {

			@Override
			public Object run() {
				return ((MultiPageEditorPart) getEditorPart()).getSelectedPage();
			}
		});
	}
	
	@SuppressWarnings("unchecked")
	private static Matcher<IEditorPart>[] createEditorMatchers(Matcher<String> titleMatcher, 
			Class<? extends MultiPageEditorPart> clazz, Matcher<IEditorPart>[] matchers){ 
		Matcher<IEditorPart>[] allMatchers = new Matcher[2 + matchers.length];
		allMatchers[0] = new MultiPageEditorMatcher(clazz);
		allMatchers[1] = new EditorPartTitleMatcher(titleMatcher);
		
		for (int i = 0; i < matchers.length; i++){
			allMatchers[i+2] = matchers[i];
		}
		return allMatchers;
	}
	
	private static class MultiPageEditorMatcher extends TypeSafeMatcher<IEditorPart>{

		private Class<? extends MultiPageEditorPart> clazz;
		
		public MultiPageEditorMatcher(Class<? extends MultiPageEditorPart> clazz) {
			this.clazz = clazz;
		}

		@Override
		public void describeTo(Description description) {
			description.appendText("Editor is of type " + clazz);
		}

		@Override
		protected boolean matchesSafely(IEditorPart item) {
			return clazz.isAssignableFrom(item.getClass()); 
		}
	}
}
