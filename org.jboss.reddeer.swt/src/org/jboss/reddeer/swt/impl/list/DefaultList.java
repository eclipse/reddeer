package org.jboss.reddeer.swt.impl.list;

import org.jboss.reddeer.swt.api.List;
import org.jboss.reddeer.swt.lookup.ListLookup;
import org.jboss.reddeer.swt.matcher.GroupMatcher;
import org.jboss.reddeer.swt.matcher.LabelMatcher;


/**
 * Default Label implementation.
 * @author Rastislav Wagner
 *
 */
public class DefaultList extends AbstractList implements List{
	
	/**
	 * List with index 0
	 */
	public DefaultList(){
		list = ListLookup.getInstance().getList(0);
	}
	/**
	 * Text with given index
	 * @param index of list
	 */
	public DefaultList(int index){
		list = ListLookup.getInstance().getList(index);
	}
	/**
	 * Text with given label
	 * @param label of list
	 */
	public DefaultList(String label){
		list = ListLookup.getInstance().getList(0,new LabelMatcher(label));
	}
	/**
	 * Text with given label in given Group
	 * @param inGroup in group
	 * @param label of list
	 */
	public DefaultList(String inGroup, String label){
		list = ListLookup.getInstance().getList(0,new LabelMatcher(label), new GroupMatcher(inGroup));
	}
	/**
	 * Text with given index in given Group
	 * @param inGroup in group
	 * @param index of text
	 */
	public DefaultList(String inGroup, int index){
		list = ListLookup.getInstance().getList(0,new GroupMatcher(inGroup));
	}

}
