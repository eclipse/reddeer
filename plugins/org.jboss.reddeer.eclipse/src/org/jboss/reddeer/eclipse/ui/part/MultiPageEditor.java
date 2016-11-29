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
package org.jboss.reddeer.eclipse.ui.part;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.part.MultiPageEditorPart;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.jboss.reddeer.common.matcher.MatcherBuilder;
import org.jboss.reddeer.common.util.Display;
import org.jboss.reddeer.common.util.ResultRunnable;
import org.jboss.reddeer.core.matcher.WithTextMatcher;
import org.jboss.reddeer.swt.impl.ctab.DefaultCTabItem;
import org.jboss.reddeer.workbench.impl.editor.AbstractEditor;
import org.jboss.reddeer.workbench.matcher.EditorPartTitleMatcher;

/**
 * Represents {@link MultiPageEditorPart} - an editor with multiple pages, 
 * each of which may contain an editor or an arbitrary SWT control. 
 * 
 * @author Lucia Jelinkova
 *
 */
public class MultiPageEditor extends AbstractEditor {

	/**
	 * Find MultiPageEditorPart with the given title.
	 *
	 * @param title the title
	 */
	public MultiPageEditor(String title) {
		this(new WithTextMatcher(title));
	}
	
	/**
	 * Find MultiPageEditorPart with the given title matcher.
	 *
	 * @param titleMatcher the title matcher
	 */
	@SuppressWarnings("unchecked")
	public MultiPageEditor(Matcher<String> titleMatcher) {
		this(titleMatcher, MultiPageEditorPart.class);
	}
	
	/**
	 * Find MultiPageEditorPart with the given title matcher and String class name.
	 *
	 * @param titleMatcher the title matcher
	 * @param className the class name
	 * @param matchers the matchers
	 */
	@SuppressWarnings("unchecked")
	public MultiPageEditor(Matcher<String> titleMatcher, String className, Matcher<IEditorPart>... matchers) {
		this(titleMatcher, MultiPageEditorPart.class, MatcherBuilder.getInstance().addMatcher(matchers, new EditorClassStringMatcher(className)));
	}
	
	/**
	 * Find MultiPageEditorPart with the given title matcher and 
	 * of given type (has to be child of {@link MultiPageEditorPart}) that
	 * is matching given matchers. 
	 *
	 * @param titleMatcher the title matcher
	 * @param clazz the clazz
	 * @param matchers the matchers
	 */
	@SuppressWarnings("unchecked")
	protected MultiPageEditor(Matcher<String> titleMatcher, 
			Class<? extends MultiPageEditorPart> clazz, Matcher<IEditorPart>... matchers) {
		super(createEditorMatchers(titleMatcher, clazz, matchers));
	}
	
	/**
	 * Activate the page (tab) with the given name.
	 *
	 * @param name the name
	 */
	public void selectPage(String name) {
		activate();
		new DefaultCTabItem(name).activate();
	}
	
	/**
	 * Returns the {@link Object} associated with the current page. 
	 * It could be editor or any {@link Composite}
	 *
	 * @return the selected page
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
	
	/**
	 * Check that the editor is of the correct type if it is not possible to 
	 * use Class object (e.g editor is internal (exported only to friend bundle)).
	 * 
	 * @author Lucia Jelinkova
	 *
	 */
	private static class EditorClassStringMatcher extends TypeSafeMatcher<IEditorPart> {

		private String fullClassName;
		
		private EditorClassStringMatcher(String clazz) {
			this.fullClassName = clazz;
		}
		
		@Override
		public void describeTo(Description description) {
			description.appendText("Editor is of type " + fullClassName);
		}

		@Override
		protected boolean matchesSafely(IEditorPart item) {
			return fullClassName.equals(item.getClass().getCanonicalName());
		}
	}
}
