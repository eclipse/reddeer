/*******************************************************************************
 * Copyright (c) 2017 Red Hat, Inc and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Red Hat Inc. - initial API and implementation
 *******************************************************************************/
package org.eclipse.reddeer.eclipse.wst.xml.ui.tabletree;

import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.texteditor.ITextEditor;
import org.hamcrest.Matcher;
import org.eclipse.reddeer.eclipse.exception.EclipseLayerException;
import org.eclipse.reddeer.eclipse.ui.part.MultiPageEditorPart;

/**
 * This is common ancestor for all XML based editor that
 * have Source and Design page. 
 * 
 * @author Lucia Jelinkova
 *
 */
public abstract class AbstractXMLEditor extends MultiPageEditorPart {

	/**
	 * Find editor extending {@link MultiPageEditorPart} 
	 * with the given title matcher.
	 *
	 * @param titleMatcher Title of the editor
	 * @param clazz Class of the editor
	 * @param matchers Additional matchers for the editor
	 */
	@SuppressWarnings("unchecked")
	public AbstractXMLEditor(Matcher<String> titleMatcher, 
			Class<? extends org.eclipse.ui.part.MultiPageEditorPart> clazz, Matcher<IEditorPart>... matchers) {
		super(titleMatcher, clazz, matchers);
	}
	
	/**
	 * Find editor with the given title matcher. This constructor allows to 
	 * search for the fully qualified String class name for 
	 * cases when the editor class is declared internal and not accessible. 
	 * 
	 * @param titleMatcher Title of the editor
	 * @param fullClassName Full class name of the editor
	 * @param matchers Additional matchers for the editor
	 */
	@SuppressWarnings("unchecked")
	public AbstractXMLEditor(Matcher<String> titleMatcher, 
			String fullClassName, Matcher<IEditorPart>... matchers) {
		super(titleMatcher, fullClassName, matchers);
	}
	
	/**
	 * Select the design page tab.
	 */
	public void selectDesignPage(){
		super.selectPage("Design");
	}
	
	/**
	 * Select the source page tab.
	 */
	public void selectSourcePage(){
		super.selectPage("Source");
	}
	
	/**
	 * Return object for working with design page.
	 *
	 * @return page that enables to work with XML in tree format
	 */
	public XMLDesignPage getDesignPage(){
		selectDesignPage();
		return new XMLDesignPage();
	}
	
	/**
	 * Return object for working with source page.
	 *
	 * @return page that enables to work with XML in text format
	 */
	public XMLSourcePage getSourcePage(){
		selectSourcePage();
		Object o = getSelectedPage();
		if (o instanceof ITextEditor){
			return new XMLSourcePage((ITextEditor) o);
		} 
		throw new EclipseLayerException("Expected " + ITextEditor.class + 
				" but was " + o.getClass());
	}

	@Override
	public XMLEditorFile getAssociatedFile() {
		return new XMLEditorFile(super.getAssociatedFile());
	}

}
