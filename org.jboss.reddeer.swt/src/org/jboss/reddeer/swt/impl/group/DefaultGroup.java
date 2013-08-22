package org.jboss.reddeer.swt.impl.group;

import org.eclipse.swt.widgets.Group;
import org.jboss.reddeer.swt.handler.WidgetHandler;
import org.jboss.reddeer.swt.lookup.WidgetLookup;
import org.jboss.reddeer.swt.matcher.WithMnemonicMatcher;
import org.jboss.reddeer.swt.reference.ReferenceComposite;
import org.jboss.reddeer.swt.reference.ReferencedComposite;

/**
 * Default Group implementation
 * @author Rastislav Wagner
 * @since 0.4
 *
 */
public class DefaultGroup implements org.jboss.reddeer.swt.api.Group,ReferencedComposite {

	private Group group;
	
	/**
	 * Default group constructor
	 */
	public DefaultGroup(){
		new DefaultGroup(0);
	}
	
	/**
	 * 
	 * @param index group index
	 */
	public DefaultGroup(int index){
		new DefaultGroup(index,null);
	}

	/**
	 * 
	 * @param text group text
	 */
	public DefaultGroup(String text){
		new DefaultGroup(0,text);
	}
	
	/**
	 * 
	 * @param index group index
	 * @param text group text
	 */
	public DefaultGroup(int index,String text){
		if (text != null && !text.isEmpty()) {
			group = (Group)WidgetLookup.getInstance().activeWidget(Group.class, index,new WithMnemonicMatcher(text));
		} else {
			group = (Group)WidgetLookup.getInstance().activeWidget(Group.class, index);
		}
		setFocus();
		setAsReference();
	}
	
	@Override
	public String getText() {
		return WidgetHandler.getInstance().getText(group);
	}
	
	@Override
	public void setAsReference() {
		ReferenceComposite.setComposite(group);
	}
	
	private void setFocus() {
		WidgetHandler.getInstance().setFocus(group);
	}

}
