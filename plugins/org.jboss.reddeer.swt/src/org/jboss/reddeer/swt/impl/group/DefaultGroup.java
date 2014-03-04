package org.jboss.reddeer.swt.impl.group;


import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Group;
import org.jboss.reddeer.swt.handler.WidgetHandler;
import org.jboss.reddeer.swt.lookup.WidgetLookup;
import org.jboss.reddeer.swt.matcher.WithMnemonicMatcher;
import org.jboss.reddeer.swt.reference.ReferencedComposite;

/**
 * Default Group implementation
 * @author Rastislav Wagner
 * @since 0.4
 *
 */
public class DefaultGroup implements org.jboss.reddeer.swt.api.Group {

	private Group group;
	
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
	 * Group with given index
	 * @param index group index
	 */
	public DefaultGroup(int index){
		this(null, index,null);
	}
	
	/**
	 * Group with given index inside given composite
	 * @param referencedComposite
	 * @param index group index 
	 */
	public DefaultGroup(ReferencedComposite referencedComposite, int index){
		this(referencedComposite, index,null);
	}

	/**
	 * Group with given text
	 * @param text group text
	 */
	public DefaultGroup(String text){
		this(null, 0,text);
	}
	
	/**
	 * Group with given text inside given composite
	 * @param referencedComposite
	 * @param text group text
	 */
	public DefaultGroup(ReferencedComposite referencedComposite, String text){
		this(referencedComposite, 0,text);
	}
	
	/**
	 * Group with given index and text inside given composite
	 * @param referencedComposite
	 * @param index group index
	 * @param text group text
	 */
	public DefaultGroup(ReferencedComposite referencedComposite, int index,String text){
		if (text != null && !text.isEmpty()) {
			group = (Group)WidgetLookup.getInstance().activeWidget(referencedComposite, Group.class, index,new WithMnemonicMatcher(text));
		} else {
			group = (Group)WidgetLookup.getInstance().activeWidget(referencedComposite, Group.class, index);
		}
	}
	
	/**
	 * Group with given index and text
	 * @param index group index
	 * @param text group text
	 */
	public DefaultGroup(int index,String text){
		if (text != null && !text.isEmpty()) {
			group = (Group)WidgetLookup.getInstance().activeWidget(null, Group.class, index,new WithMnemonicMatcher(text));
		} else {
			group = (Group)WidgetLookup.getInstance().activeWidget(null, Group.class, index);
		}
	}
	
	@Override
	public String getText() {
		return WidgetHandler.getInstance().getText(group);
	}

	@Override
	public Control getControl() {
		return group;
	}


	public Group getSWTWidget() {
		return group;
	}
	
	@Override
	public boolean isEnabled() {
		return WidgetHandler.getInstance().isEnabled(group);
	}

}
