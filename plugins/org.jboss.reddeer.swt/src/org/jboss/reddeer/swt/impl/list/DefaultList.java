package org.jboss.reddeer.swt.impl.list;

import org.jboss.reddeer.swt.api.List;
import org.jboss.reddeer.swt.lookup.ListLookup;
import org.jboss.reddeer.swt.matcher.LabelMatcher;
import org.jboss.reddeer.swt.reference.ReferencedComposite;


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
		list = ListLookup.getInstance().getList(null, 0);
	}
	
	/**
	 * List with index 0 inside given composite
	 * @param referencedComposite
	 */
	public DefaultList(ReferencedComposite referencedComposite){
		list = ListLookup.getInstance().getList(referencedComposite, 0);
	}
	
	/**
	 * Text with given index
	 * @param index of list
	 */
	public DefaultList(int index){
		list = ListLookup.getInstance().getList(null, index);
	}
	
	/**
	 * Text with given index inside given composite
	 * @param referencedComposite
	 * @param index of list
	 */
	public DefaultList(ReferencedComposite referencedComposite, int index){
		list = ListLookup.getInstance().getList(referencedComposite, index);
	}
	
	/**
	 * Text with given label
	 * @param label of list
	 */
	public DefaultList(String label){
		list = ListLookup.getInstance().getList(null, 0,new LabelMatcher(label));
	}
	
	/**
	 * Text with given label inside given composite
	 * @param referencedComposite
	 * @param label of list
	 */
	public DefaultList(ReferencedComposite referencedComposite, String label){
		list = ListLookup.getInstance().getList(referencedComposite, 0,new LabelMatcher(label));
	}
	
	/**
	 * Text with given index
	 * @param index of text
	 */
	public DefaultList(String label, int index){
		list = ListLookup.getInstance().getList(null, index,new LabelMatcher(label));
	}
	
	/**
	 * Text with given index inside given composite
	 * @param referencedComposite
	 * @param label
	 * @param index of text
	 */
	public DefaultList(ReferencedComposite referencedComposite, String label, int index){
		list = ListLookup.getInstance().getList(referencedComposite, index,new LabelMatcher(label));
	}

}
