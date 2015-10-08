package org.jboss.reddeer.eclipse.wst.xml.ui.tabletree;

import org.hamcrest.Matcher;
import org.jboss.reddeer.core.matcher.WithTextMatcher;

/**
 * Represents an XML editor with Design and Source pages.
 * 
 * @author Lucia Jelinkova
 *
 */
public class XMLMultiPageEditor extends AbstractXMLEditor {

	/**
	 * Find XMLMultiPageEditorPart with the given title
	 * @param title
	 */
	public XMLMultiPageEditor(String title) {
		this(new WithTextMatcher(title));
	}
	
	/**
	 * Find XMLMultiPageEditorPart with the given title matcher
	 * @param title
	 */
	@SuppressWarnings("unchecked")
	public XMLMultiPageEditor(Matcher<String> titleMatcher) {
		super(titleMatcher, "org.eclipse.wst.xml.ui.internal.tabletree.XMLMultiPageEditorPart");
	}
}
