package org.jboss.reddeer.swt.impl.group;

import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Group;
import org.hamcrest.Matcher;
import org.jboss.reddeer.core.handler.WidgetHandler;
import org.jboss.reddeer.core.matcher.WithMnemonicTextMatcher;
import org.jboss.reddeer.core.reference.ReferencedComposite;
import org.jboss.reddeer.swt.widgets.AbstractWidget;

/**
 * Default Group implementation
 * @author Rastislav Wagner
 * @since 0.4
 *
 */
public class DefaultGroup extends AbstractWidget<Group> implements org.jboss.reddeer.swt.api.Group {

	/**
	 * Default group constructor
	 */
	public DefaultGroup(){
		this(null, 0);		
	}

	/**
	 * Group inside given composite
	 * @param referencedComposite
	 */
	public DefaultGroup(ReferencedComposite referencedComposite){
		this(referencedComposite, 0);
	}

	/**
	 * Group with given text
	 * @param text group text
	 */
	public DefaultGroup(String text){
		this(null, text);
	}

	/**
	 * Group with given text inside given composite
	 * @param referencedComposite
	 * @param text group text
	 */
	public DefaultGroup(ReferencedComposite referencedComposite, String text){
		this(referencedComposite, 0, new WithMnemonicTextMatcher(text));
	}

	/**
	 * Group that matches given matchers
	 * @param matchers
	 */
	public DefaultGroup(Matcher<?>... matchers) {
		this(null, matchers);
	}

	/**
	 * Group that matches given matchers
	 * @param referencedComposite
	 * @param matchers
	 */
	public DefaultGroup(ReferencedComposite referencedComposite, Matcher<?>... matchers) {
		this(referencedComposite, 0, matchers);
	}

	/**
	 * Group with given index that matches given matchers
	 * @param index group index
	 * @param matchers
	 */
	public DefaultGroup(int index, Matcher<?>... matchers){
		this(null, index);
	}

	/**
	 * Group with given index inside given composite that matches given matchers
	 * @param referencedComposite
	 * @param index group index 
	 * @param matchers
	 */
	public DefaultGroup(ReferencedComposite referencedComposite, int index, Matcher<?>... matchers){
		super(Group.class, referencedComposite, index, matchers);
	}

	@Override
	public String getText() {
		return WidgetHandler.getInstance().getText(swtWidget);
	}

	@Override
	public Control getControl() {
		return swtWidget;
	}
}
