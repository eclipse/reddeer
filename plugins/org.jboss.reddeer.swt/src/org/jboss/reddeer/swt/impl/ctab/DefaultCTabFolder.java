package org.jboss.reddeer.swt.impl.ctab;

import org.eclipse.swt.custom.CTabFolder;
import org.hamcrest.Matcher;
import org.jboss.reddeer.core.reference.ReferencedComposite;

/**
 * Default {@link CTabFolder} implementation
 * 
 * @author Lucia Jelinkova
 *
 */
public class DefaultCTabFolder extends AbstractCTabFolder {

	/**
	 * Default parameter-less constructor.
	 */
	public DefaultCTabFolder() {
		this(0);
	}
	
	/**
	 * CTabFolder inside given composite.
	 *
	 * @param referencedComposite the referenced composite
	 */
	public DefaultCTabFolder(ReferencedComposite referencedComposite) {
		this(referencedComposite, 0);
	}
	
	/**
	 * CTabFolder that matches given matchers.
	 *
	 * @param matchers the matchers
	 */
	public DefaultCTabFolder(Matcher<?>... matchers) {
		this(null, matchers);
	}
	
	/**
	 * CTabFolder that matches given matchers.
	 *
	 * @param referencedComposite the referenced composite
	 * @param matchers the matchers
	 */
	public DefaultCTabFolder(ReferencedComposite referencedComposite, Matcher<?>... matchers) {
		this(referencedComposite, 0, matchers);
	}
	
	/**
	 * CTabFolder with specified index will be constructed .
	 *
	 * @param index the index
	 * @param matchers the matchers
	 */
	public DefaultCTabFolder(int index, Matcher<?>... matchers) {
		this(null, index, matchers);
	}
	
	/**
	 * CTabFolder with specified index inside given composite will be constructed .
	 *
	 * @param referencedComposite the referenced composite
	 * @param index the index
	 * @param matchers the matchers
	 */
	public DefaultCTabFolder(ReferencedComposite referencedComposite, int index, Matcher<?>... matchers) {
		super(referencedComposite, index, matchers);
	}
	
	/**
	 * Constructs CTabFolder from given swt widget
	 * @param swtWidget
	 */
	DefaultCTabFolder(CTabFolder swtWidget){
		super(swtWidget);
	}
}
