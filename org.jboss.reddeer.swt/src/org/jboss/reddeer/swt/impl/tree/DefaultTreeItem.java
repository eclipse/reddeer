package org.jboss.reddeer.swt.impl.tree;


import java.util.Iterator;
import java.util.List;

import org.jboss.reddeer.junit.logging.Logger;
import org.jboss.reddeer.swt.api.Tree;
import org.jboss.reddeer.swt.api.TreeItem;
import org.jboss.reddeer.swt.condition.WaitCondition;
import org.jboss.reddeer.swt.exception.SWTLayerException;
import org.jboss.reddeer.swt.reference.ReferencedComposite;
import org.jboss.reddeer.swt.wait.WaitUntil;

/**
 * Default tree item implementation
 * 
 * @author jjankovi
 *
 */
public class DefaultTreeItem extends AbstractTreeItem {

protected static final Logger logger = Logger.getLogger(DefaultTreeItem.class);
	
	/**
	 * Default parameter-less constructor
	 */
	public DefaultTreeItem() {
		this(null, 0);
	}
	
	/**
	 * TreeItem inside given composite
	 * @param
	 */
	public DefaultTreeItem(ReferencedComposite referencedComposite) {
		this(referencedComposite, 0);
	}
	
	/**
	 * Tree item with specified path will be constructed 
	 * 
	 * @param treeItemPath
	 */
	public DefaultTreeItem(String... treeItemPath) {
		this(null, 0, treeItemPath);
	}
	
	/**
	 * Tree item with specified path inside given composite will be constructed
	 * @param referencedComposite
	 * @param treeItemPath
	 */
	public DefaultTreeItem(ReferencedComposite referencedComposite, String... treeItemPath) {
		this(referencedComposite, 0, treeItemPath);
	}
	
	/**
	 * Tree item with specified tree index and path will be constructed
	 * 
	 * @param treeIndex
	 * @param treeItemPath
	 */
	public DefaultTreeItem(int treeIndex, String... treeItemPath) {
		super(findTreeItem(null, treeIndex, treeItemPath));
	}
	
	/**
	 * Tree item with specified tree index and path inside given composite will be constructed 
	 * @param referencedComposite
	 * @param treeIndex
	 * @param treeItemPath
	 */
	public DefaultTreeItem(ReferencedComposite referencedComposite, int treeIndex, String... treeItemPath) {
		super(findTreeItem(referencedComposite, treeIndex, treeItemPath));
	}
	
	/**
	 * Tree item with specified tree item index will be constructed
	 * 
	 * @param treeItemIndex
	 */
	public DefaultTreeItem(int treeItemIndex) {
		this(null, 0, treeItemIndex);
	}
	
	/**
	 * Tree item with specified tree item index inside given composite will be constructed
	 * @param referencedComposite
	 * @param treeItemIndex
	 */
	public DefaultTreeItem(ReferencedComposite referencedComposite, int treeItemIndex) {
		this(referencedComposite, 0, treeItemIndex);
	}
	
	/**
	 * Tree item with specified tree and tree item index will be constructed
	 * 
	 * @param treeIndex
	 * @param treeItemIndex
	 */
	public DefaultTreeItem(int treeIndex, int treeItemIndex) {
		super(findTreeItem(null, treeIndex, treeItemIndex));
	}
	
	/**
	 * Tree item with specified tree and tree item index inside given composite will be constructed
	 * @param referencedComposite
	 * @param treeIndex
	 * @param treeItemIndex
	 */
	public DefaultTreeItem(ReferencedComposite referencedComposite, int treeIndex, int treeItemIndex) {
		super(findTreeItem(referencedComposite, treeIndex, treeItemIndex));
	}
	
	/**
	 * Return swt widget of Tree Item 
	 */
	public org.eclipse.swt.widgets.TreeItem getSWTWidget() {
		return swtTreeItem;
	}
	
	private static org.eclipse.swt.widgets.TreeItem findTreeItem(ReferencedComposite referencedComposite, int treeIndex, int treeItemIndex){
		Tree tree = new DefaultTree(referencedComposite, treeIndex);
		new WaitUntil(new TreeHasChildren(tree));
		logger.debug("Searching for tree item with index: " + treeIndex);
		List<TreeItem> items = tree.getItems();
		if (items.size() < treeItemIndex + 1) {
			SWTLayerException exception = new SWTLayerException("No matching tree item found");
			exception.addMessageDetail("Tree Index: " + treeIndex);
			exception.addMessageDetail("Tree Item Index: " + treeItemIndex);
			exception.addMessageDetail("Tree has these " + items.size()  +" Tree Items:");
			for (TreeItem treeItem : items){
				exception.addMessageDetail("  " + treeItem.getText());
			}
			throw exception;
		}
		else{
			return tree.getItems().get(treeItemIndex).getSWTWidget();
		}
	}
  
	private static org.eclipse.swt.widgets.TreeItem findTreeItem(ReferencedComposite referencedComposite, int treeIndex, String... treeItemPath){
		org.eclipse.swt.widgets.TreeItem result = null;
		Tree tree = new DefaultTree(referencedComposite, treeIndex);
		new WaitUntil(new TreeHasChildren(tree));
		List<TreeItem> items = tree.getItems();
		int index = 0;
		while (index < treeItemPath.length){
			String pathItem = treeItemPath[index];
			logger.debug("Searching for tree item with label: " + pathItem);
			TreeItem tiItem = null;
			boolean isFound = false;
			Iterator<TreeItem> itTreeItem = items.iterator();
			while (itTreeItem.hasNext() && (!isFound)){
				tiItem = itTreeItem.next();
				if (tiItem.getText().equals(pathItem)){
					isFound = true;
				}
			}
			if (isFound) {
				// It's not last item of Tree Item Path
				if (index < (treeItemPath.length - 1)){
					items = tiItem.getItems();
				}
				else{       
					result = tiItem.getSWTWidget();
				}  
			}
			else{
				SWTLayerException exception = new SWTLayerException("No matching tree item found");
				exception.addMessageDetail("Tree Index: " + treeIndex);
				StringBuffer sbPath = new StringBuffer("");
				for (String treeItem : treeItemPath){
					if (sbPath.length() > 0){
						sbPath.append(" > ");
					}	
					sbPath.append(treeItem);
				}
				exception.addMessageDetail("Tree Item Path: " + sbPath.toString());
				exception.addMessageDetail("Unalbe to find path item with text: " + pathItem);
				exception.addMessageDetail("These Tree Items have been found at level where path item " + pathItem + " was expected:");
				for (TreeItem treeItem : items){
					exception.addMessageDetail("  " + treeItem.getText());	
				}
				throw exception;
			}
			index++;
		}
		return result;
	}
	}

/**
 * Condition is fulfilled when tree has children 
 * 
 * @author jjankovi
 *
 */
class TreeHasChildren implements WaitCondition {
	private Tree tree;
	public TreeHasChildren(Tree tree) {
		super();
		this.tree = tree;
	}
	
	@Override
	public boolean test() {
		return tree.getItems().size() > 0;		
	}
		@Override
	public String description() {
		return "Tree has no children";
	}
}
